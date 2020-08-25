
from rest_framework import viewsets
from rest_framework.decorators import list_route

from lib.core.decorator.response import Core_connector
from lib.utils.exceptions import PubErrorCustom
from lib.utils.db import RedisTokenHandler
from lib.utils.string_extension import get_token
from lib.utils.http_request import send_request_other

from app.user.models import Users
from app.user.serialiers import UsersSerializers
from app.cache.serialiers import UserModelSerializerToRedis


class SsoAPIView(viewsets.ViewSet):


    @list_route(methods=['POST'])
    @Core_connector(isTransaction=True,isTicket=False)
    def login(self,request, *args, **kwargs):

        """
        登录
        :param request:
        :param args:
        :param kwargs:
        :return:
        """

        try:
            user = Users.objects.get(uuid=request.data_format.get("loginname"))
        except Users.DoesNotExist:
            raise PubErrorCustom("登录账户或密码错误！")

        if user.passwd != request.data_format.get("password"):
                raise PubErrorCustom("登录账户或密码错误！")

        if user.status == 1:
            raise PubErrorCustom("登陆账号已到期！")
        elif user.status == 2:
            raise PubErrorCustom("已冻结！")

        token = get_token()
        res = UserModelSerializerToRedis(user, many=False).data
        RedisTokenHandler(key=token).set(res)

        return {"data": token}


    #登出
    @list_route(methods=['POST'])
    @Core_connector(isTicket=True)
    def logout(self,request, *args, **kwargs):

        RedisTokenHandler(key=request.ticket).delete()
        return None

    #刷新token
    @list_route(methods=['POST'])
    @Core_connector(isTicket=True)
    def refeshToken(self,request, *args, **kwargs):

        redis_cli = RedisTokenHandler(key=request.ticket)
        res = redis_cli.get()
        redis_cli.delete()

        token = get_token()
        redis_cli = RedisTokenHandler(key=token)
        redis_cli.set(res)

        return { "data": token}