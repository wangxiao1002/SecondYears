

from lib.utils.task.base import TaskBase

class MsgCustomer(TaskBase):

    def sendtask_add(self,obj):

        self.request_handler(
            method='POST',
            url="{}/v1/taskapi/task/msgcustomer".format(self.url),
            json={
                "data":{
                    "obj":obj,
                }
            }
        )

    def sendtask_upd(self,obj):

        self.request_handler(
            method='PUT',
            url="{}/v1/taskapi/task/msgcustomer".format(self.url),
            json={
                "data":{
                    "obj":obj,
                }
            }
        )

    def sendtask_del(self,id):

        self.request_handler(
            method='DELETE',
            url="{}/v1/taskapi/task/msgcustomer".format(self.url),
            json={
                "data":{
                    "id":id,
                }
            }
        )


    def sendmsg(self,listids,nickname,openid,accid):

        from lib.utils.wechat.msg import WeChatAccEvent
        WeChatAccEvent(accid=accid).\
            msgHandler(
                send_type='1',
                listids=listids,
                isSend=True,
                runS=True,
                user={
                    "openid": openid,
                    "nickname": nickname
                }
            )