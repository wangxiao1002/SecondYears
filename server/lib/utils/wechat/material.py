
import json
from requests import request

from lib.utils.wechat.base import WechatBaseForUser
from lib.utils.exceptions import PubErrorCustom

from app.public.models import Meterial
from django.shortcuts import HttpResponse

class WechatMaterial(WechatBaseForUser):

    def __init__(self,**kwargs):

        accid = kwargs.get("accid",None)
        if not accid:
            raise PubErrorCustom("accid为空!")
        super().__init__(accid=accid)

    def get_file_by_url(self,url):

        return None

    def type_change(self,type):
        if type == '2':
            return 'image'
        elif type == '4':
            return 'voice'
        elif type == '5':
            return 'video'
        elif type =='6':
            return 'thumb'
        elif type == '1':
            return 'news'
        else:
            raise PubErrorCustom("类型有误!")

    def create_forever(self,**kwargs):

        meterialObj = kwargs.get("meterialObj",None)
        type = self.type_change(kwargs.get("type",None))
        title = kwargs.get("title",None)
        introduction = kwargs.get("introduction",None)

        response = self.request_handler(method="POST",
                           url="https://api.weixin.qq.com/cgi-bin/material/add_material?access_token={}&type={}".format(
                               self.auth_accesstoken,type),
                           files={"media":(meterialObj['filename'],meterialObj['file'])},
                           data={} if type !='video' else {
                               "description":json.dumps({
                                   "title": title if title else "title",
                                   "introduction": introduction if introduction else "introduction"
                               })
                           })

        media_id = response['media_id']
        url = response.get("url","")

        return media_id,url


    def delete_forever(self,media_id):

        self.request_handler(method="POST",
                           url="https://api.weixin.qq.com/cgi-bin/material/del_material?access_token={}".format(
                               self.auth_accesstoken),
                           json={
                                "media_id":media_id
                           })

    def get_forever(self,media_id):

        return self.request_handler(method="POST",
                           url="https://api.weixin.qq.com/cgi-bin/material/get_material?access_token={}".format(
                               self.auth_accesstoken),
                           json={
                                "media_id":media_id
                           })

    def get_forever_list(self,**kwargs):

        type = self.type_change(kwargs.get("type"))
        offset = kwargs.get("offset",0)
        count = kwargs.get("count",20)

        response = self.request_handler(method="POST",
                           url="https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token={}".format(
                               self.auth_accesstoken),
                                json={
                                    "type":type,
                                    "offset":offset,
                                    "count":count
                                })

        if type == 'video':
            for item in response['item']:
                res = self.get_forever(item['media_id'])
                item['url'] = res['down_url']
                item['title'] = res['title']
                item['description'] = res['description']

        return response