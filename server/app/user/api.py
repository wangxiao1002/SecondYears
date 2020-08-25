
from project.config_include.common import ServerUrl
from rest_framework import viewsets
from rest_framework.decorators import list_route

from lib.core.decorator.response import Core_connector
from lib.utils.exceptions import PubErrorCustom

from app.user.serialiers import UsersModelSerializer,RoleModelSerializer,UsersSerializers
from app.user.models import Users,Role
from lib.utils.log import logger

class UserAPIView(viewsets.ViewSet):

    @list_route(methods=['GET'])
    @Core_connector(isTicket=True)
    def userinfo(self,request, *args, **kwargs):

        return {"data": {
            "userid": request.user.get("userid"),
            "loginname": request.user.get("uuid"),
            "username": request.user.get("name"),
            "rolecode":request.user.get("rolecode"),
            "avatar": ServerUrl+'/static/images/pic.jpg',
            "menu": []
        }}


    @list_route(methods=['POST'])
    @Core_connector(isTicket=True,isTransaction=True)
    def upd_passwd(self,request):

        try:
            user = Users.objects.get(userid=request.user['userid'])
        except Users.DoesNotExist:
            raise PubErrorCustom("该用户不存在!")

        user.passwd = request.data_format.get("passwd")
        if not user.passwd:
            raise PubErrorCustom("密码不能为空!")
        user.save()

    @list_route(methods=['POST',"PUT","DELETE","GET"])
    @Core_connector(isPagination=True)
    def userHandler(self,request):
        logger.info(request.user)
        if request.user['rolecode'] != 1000:
            raise PubErrorCustom("只有超级管理员能操作!")

        if request.method =='POST':
            # userid = request.data_format.get('userid')
            uuid = request.data_format.get("login_name")
            name = request.data_format.get("name")
            pic = request.data_format.get("pic")
            passwd = request.data_format.get("passwd")

            if not uuid:
                raise PubErrorCustom('登录名称不能为空!')

            if not name:
                name = uuid

            user = Users.objects.create(**{
                "uuid":uuid,
                "mobile":uuid,
                "rolecode":1001,
                "name":name,
                "passwd":passwd,
                "pic":pic
            })
            user.userid=user.id
            user.save()
        elif request.method =='PUT':
            userid = request.data_format.get('userid')
            uuid = request.data_format.get("login_name")
            name = request.data_format.get("name")
            pic = request.data_format.get("pic")
            passwd = request.data_format.get("passwd")

            try:
                user = Users.objects.get(userid=userid)
            except Users.DoesNotExist:
                raise PubErrorCustom('该用户不存在!')

            user.uuid = uuid if uuid else user.uuid
            user.mobile = uuid if uuid else user.uuid
            user.name = name if name else user.name
            user.pic = pic if pic else user.pic
            user.passwd = passwd
            user.save()
        elif request.method =='DELETE':
            Users.objects.filter(userid=request.data_format.get('userid')).delete()
        elif request.method =='GET':
            return {"data":UsersSerializers(Users.objects.filter(rolecode=1001).order_by('-createtime')[request.page_start:request.page_end],many=True).data}
