
from rest_framework import serializers
from project.config_include.common import ServerUrl
from lib.utils.mytime import UtilTime

class MeterialSerializer(serializers.Serializer):

    local_url = serializers.SerializerMethodField()
    title = serializers.CharField()
    introduction = serializers.CharField()
    media_id = serializers.CharField()
    url = serializers.CharField()
    createtime = serializers.SerializerMethodField()

    def get_createtime(self,obj):
        return UtilTime().timestamp_to_string(obj.createtime)

    def get_local_url(self,obj):

        # return "{}{}".format(ServerUrl,obj.local_url)
        return obj.url

