
import json
from requests import request

from lib.utils.wechat.base import WechatBaseForUser
from lib.utils.exceptions import PubErrorCustom


class WechatQrcode(WechatBaseForUser):

    def __init__(self,**kwargs):

        accid = kwargs.get("accid",None)
        if not accid:
            raise PubErrorCustom("accid为空!")

        super().__init__(accid=accid)


    # def image_text_create(self,articles):
    #     """
    #     新增永久图文素材
    #     :param obj:
    #     :return:
    #     """
    #
    #     response = self.request_handler(method="POST",
    #                        url="https://api.weixin.qq.com/cgi-bin/material/add_news?access_token={}".format(
    #                            self.auth_accesstoken),
    #                        json={
    #                             "articles":articles
    #                        })
    #
    #     return response['media_id']


    def qrcode_create(self,id):
        """
        生成临时二维码
        :return:
        """
        response = self.request_handler(method="POST",
                           url="https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token={}".format(
                               self.auth_accesstoken),
                           json={
                               "expire_seconds": 2592000,
                               "action_name":"QR_STR_SCENE",
                               "action_info": {"scene": {"scene_str": str(id)}}
                           })
        return response['url']

    def qrcode_create_forever(self,id):
        """
        生成永久二维码
        :return:
        """
        response = self.request_handler(method="POST",
                           url="https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token={}".format(
                               self.auth_accesstoken),
                           json={
                               "action_name":"QR_LIMIT_STR_SCENE",
                               "action_info": {"scene": {"scene_str": str(id)}}
                           })
        return response['url']


