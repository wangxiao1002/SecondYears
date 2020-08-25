
import json

from lib.utils.exceptions import PubErrorCustom
from lib.utils.mytime import UtilTime

from lib.utils.db import RedisCaCheHandlerBase

from app.cache.serialiers import *

class RedisCaCheHandler(RedisCaCheHandlerBase):

    def __init__(self,**kwargs):

        #必查询字段
        self.must_params = list(set([] + kwargs.get('must_params',[])))
        #查询时过滤字段
        self.filter_params = list(set(['google_token','passwd','pay_passwd'] + kwargs.get('filter_params',[])))
        #带条件查询字段
        self.condition_params = kwargs.get('condition_params',[])

        #请求的值
        self.filter_value = kwargs.get('filter_value') if kwargs.get('filter_value') else {}

        #唯一key
        self.must_key = kwargs.get('must_key') if kwargs.get('must_key') else ""
        #唯一key value
        self.must_key_value = kwargs.get('must_key_value') if kwargs.get('must_key_value') else ""

        #方法
        if not kwargs.get("method"):
            raise PubErrorCustom("方法不能为空")
        self.method = kwargs.get("method")

        #表
        if not kwargs.get("table"):
            raise PubErrorCustom("表不能为空")
        self.table = kwargs.get("table")

        #序列化
        self.serialiers = kwargs.get("serialiers")

        self.ut = UtilTime()

        kwargs.setdefault('key',self.table)
        super().__init__(**kwargs)

    def filter(self):

        #查前端请求的带条件字段
        self.condition_params_web = list(set([] + self.filter_value.get('conditions', []))) if self.filter_value else []

        self.res = self.redis_dict_get_all()
        if not self.res:
            self.res=[]

        #特殊处理字段
        createtime = self.filter_value.pop('createtime') if self.filter_value.get('createtime') else None

        data=[]

        for key in self.res:

            isOk = True
            #必查字段处理
            for item in self.must_params:

                if not self.res[key].get(item,None):
                    isOk=False
                    break
                if str(self.res[key].get(item,None)) != self.filter_value.get('rolecode',None) :
                    isOk=False
                    break
            if not isOk:
                continue

            #带条件查询字段
            for item in self.condition_params:
                rValue = self.filter_value[item[0]] if self.filter_value.get(item[0], None) else None

                if item[1] == 'like':
                    if rValue and item[0] in str(self.res.get(key)) and str(rValue) not in str(self.res.get(key).get(item[0])) :
                        isOk = False
                        break

                elif item[1] == '>':
                    if rValue and item[0] in str(self.res.get(key)) and str(rValue) > str(self.res.get(key).get(item[0])):
                        isOk = False
                        break
                elif item[1] == '>=':
                    if rValue and item[0] in str(self.res.get(key)) and str(rValue) >= str(self.res.get(key).get(item[0])):
                        isOk = False
                        break
                elif item[1] == '<':
                    if rValue and item[0] in str(self.res.get(key)) and str(rValue) < str(self.res.get(key).get(item[0])):
                        isOk = False
                        break
                elif item[1] == '<=':
                    if rValue and item[0] in str(self.res.get(key)) and str(rValue) <= str(self.res.get(key).get(item[0])):
                        isOk = False
                        break
                else:
                    raise PubErrorCustom("标识有误!")
            if not isOk:
                continue

            #其他查询字段
            for item in self.filter_value:
                isOkQuery = False
                for i in self.condition_params:
                    if item == i[0]:
                        isOkQuery=True
                        break
                if isOkQuery:
                    continue

                rValue = self.filter_value.get(item,None)
                if rValue and item in str(self.res.get(key)) and str(rValue) != str(self.res.get(key).get(item)) :
                    isOk = False
                    break
            if not isOk:
                continue

            #特殊字段处理
            if not self.timeHandler(createtime,self.res,key):
                continue

            #前端条件过滤查询
            for item in self.condition_params_web:
                if '=' in item:
                    s = item.split('=')
                    if not (str(self.res[key].get(s[0])) == str(s[1])):
                        isOk = False
                        break
                elif '≠' in item:
                    s = item.split('≠')
                    if not (str(self.res[key].get(s[0])) != str(s[1])):
                        isOk = False
                        break
                elif 'like' in item:
                    s = item.split('like')
                    if not (str(s[1]) in str(self.res[key].get(s[0]))):
                        isOk = False
                        break
                elif '>' in item:
                    s = item.split('>')
                    try:
                        if not (float(self.res[key].get(s[0])) > float(s[1])):
                            isOk = False
                            break
                    except ValueError :
                        if not (str(self.res[key].get(s[0])) > str(s[1])):
                            isOk = False
                            break
                elif '≥' in item:
                    s = item.split('≥')
                    try:
                        if not (float(self.res[key].get(s[0])) >= float(s[1])):
                            isOk = False
                            break
                    except ValueError :
                        if not (str(self.res[key].get(s[0])) >= str(s[1])):
                            isOk = False
                            break
                elif '<' in item:
                    s = item.split('<')
                    try:
                        if not (float(self.res[key].get(s[0])) < float(s[1])):
                            isOk = False
                            break
                    except ValueError :
                        if not (str(self.res[key].get(s[0])) < str(s[1])):
                            isOk = False
                            break
                elif '≤' in item:
                    s = item.split('≤')
                    try:
                        if not (float(self.res[key].get(s[0])) <= float(s[1])):
                            isOk = False
                            break
                    except ValueError :
                        if not (str(self.res[key].get(s[0])) <= str(s[1])):
                            isOk = False
                            break
                elif '∈' in item:
                    s = item.split('∈')
                    if not (str(self.res[key].get(s[0])) in str(s[1]) ):
                        isOk = False
                        break
            if not isOk:
                continue

            for item in self.filter_params:
                if item in self.res[key]:
                    self.res[key].pop(item)
            data.append(self.res[key])

        data.sort(key=lambda k: (k.get('createtime', 0)), reverse=True)
        return data

    def timeHandler(self,timeValue,res,key):
        if timeValue and str(timeValue) != '-1':
            times = timeValue.split(',')

            if len(times) > 1:
                start_date = self.ut.string_to_timestamp(times[0] + ' 00:00:01')
                end_date = self.ut.string_to_timestamp(times[1] + ' 23:59:59')
            else:
                today = self.ut.arrow_to_string(self.ut.string_to_arrow(times[0], format_v="YYYY-MM-DD").replace(days=1),
                                           format_v="YYYY-MM-DD")
                if today == self.ut.arrow_to_string(format_v="YYYY-MM-DD"):
                    start_date = self.ut.string_to_timestamp(times[0] + ' 00:00:01')
                    end_date = self.ut.string_to_timestamp(times[0] + " 23:59:59")
                elif today < self.ut.arrow_to_string(format_v="YYYY-MM-DD"):
                    start_date = self.ut.string_to_timestamp(times[0] + ' 00:00:01')
                    end_date = self.ut.string_to_timestamp(self.ut.arrow_to_string(format_v="YYYY-MM-DD") + " 23:59:59")
                else:
                    start_date = self.ut.string_to_timestamp(times[0] + ' 00:00:01')
                    end_date = self.ut.string_to_timestamp(times[0] + " 23:59:59")

            if res[key]['createtime'] < start_date or res[key]['createtime'] > end_date:
                return False

        return True


    def save(self):

        res = eval("{}(self.filter_value,many=False).data".format(self.serialiers))
        self.redis_dict_set(res.get(self.must_key),res)
        return res

    def delete(self):
        self.redis_dict_del(self.must_key_value)

    def get(self):
        return self.redis_dict_get(self.must_key_value)

    def saveAll(self):

        res = eval("{}(self.filter_value,many=True).data".format(self.serialiers))
        self.redis_dict_delall()

        for item in res:
            self.redis_dict_set(item[self.must_key], item)

    def run(self):

        return eval("self.{}()".format(self.method))
