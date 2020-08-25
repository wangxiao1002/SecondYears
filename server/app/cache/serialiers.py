
from rest_framework import serializers

from lib.utils.mytime import UtilTime
from lib.utils.exceptions import PubErrorCustom
from app.user.models import Users,Role

class UserModelSerializerToRedis(serializers.ModelSerializer):

    role = serializers.SerializerMethodField()
    createtime_format = serializers.SerializerMethodField()
    bal = serializers.SerializerMethodField()

    def get_role(self,obj):
        try:
            roleObj = Role.objects.get(rolecode=obj.rolecode)
            return RoleModelSerializerToRedis(roleObj,many=False).data
        except Role.DoesNotExist:
            raise PubErrorCustom("无此角色信息!")

    def get_createtime_format(self,obj):
        return UtilTime().timestamp_to_string(obj.createtime)

    def get_bal(self,obj):
        return round(float(obj.bal),2)

    class Meta:
        model = Users
        fields = '__all__'

class RoleModelSerializerToRedis(serializers.ModelSerializer):

    class Meta:
        model = Role
        fields = '__all__'