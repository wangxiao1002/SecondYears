
import json
from lib.utils.wechat.base import WechatBase

class MouldMsg(WechatBase):

    def __init__(self,**kwargs):
        super().__init__(**kwargs)

    def get_list(self,template_id=None):

        response = self.request_handler(
            method="GET",
            url="https://api.weixin.qq.com/cgi-bin/template/get_all_private_template?access_token={}".format(self.auth_accesstoken),
        )
        if not template_id:
            return response['template_list']
        else:
            fRes = list(filter(lambda x: x['template_id'] == template_id, response['template_list']))
            return fRes[0] if len(fRes) else None

    def send_msg(self,data):
        data = json.dumps(data, ensure_ascii=False).encode('utf-8')
        self.request_handler(
            method="POST",
            url="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token={}".format(
                self.auth_accesstoken),
            data=data
        )