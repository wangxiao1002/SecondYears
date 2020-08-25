

from lib.utils.task.base import TaskBase

class Reply(TaskBase):

    def sendtask(self,obj,nickname,openid,accid):

        self.request_handler(
            method='POST',
            url="{}/v1/taskapi/task/reply".format(self.url),
            json={
                "data":{
                    "obj":obj,
                    "nickname":nickname,
                    "openid":openid,
                    "accid":accid
                }
            }
        )


    def sendmsg(self,count,listids,send_type,nickname,openid,accid):

        from lib.utils.wechat.msg import WeChatAccEvent
        WeChatAccEvent(accid=accid).\
            msgHandler(
                send_type=send_type,
                listids=listids,
                count=count,
                isSend=True,
                runS=True,
                user={
                    "openid": openid,
                    "nickname": nickname
                }
            )