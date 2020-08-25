
import uuid,os
from rest_framework import viewsets
from rest_framework.decorators import list_route
from project.config_include.common import ServerUrl
from lib.core.decorator.response import Core_connector
from lib.utils.exceptions import PubErrorCustom
from project.settings import IMAGE_PATH,BASE_DIR
from lib.utils.log import logger
from lib.utils.wechat.material import WechatMaterial
from app.wechat.models import AccMsgMass
from app.public.models import Meterial
from app.public.serialiers import MeterialSerializer

class PublicAPIView(viewsets.ViewSet):

    @list_route(methods=['POST','OPTIONS'])
    @Core_connector()
    def file(self,request, *args, **kwargs):

        file_obj = request.FILES.get('filename')
        if file_obj:

            new_file = "{}_{}".format(uuid.uuid4().hex,file_obj.name)

            file_path =os.path.join(IMAGE_PATH, new_file)
            with open(file_path,'wb+') as f:
                for chunk in file_obj.chunks():
                    f.write(chunk)

            return {"data":{"path":"{}/static/images/{}".format(ServerUrl,new_file)}}

        else:
            raise PubErrorCustom("文件上传失败!")

    @list_route(methods=['POST', 'OPTIONS'])
    @Core_connector(isTransaction=True)
    def meterial(self, request, *args, **kwargs):

        file_obj = request.FILES.get('filename')
        if file_obj:
            new_file = "{}_{}".format(uuid.uuid4().hex, file_obj.name)
            file_strem = file_obj.read()

            media_id, url = WechatMaterial(accid=request.data_format.get("accid", "")).create_forever(
                meterialObj={"file":file_strem,"filename":new_file},
                type=request.data_format.get("type", ""),
                title=request.data_format.get("title", ""),
                introduction=request.data_format.get("introduction", "")
            )
            return {"data": {"path":url,"media_id":media_id}}
        else:
            raise PubErrorCustom("文件上传失败!")

    @list_route(methods=['GET'])
    @Core_connector(isPagination=True)
    def meterial_get(self, request, *args, **kwargs):


        response = WechatMaterial(accid=request.query_params_format.get("accid", "")).get_forever_list(
            type = request.query_params_format.get("type",None),
            offset=request.offset,
            count=request.count
        )

        return {"data":response['item'],"count":response['total_count']}

    @list_route(methods=['GET'])
    @Core_connector()
    def meterialDetail_get(self, request, *args, **kwargs):

        response = WechatMaterial(accid=request.query_params_format.get("accid", "")).get_forever(
            media_id=request.query_params_format.get("media_id", "")
        )

        return response


    @list_route(methods=['DELETE'])
    @Core_connector(isTransaction=True)
    def meterial_delete(self, request, *args, **kwargs):

        WechatMaterial(accid=obj.accid).delete_forever(request.data_format.get("media_id",0))

        return None