




from lib.utils.wechat.base import WechatBase


class WechatAccCount(WechatBase):

    def __init__(self,**kwargs):
        super().__init__(**kwargs)


    def getusersunmmary(self,begin_date,end_date):

        """
        获取用户增减数据
        :param begin_date:
        :param end_date:
        :return:
        """

        return self.request_handler(
            method="POST",
            url="https://api.weixin.qq.com/datacube/getusersummary?access_token={}".format(self.auth_accesstoken),
            json={
                "begin_date":begin_date,
                "end_date":end_date
            })['list']

    def getusercumulate(self,begin_date,end_date):

        """
        获取累计用户数据
        :param begin_date:
        :param end_date:
        :return:
        """

        return self.request_handler(
            method="POST",
            url="https://api.weixin.qq.com/datacube/getusercumulate?access_token={}".format(self.auth_accesstoken),
            json={
                "begin_date":begin_date,
                "end_date":end_date
            })['list']


    def getarticlesummary(self,begin_date,end_date):
        """
        获取图文群发每日数据
        :param begin_date:
        :param end_date:
        :return:
        """

        """
                    { 
                "list": [ 
                    { 
                        "ref_date": "2014-12-08", 
                        "msgid": "10000050_1", 
                        "title": "12月27日 DiLi日报", 
                        "int_page_read_user": 23676, 
                        "int_page_read_count": 25615, 
                        "ori_page_read_user": 29, 
                        "ori_page_read_count": 34, 
                        "share_user": 122, 
                        "share_count": 994, 
                        "add_to_fav_user": 1, 
                        "add_to_fav_count": 3
                    } 
                 //后续会列出该日期内所有被阅读过的文章（仅包括群发的文章）在当天的阅读次数等数据
                ]
            }
        """
        return self.request_handler(
            method="POST",
            url="https://api.weixin.qq.com/datacube/getarticlesummary?access_token={}".format(self.auth_accesstoken),
            json={
                "begin_date": begin_date,
                "end_date": end_date
            })['list']


    def getarticletotal(self,begin_date,end_date):
        """
        获取图文群发总数据
        :param begin_date:
        :param end_date:
        :return:
        """

        return self.request_handler(
            method="POST",
            url="https://api.weixin.qq.com/datacube/getarticletotal?access_token={}".format(self.auth_accesstoken),
            json={
                "begin_date": begin_date,
                "end_date": end_date
            })['list']
