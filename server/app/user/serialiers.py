
import json
from rest_framework import serializers
from app.user.models import Users,Role
from lib.utils.mytime import UtilTime

class RoleModelSerializer(serializers.ModelSerializer):

    class Meta:
        model = Role
        fields = '__all__'

class UsersSerializers(serializers.Serializer):

    userid = serializers.IntegerField()
    pic = serializers.CharField()
    name = serializers.CharField()
    login_name = serializers.SerializerMethodField()

    def get_login_name(self,obj):
        return obj.uuid


class UsersModelSerializer(serializers.ModelSerializer):

    createtime_format = serializers.SerializerMethodField()
    bal = serializers.DecimalField(max_digits=16, decimal_places=2)

    rolename = serializers.SerializerMethodField()


    def get_rolename(self,obj):
        try:
            roleObj = Role.objects.get(rolecode=obj.rolecode)
            return roleObj.name
        except Role.DoesNotExist:
            return "未知"

    def get_createtime_format(self,obj):
        return UtilTime().timestamp_to_string(obj.createtime)

    class Meta:
        model = Users
        fields = '__all__'