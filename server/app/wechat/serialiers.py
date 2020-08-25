
import json
from rest_framework import serializers
from app.wechat.models import Acc,AccTag,AccQrcode,AccQrcodeList,AccQrcodeImageTextList,\
                    AccLinkUser,AccFollow,AccReply,AccMsgCustomer,AccMsgCustomerLinkAcc,AccMsgMould,AccMsgMass
from lib.utils.mytime import UtilTime
from app.public.models import Meterial
from project.config_include.common import ServerUrl
from app.public.serialiers import MeterialSerializer
from lib.utils.wechat.material import WechatMaterial
from lib.utils.log import logger

class AccSerializer1(serializers.Serializer):
    accid = serializers.IntegerField()
    nick_name = serializers.CharField()
    head_img = serializers.CharField()
    createtime = serializers.IntegerField()

class AccSerializer(serializers.Serializer):

    accid = serializers.IntegerField()
    nick_name = serializers.CharField()
    head_img = serializers.CharField()
    createtime = serializers.IntegerField()
    fans_count = serializers.SerializerMethodField()
    active_fans_count = serializers.SerializerMethodField()
    service_type = serializers.SerializerMethodField()
    verify_type = serializers.SerializerMethodField()
    follow_setting = serializers.CharField()
    reply_setting = serializers.CharField()


    def get_service_type(self,obj):
        return str(json.loads(obj.service_type_info).get("id","3"))

    def get_verify_type(self,obj):
        res = str(json.loads(obj.verify_type_info).get("id",None))

        return '0' if res and res!='-1' else '1'

    def get_active_fans_count(self,obj):

        return AccLinkUser.objects.filter(umark='0',last_active_time__gte=UtilTime().today.shift(hours=-48).timestamp).count()

    def get_fans_count(self,obj):

        return AccLinkUser.objects.filter(accid=obj.accid,umark='0').count()

class AccLinkUserSerializer(serializers.Serializer):

    openid = serializers.CharField()
    accid = serializers.IntegerField()
    nickname = serializers.CharField()
    sex = serializers.CharField()
    province = serializers.CharField()
    country = serializers.CharField()
    city = serializers.CharField()
    tags = serializers.SerializerMethodField()
    subscribe_time = serializers.SerializerMethodField()
    subscribe_scene = serializers.CharField()
    memo = serializers.CharField()
    headimgurl = serializers.CharField()

    def get_tags(self,obj):
        return AccTagModelSerializer(AccTag.objects.filter(id__in=json.loads(obj.tags)).order_by('-createtime'),many=True).data

    def get_subscribe_time(self,obj):
        return UtilTime().timestamp_to_string(obj.subscribe_time)

class AccFollowSerializer(serializers.Serializer):

    nick_name = serializers.CharField()
    accid = serializers.IntegerField()
    head_img = serializers.CharField()
    follow_setting = serializers.CharField()
    follow_obj = serializers.SerializerMethodField()

    def get_follow_obj(self,obj):
        res={}
        if obj.send_type and obj.listids:
            res['send_type'] = obj.send_type
            res['send_content_count'] = len(json.loads(obj.listids))
        return res

class AccFollowModelSerializer(serializers.ModelSerializer):

    lists = serializers.SerializerMethodField()
    acc = serializers.SerializerMethodField()

    def get_acc(self,obj):
        try:
            return AccSerializer(Acc.objects.get(accid=obj.accid), many=False).data
        except AccQrcode.DoesNotExist:
            return {}

    def get_lists(self,obj):
        return AccQrcodeListModelSerializer(AccQrcodeList.objects.filter(id__in=json.loads(obj.listids)).order_by('sort'),many=True).data

    class Meta:
        model = AccFollow
        fields = '__all__'

class AccReplySerializer(serializers.Serializer):

    nick_name = serializers.CharField()
    accid = serializers.IntegerField()
    head_img = serializers.CharField()
    reply_setting = serializers.CharField()
    reply_obj = serializers.SerializerMethodField()

    def get_reply_obj(self,obj):
        res={}
        if obj.send_type and obj.listids and obj.nosend_limit:
            res['send_type'] = obj.send_type
            res['send_content_count'] = len(json.loads(obj.listids))
            res['send_time'] = obj.nosend_limit
        return res

class AccReplyModelSerializer(serializers.ModelSerializer):

    lists = serializers.SerializerMethodField()
    acc = serializers.SerializerMethodField()
    trigger = serializers.SerializerMethodField()
    quiet = serializers.SerializerMethodField()

    def get_quiet(self,obj):

        return obj.quiet.split('-')

    def get_trigger(self,obj):

        return [obj.trigger[0],obj.trigger[1],obj.trigger[2]]

    def get_acc(self,obj):
        try:
            return AccSerializer(Acc.objects.get(accid=obj.accid), many=False).data
        except AccQrcode.DoesNotExist:
            return {}

    def get_lists(self,obj):
        return AccQrcodeListModelSerializer(AccQrcodeList.objects.filter(id__in=json.loads(obj.listids)).order_by('sort'),many=True).data

    class Meta:
        model = AccReply
        fields = '__all__'

class AccFollowModelSerializer1(serializers.ModelSerializer):

    class Meta:
        model = AccFollow
        fields = '__all__'

class AccSendSerializer1(serializers.Serializer):

    nick_name = serializers.CharField()
    date = serializers.CharField()
    send_count = serializers.IntegerField()
    reply_count = serializers.IntegerField()

class AccReplyModelSerializer1(serializers.ModelSerializer):

    class Meta:
        model = AccReply
        fields = '__all__'

class AccQrcodeModelSerializer(serializers.ModelSerializer):

    tags = serializers.SerializerMethodField()
    lists = serializers.SerializerMethodField()
    acc = serializers.SerializerMethodField()
    createtime = serializers.SerializerMethodField()
    endtime = serializers.SerializerMethodField()

    def get_createtime(self,obj):
        return UtilTime().timestamp_to_string(obj.createtime,format_v="%Y-%m-%d")

    def get_endtime(self,obj):
        if obj.endtime:
            return UtilTime().timestamp_to_string(obj.endtime, format_v="%Y-%m-%d")
        else:
            return ""

    def get_acc(self,obj):
        try:
            return AccSerializer(Acc.objects.get(accid=obj.accid), many=False).data
        except AccQrcode.DoesNotExist:
            return {}

    def get_tags(self,obj):
        return AccTagModelSerializer(AccTag.objects.filter(id__in=json.loads(obj.tags)).order_by('-createtime'),many=True).data

    def get_lists(self,obj):
        return AccQrcodeListModelSerializer(AccQrcodeList.objects.filter(id__in=json.loads(obj.listids)).order_by('sort'),many=True).data


    class Meta:
        model = AccQrcode
        fields = ('id','name','accid','tot_count','createtime','acc','new_count','follow_count','type','endtime','qr_type','send_type','url','tags','lists',)

class AccQrcodeListModelSerializer1(serializers.ModelSerializer):

    class Meta:
        model = AccQrcodeList
        fields = '__all__'

class AccQrcodeListModelSerializer(serializers.ModelSerializer):

    imagetextlist=serializers.SerializerMethodField()

    def get_imagetextlist(self,obj):
        return AccQrcodeImageTextListModelSerializer(AccQrcodeImageTextList.objects.filter(id__in=json.loads(obj.iamgetextids)).order_by('sort'),
                                            many=True).data

    class Meta:
        model = AccQrcodeList
        fields = '__all__'

class AccQrcodeImageTextListModelSerializer(serializers.ModelSerializer):

    # local_url = serializers.SerializerMethodField()
    #
    # def get_local_url(self,obj):
    #
    #     if obj.media_id:
    #         return "{}{}".format(ServerUrl,Meterial.objects.get(media_id=obj.media_id).local_url)
    #     else:
    #         return ""

    class Meta:
        model = AccQrcodeImageTextList
        fields = '__all__'

class AccTagModelSerializer(serializers.ModelSerializer):

    class Meta:
        model = AccTag
        fields = '__all__'

class AccMsgCustomerLinkAccModelSerializer(serializers.ModelSerializer):

    acc = serializers.SerializerMethodField()

    def get_acc(self,obj):

        logger.info(obj.accid)
        try:
            r = Acc.objects.get(accid=obj.accid)
            return {
                "nick_name":r.nick_name,
                "head_img":r.head_img
            }
        except Acc.DoesNotExist:
            return {}

    class Meta:
        model = AccMsgCustomerLinkAcc
        fields = '__all__'

class AccMsgCustomerModelSerializer1(serializers.ModelSerializer):
    class Meta:
        model = AccMsgCustomer
        fields = '__all__'

class AccMsgCustomerModelSerializer(serializers.ModelSerializer):

    acclinkobj = serializers.SerializerMethodField()
    sendobjects_format = serializers.SerializerMethodField()
    accids_format =  serializers.SerializerMethodField()
    createtime_format = serializers.SerializerMethodField()
    sendtime_format = serializers.SerializerMethodField()
    lists = serializers.SerializerMethodField()

    def get_lists(self,obj):
        return AccQrcodeListModelSerializer(AccQrcodeList.objects.filter(id__in=json.loads(obj.listids)).order_by('sort'),many=True).data

    def get_acclinkobj(self,obj):

        return AccMsgCustomerLinkAccModelSerializer(AccMsgCustomerLinkAcc.objects.filter(msgid=obj.id,accid__in=json.loads(obj.accids)),many=True).data

    def get_createtime_format(self,obj):
        return UtilTime().timestamp_to_string(obj.createtime)

    def get_sendtime_format(self,obj):
        return UtilTime().timestamp_to_string(obj.sendtime)

    def get_accids_format(self,obj):
        accids = json.loads(obj.accids)
        logger.info(accids)
        try:
            nickname = Acc.objects.get(accid=accids[0]).nick_name
            return "{}等{}个号".format(nickname,len(accids)) if len(accids)>1 else nickname
        except Acc.DoesNotExist:
            return ""

    def get_sendobjects_format(self,obj):
        ut = UtilTime()
        res = ""
        if obj.type == '1':
            return "全部粉丝"
        else:
            if obj.select_sex == '1':
                res+='仅男性粉丝'
            elif  obj.select_sex == '2':
                res += '仅女性粉丝'
            elif obj.select_sex == '0':
                res += '未知性别'

            if len(obj.select_followtime):
                res += "{}-{}".format(ut.timestamp_to_string(obj.select_followtime.split('-')[0]),ut.timestamp_to_string(obj.select_followtime.split('-')[1]))

            if len(obj.select_province) and len(obj.select_city):
                res += "{}-{}".format(obj.select_province,obj.select_city)

            for j,item in enumerate(AccTag.objects.filter(id__in=json.loads(obj.select_tags))):
                if j>0:
                    res += ","
                res += item.name

        return res

    class Meta:
        model = AccMsgCustomer
        fields = '__all__'

class AccMsgMouldSerializer(serializers.Serializer):

    id = serializers.IntegerField()
    acc = serializers.SerializerMethodField()
    name = serializers.CharField()
    sendobjects_format = serializers.SerializerMethodField()
    mould_name = serializers.CharField()
    sendtime_format = serializers.SerializerMethodField()
    send_count = serializers.IntegerField()
    status = serializers.CharField()
    mould_data = serializers.SerializerMethodField()

    def get_mould_data(self,obj):

        return json.loads(obj.mould_data)

    def get_acc(self,obj):
        try:
            return AccSerializer1(Acc.objects.get(accid=obj.accid), many=False).data
        except Acc.DoesNotExist:
            return {}

    def get_sendtime_format(self,obj):
        return UtilTime().timestamp_to_string(obj.sendtime)

    def get_sendobjects_format(self,obj):

        res = ""
        if obj.type == '0':
            return "全部粉丝"
        else:
            if obj.select_sex == '1':
                res+='仅男性粉丝'
            elif  obj.select_sex == '2':
                res += '仅女性粉丝'
            elif obj.select_sex == '0':
                res += '未知性别'

            if len(obj.select_province) and len(obj.select_city):
                res += "{}-{}".format(obj.select_province,obj.select_city)

            for j,item in enumerate(AccTag.objects.filter(id__in=json.loads(obj.select_tags))):
                if j>0:
                    res += ","
                res += item.name

        return res

class AccMsgMouldModelSerializer1(serializers.ModelSerializer):

    class Meta:
        model = AccMsgMould
        fields = '__all__'

class AccMsgMouldModelSerializer(serializers.ModelSerializer):

    mould_data = serializers.SerializerMethodField()
    mould_skip = serializers.SerializerMethodField()

    def get_mould_skip(self,obj):
        d={}
        if obj.mould_skip[0]:
            if obj.mould_skip[0] == '0':
                d['skip_type'] =obj.mould_skip[0]
                d['skip_url'] = obj.mould_skip[1:]
            else:
                d['skip_type'] =obj.mould_skip[0]
                d['skip_appid'] = obj.mould_skip[1:].split("||")[0]
                d['skip_pagepath'] = obj.mould_skip[1:].split("||")[1]

        return d

    def get_mould_data(self,obj):
        r = json.loads(obj.mould_data)
        return r if len(r) else {}

    class Meta:
        model = AccMsgMould
        fields = '__all__'

class AccMsgMassSerializer(serializers.Serializer):

    id = serializers.IntegerField()
    acc = serializers.SerializerMethodField()
    msgtype = serializers.CharField()
    sendobjects_format = serializers.SerializerMethodField()
    power=serializers.CharField()
    sendtime_format = serializers.SerializerMethodField()
    send_count = serializers.IntegerField()
    status = serializers.CharField()

    def get_acc(self,obj):
        try:
            return AccSerializer(Acc.objects.get(accid=obj.accid), many=False).data
        except Acc.DoesNotExist:
            return {}

    def get_sendtime_format(self,obj):
        return UtilTime().timestamp_to_string(obj.sendtime)

    def get_sendobjects_format(self,obj):
        ut = UtilTime()
        res = ""
        if obj.type == '1':
            return "全部粉丝"
        else:
            if obj.select_sex == '1':
                res+='仅男性粉丝'
            elif  obj.select_sex == '2':
                res += '仅女性粉丝'
            elif obj.select_sex == '0':
                res += '未知性别'

            if len(obj.select_followtime):
                res += "{}-{}".format(ut.timestamp_to_string(obj.select_followtime.split('-')[0]),ut.timestamp_to_string(obj.select_followtime.split('-')[1]))

            if len(obj.select_province) and len(obj.select_city):
                res += "{}-{}".format(obj.select_province,obj.select_city)

            for j,item in enumerate(AccTag.objects.filter(id__in=json.loads(obj.select_tags))):
                if j>0:
                    res += ","
                res += item.name

        return res

class AccMsgMassModelSerializer1(serializers.ModelSerializer):

    class Meta:
        model = AccMsgMass
        fields = '__all__'

class AccMsgMassModelSerializer(serializers.ModelSerializer):

    lists=serializers.SerializerMethodField()

    def get_lists(self,obj):

        aqlObj = AccQrcodeList.objects.get(id=json.loads(obj.listids[0]))
        if obj.msg_type != '1':
            return  AccQrcodeListModelSerializer1(aqlObj, many=False).data
        else:
            return WechatMaterial(accid=obj.accid).get_forever(
                media_id=aqlObj.media
            )

    class Meta:
        model = AccMsgMass
        fields = '__all__'

class AccCountBaseSerializer(serializers.Serializer):

    xz_num  = serializers.IntegerField()
    xz_add_rate = serializers.DecimalField(max_digits=18,decimal_places=2)
    qg_num = serializers.IntegerField()
    qg_add_rate = serializers.DecimalField(max_digits=18,decimal_places=2)
    jz_num = serializers.IntegerField()
    jz_add_rate = serializers.DecimalField(max_digits=18,decimal_places=2)
    hy_num = serializers.IntegerField()
    hy_add_rate = serializers.DecimalField(max_digits=18,decimal_places=2)
    tot_fs_num = serializers.IntegerField()
    tot_fs_add_rate = serializers.DecimalField(max_digits=18,decimal_places=2)
    yd_num = serializers.IntegerField()
    yd_add_rate = serializers.DecimalField(max_digits=18,decimal_places=2)


class AccCountBaseSerializer1(serializers.Serializer):

    date = serializers.CharField()
    hy_num = serializers.IntegerField()
    seven_day_fs_num = serializers.IntegerField()
    fifteen_day_fs_num = serializers.IntegerField()
    tot_fs_num = serializers.IntegerField()

    hy_rate = serializers.SerializerMethodField()

    def get_hy_rate(self,obj):
        return round(obj.hy_num * 100.0 / obj.tot_fs_num,2)

