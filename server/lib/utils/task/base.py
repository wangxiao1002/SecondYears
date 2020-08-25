
import json
from requests import request

from project.config_include.params import TASKURL
from lib.utils.log import logger
from lib.utils.exceptions import PubErrorCustom


class TaskBase(object):

    def __init__(self):

        self.url = TASKURL


    def request_handler(self,**kwargs):

        logger.info("请求任务服务器: \nmethod->{}\nurl->{}\njson->{}\ndata->{}\n".format(
            kwargs.get("method"),
            kwargs.get("url"),
            kwargs.get("json"),
            kwargs.get("data")
        ))

        response = request(method=kwargs.get("method"),
                           url=kwargs.get("url"),
                           json=kwargs.get("json"),
                           files=kwargs.get("files"),
                           data=kwargs.get("data"))
        logger.info("任务服务器返回:{}".format(response.text))
        response = json.loads(response.content.decode('utf-8'))

        if response.get("code",None) == '10000':
            return response
        else:
            raise PubErrorCustom(response.get("msg"))

