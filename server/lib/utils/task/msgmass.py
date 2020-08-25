

from lib.utils.task.base import TaskBase
from lib.utils.log import logger

class MsgMass(TaskBase):

    def sendtask_add(self,obj):

        self.request_handler(
            method='POST',
            url="{}/v1/taskapi/task/msgmass".format(self.url),
            json={
                "data":{
                    "obj":obj,
                }
            }
        )

    def sendtask_upd(self,obj):

        self.request_handler(
            method='PUT',
            url="{}/v1/taskapi/task/msgmass".format(self.url),
            json={
                "data":{
                    "obj":obj,
                }
            }
        )

    def sendtask_del(self,id):

        self.request_handler(
            method='DELETE',
            url="{}/v1/taskapi/task/msgmass".format(self.url),
            json={
                "data":{
                    "id":id,
                }
            }
        )


    def send_msg(self,listids,openids,is_to_all,send_ignore_reprint,accid):

        from lib.utils.wechat.msg import WechatAccMassMsg
        logger.info("{}-{}-{}-{}-{}".format(listids,openids,is_to_all,send_ignore_reprint,accid))
        WechatAccMassMsg(accid=accid).\
            run(
                listids=listids,
                openids=openids,
                is_to_all=is_to_all,
                send_ignore_reprint=send_ignore_reprint
            )