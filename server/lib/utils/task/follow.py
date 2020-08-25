

from lib.utils.task.base import TaskBase
from app.wechat.models import Acc,AccFollow,AccQrcodeList,AccQrcodeImageTextList

from lib.utils.log import logger

class Follow(TaskBase):

    def sendtask(self,obj,nickname,openid,accid):

        self.request_handler(
            method='POST',
            url="{}/v1/taskapi/task/follow".format(self.url),
            json={
                "data":{
                    "obj":obj,
                    "nickname":nickname,
                    "openid":openid,
                    "accid":accid
                }
            }
        )


    def sendmsg(self,listid,nickname,openid,accid):
        from lib.utils.wechat.msg import WeChatAccEvent
        logger.info("{},{},{},{}".format(listid,nickname,openid,accid))
        WeChatAccEvent(accid=accid).\
            msgHandler(
                send_type='2',
                listids=[listid],
                isSend=True,
                user={
                    "openid": openid,
                    "nickname": nickname
                }
            )

