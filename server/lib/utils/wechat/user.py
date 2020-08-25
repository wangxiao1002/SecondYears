


from lib.utils.wechat.base import WechatBase

class WechatAccUser(WechatBase):

    def __init__(self,**kwargs):

        self.auth_accesstoken = kwargs.get("auth_accesstoken",None)
        super().__init__(**kwargs)

    def get_info(self,openid):
        return self.request_handler(
            method="GET",
            url="https://api.weixin.qq.com/cgi-bin/user/info?access_token={}&openid={}&lang=zh_CN".format(
                self.auth_accesstoken,
                openid
            ))

    def get_user_list(self,next_openid=False):
        return self.request_handler(
                method="GET",
                url="https://api.weixin.qq.com/cgi-bin/user/get?access_token={}&next_openid={}".format(
                    self.auth_accesstoken,
                    next_openid
                ) if next_openid else "https://api.weixin.qq.com/cgi-bin/user/get?access_token={}".format(
                    self.auth_accesstoken)
            )

class WeChatAccTag(WechatBase):

    def __init__(self,**kwargs):

        self.auth_accesstoken = kwargs.get("auth_accesstoken",None)
        super().__init__(**kwargs)

    def create(self,name):

        response = self.request_handler(
            method="POST",
            url="https://api.weixin.qq.com/cgi-bin/tags/create?access_token={}".format(
                self.auth_accesstoken
            ),
            json={"tag":{"name":name}})
        return response['tag']['id']

    def update(self,id,name):

        self.request_handler(
            method="POST",
            url="https://api.weixin.qq.com/cgi-bin/tags/update?access_token={}".format(
                self.auth_accesstoken
            ),
            json={"tag":{"id":id,"name":name}})

    def delete(self,id):

        self.request_handler(
            method="POST",
            url="https://api.weixin.qq.com/cgi-bin/tags/delete?access_token={}".format(
                self.auth_accesstoken
            ),
            json={"tag":{"id":id}})


    def batchtagging(self,openids,tagid):

        self.request_handler(
            method="POST",
            url="https://api.weixin.qq.com/cgi-bin/tags/members/batchtagging?access_token={}".format(
                self.auth_accesstoken
            ),
            json={
                "tagid" : tagid,
                "openid_list":openids
            })

    def batchtagging_canle(self,openids,tagid):

        self.request_handler(
            method="POST",
            url="https://api.weixin.qq.com/cgi-bin/tags/members/batchuntagging?access_token={}".format(
                self.auth_accesstoken
            ),
            json={
                "tagid" : tagid,
                "openid_list":openids
            })

    def get_tag_list(self):

        return self.request_handler(
            method="POST",
            url="https://api.weixin.qq.com/cgi-bin/tags/get?access_token={}".format(
                self.auth_accesstoken
            ))