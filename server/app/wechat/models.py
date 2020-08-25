from django.db import models

from lib.utils.mytime import UtilTime

class Acc(models.Model):

    """
    公众号列表
    """

    accid=models.BigAutoField(primary_key=True)

    authorizer_appid = models.CharField(max_length=60,verbose_name="授权方appid")
    authorizer_refresh_token = models.CharField(max_length=60,verbose_name="刷新令牌")

    nick_name = models.CharField(max_length=60,verbose_name="昵称",default="")
    head_img = models.CharField(max_length=255,verbose_name="头像",default="")
    service_type_info  = models.CharField(max_length=255,verbose_name="公众号类型",default="{}")
    verify_type_info  = models.CharField(max_length=255,verbose_name="公众号认证类型",default="{}")

    user_name = models.CharField(max_length=60,verbose_name="原始ID",default="")
    principal_name = models.CharField(max_length=60,verbose_name="主体名称",default="")
    alias = models.CharField(max_length=60,verbose_name="公众号所设置的微信号，可能为空",default="")
    business_info = models.CharField(max_length=255,verbose_name="用以了解功能的开通状况（0代表未开通，1代表已开通）",default="{}")
    qrcode_url = models.CharField(max_length=255,verbose_name="二维码图片的 URL，开发者最好自行也进行保存",default="")

    synctime = models.BigIntegerField(default=0,verbose_name="上次同步粉丝列表时间")

    follow_setting = models.CharField(max_length=1,verbose_name="关注设置标志:'0'-设置,'1'-未设置",default='1')
    reply_setting = models.CharField(max_length=1,verbose_name="智能回复设置标志:'0'-设置,'1'-未设置",default='1')

    createtime=models.BigIntegerField(default=0)

    send_type= None
    listids = None
    nosend_limit = None

    def save(self, *args, **kwargs):

        if not self.createtime:
            self.createtime = UtilTime().timestamp
        return super(Acc, self).save(*args, **kwargs)

    class Meta:
        verbose_name = '公众号列表'
        verbose_name_plural = verbose_name
        db_table = 'acc'


class AccLinkUser(models.Model):

    """
    公众号用户关联表
    """

    id=models.BigAutoField(primary_key=True)
    accid = models.BigIntegerField(verbose_name="公众号ID")
    openid = models.CharField(max_length=60,verbose_name="粉丝openid")
    tags = models.CharField(max_length=1024, verbose_name="标签集合", default="[]")
    createtime=models.BigIntegerField(default=0)

    headimgurl= models.CharField(max_length=255,verbose_name="头像",default="")
    nickname = models.CharField(max_length=60,verbose_name="昵称",default="")
    sex = models.CharField(max_length=1,verbose_name="用户的性别，值为1时是男性，值为2时是女性，值为0时是未知",default="")
    subscribe_time = models.BigIntegerField(default=0,verbose_name="关注时间")
    city =models.CharField(max_length=60,verbose_name="城市",default="")
    country = models.CharField(max_length=60,verbose_name="国家",default="")
    province = models.CharField(max_length=60,verbose_name="省份",default="")
    subscribe_scene = models.CharField(max_length=60,verbose_name="ADD_SCENE_SEARCH 公众号搜索，ADD_SCENE_ACCOUNT_MIGRATION 公众号迁移，ADD_SCENE_PROFILE_CARD 名片分享，ADD_SCENE_QR_CODE 扫描二维码，ADD_SCENE_PROFILE_LINK 图文页内名称点击，ADD_SCENE_PROFILE_ITEM 图文页右上角菜单，ADD_SCENE_PAID 支付后关注，ADD_SCENE_OTHERS 其他",default="")
    memo = models.CharField(max_length=60,verbose_name="备注",default="")

    umark = models.CharField(max_length=1,verbose_name="状态 0-正常,1-删除",default='0')

    last_active_time = models.BigIntegerField(default=0,
                                              verbose_name="""
                                              上次活跃时间
                                              (包括粉丝发送消息给公众号、 点击自定义菜单、关注公众号、
                                               扫描二维码、 支付成功、用户维权等，阅读公众号文章不算互动） 的粉丝数。
                                              """)

    def save(self, *args, **kwargs):

        if not self.createtime:
            self.createtime = UtilTime().timestamp
        return super(AccLinkUser, self).save(*args, **kwargs)

    class Meta:
        verbose_name = '公众号用户关联表'
        verbose_name_plural = verbose_name
        db_table = 'acclinkuser'



class AccTag(models.Model):
    """
    标签
    """

    id = models.BigAutoField(primary_key=True)

    name = models.CharField(max_length=60,default="",verbose_name="标签名字")
    accid = models.BigIntegerField(verbose_name="公众号ID")
    fans_count = models.IntegerField(default=0,verbose_name="粉丝数量")

    wechat_fans_count = models.IntegerField(default=0,verbose_name="微信粉丝数量")

    createtime = models.BigIntegerField(default=0)

    umark = models.CharField(max_length=1, verbose_name="状态 0-正常,1-删除", default='0')

    def save(self, *args, **kwargs):

        ut =  UtilTime()
        if not self.createtime:
            self.createtime = ut.timestamp
        return super(AccTag, self).save(*args, **kwargs)

    class Meta:
        verbose_name = '标签'
        verbose_name_plural = verbose_name
        db_table = 'acctag'


class AccQrcode(models.Model):
    """
    渠道二维码
    """

    id = models.BigAutoField(primary_key=True)

    name = models.CharField(max_length=60,default="",verbose_name="二维码名称")
    accid = models.BigIntegerField(verbose_name="公众号ID")
    tot_count = models.IntegerField(verbose_name="总扫码次数(既扫码也关注)",default=0)
    new_count = models.IntegerField(verbose_name="新扫码且关注",default=0)
    follow_count = models.IntegerField(verbose_name="已关注扫码",default=0)
    type = models.CharField(max_length=1,verbose_name="类型,0-临时,1-永久",default="1")
    endtime = models.BigIntegerField(default=0)

    qr_type = models.CharField(max_length=1,verbose_name="扫码推送:0-新建扫码推送消息,1-不启用",default="0")

    tags = models.CharField(max_length=1024,verbose_name="标签集合",default="[]")

    send_type = models.CharField(max_length=1,verbose_name="推送方式:0-随机推送一条,1-全部推送",default="0")

    url = models.CharField(max_length=255,verbose_name="二维码链接",default="")

    listids = models.CharField(max_length=1024,verbose_name="推送内容id集合",default='[]')

    createtime = models.BigIntegerField(default=0)

    def save(self, *args, **kwargs):

        ut =  UtilTime()
        if not self.createtime:
            self.createtime = ut.timestamp
        if self.type == '0':
            self.endtime = ut.today.shift(days=30).timestamp
        return super(AccQrcode, self).save(*args, **kwargs)

    class Meta:
        verbose_name = '渠道二维码'
        verbose_name_plural = verbose_name
        db_table = 'accqrcode'


class AccFollow(models.Model):
    """
    关注回复
    """


    id = models.BigAutoField(primary_key=True)
    accid = models.BigIntegerField(verbose_name="公众号ID")
    send_type = models.CharField(max_length=1,verbose_name="推送方式:0-全部推送,1-按顺序推送,2-随机推送一条",default="0")
    send_limit = models.CharField(max_length=20,verbose_name="顺序推送时，每条间隔的时间 1,H ->1小时 1,M -> 1分钟 1,S -> 1秒",default="")
    listids = models.CharField(max_length=1024,verbose_name="推送内容id集合",default='[]')
    createtime = models.BigIntegerField(default=0)


    nick_name = None
    head_img = None
    follow_setting = None

    def save(self, *args, **kwargs):

        ut =  UtilTime()
        if not self.createtime:
            self.createtime = ut.timestamp
        return super(AccFollow, self).save(*args, **kwargs)

    class Meta:
        verbose_name = '关注回复'
        verbose_name_plural = verbose_name
        db_table = 'accfollow'

class AccReply(models.Model):
    """
    智能回复
    """

    id = models.BigAutoField(primary_key=True)
    accid = models.BigIntegerField(verbose_name="公众号ID")
    send_type = models.CharField(max_length=1,verbose_name="推送方式:0-全部推送,1-按顺序推送,2-随机推送一条",default="0")
    send_limit = models.CharField(max_length=20,verbose_name="顺序推送时，每条间隔的时间 1,H ->1小时 1,M -> 1分钟 1,S -> 1秒",default="")
    nosend_limit = models.CharField(max_length=20,verbose_name="时间设置,多少未互动推送 1,H ->1小时 1,M -> 1分钟 1,S -> 1秒",default="")
    trigger = models.CharField(max_length=3,verbose_name="""
                                    触发条件,0-开启,1-关闭
                                        第一位:是否关注公众号
                                        第二位:发送消息给公众号
                                        第三位:点击菜单
                                """,default="111")
    quiet = models.CharField(max_length=60,default="",verbose_name="安静时间段")
    send_place = models.IntegerField(default=0,verbose_name="每天推送限制条数")
    listids = models.CharField(max_length=1024,verbose_name="推送内容id集合",default='[]')
    createtime = models.BigIntegerField(default=0)

    def save(self, *args, **kwargs):

        ut =  UtilTime()
        if not self.createtime:
            self.createtime = ut.timestamp
        return super(AccReply, self).save(*args, **kwargs)

    class Meta:
        verbose_name = '智能回复'
        verbose_name_plural = verbose_name
        db_table = 'accreply'

class AccSend(models.Model):
    """
    推送信息表
    """

    id = models.BigAutoField(primary_key=True)
    accid = models.BigIntegerField(verbose_name="公众号ID")
    send_type = models.CharField(max_length=1,
                                 verbose_name="""
                                    推送类型:
                                        0-扫二维码推消息,
                                        1-关注推消息,
                                        2-智能推消息
                                 """,default="0")
    cid = models.BigIntegerField(default=0,verbose_name="推送内容表ID")
    openid = models.CharField(max_length=60,verbose_name="openid",default="")
    date = models.CharField(max_length=10,verbose_name="日期",default="")
    createtime = models.BigIntegerField(default=0)

    send_count = None
    reply_count = None
    nick_name = None

    def save(self, *args, **kwargs):

        ut =  UtilTime()
        if not self.date:
            self.date = ut.arrow_to_string(format_v="YYYY-MM-DD")
        if not self.createtime:
            self.createtime = ut.timestamp
        return super(AccSend, self).save(*args, **kwargs)

    class Meta:
        verbose_name = '推送信息表'
        verbose_name_plural = verbose_name
        db_table = 'accsend'


class AccQrcodeList(models.Model):
    """
    渠道二维码推送内容
    """

    id = models.BigAutoField(primary_key=True)
    qrid = models.BigIntegerField(verbose_name="二维码ID")
    type = models.CharField(max_length=1,verbose_name="类型,1-图文,2-图片,3-文字,4-音频,5-视频")

    iamgetextids = models.CharField(max_length=1024,default="[]",verbose_name="图文列表ID")

    media_id = models.CharField(max_length=60,verbose_name="媒体ID/图文推送内容ID",default="")
    title = models.CharField(max_length=60,verbose_name="视频标题",default="")
    description = models.CharField(max_length=255, verbose_name="视频描述", default="")
    url = models.CharField(max_length=255,verbose_name="图片链接",default="")
    content = models.TextField(verbose_name="文字内容",default="")

    sort = models.IntegerField(default=0)

    class Meta:
        verbose_name = '渠道二维码推送内容'
        verbose_name_plural = verbose_name
        db_table = 'accqrcodelist'

class AccQrcodeImageTextList(models.Model):
    """
    渠道二维码图文推送内容
    """

    id = models.BigAutoField(primary_key=True)
    qr_listid = models.BigIntegerField(verbose_name="二维码推送内容ID")

    picurl = models.CharField(max_length=255,verbose_name="资源链接")
    media_id = models.CharField(max_length=60, verbose_name="媒体ID/资源链接", default="")
    url = models.CharField(max_length=255,verbose_name="点击图文跳转的链接")
    title = models.CharField(max_length=255,verbose_name="标题")
    description = models.TextField(verbose_name="描述,文字消息时,用此处值")

    sort = models.IntegerField(default=0)

    class Meta:
        verbose_name = '渠道二维码图文推送内容'
        verbose_name_plural = verbose_name
        db_table = 'accqrcodeimagetextlist'


class AccMsgCustomer(models.Model):
    """
    客服消息表
    """

    id = models.BigAutoField(primary_key=True)
    accids = models.CharField(max_length=1024,verbose_name="公众号ids",default="[]")
    name = models.CharField(max_length=60,default="",verbose_name="消息名称")
    listids = models.CharField(max_length=1024,verbose_name="推送内容id集合",default='[]')
    sendtime = models.BigIntegerField(default=0,verbose_name="发送时间")
    createtime = models.BigIntegerField(default=0)

    type = models.CharField(max_length=1,verbose_name="群发粉丝 '0'-按条件筛选,'1'-全部粉丝")
    status = models.CharField(max_length=1,verbose_name="""
                                            发送状态
                                                '0'-已发送,
                                                '1'-未发送,
                                                '2'-发送中,
                                                '3'-发送终止,
                                                '4'-发送失败
                                            """,default='1')

    select_sex = models.CharField(max_length=1,verbose_name="条件筛选->性别,值为1时是男性，值为2时是女性，值为0时是未知")
    select_followtime = models.CharField(max_length=60,verbose_name="条件筛选->关注时间",default="")
    select_province = models.CharField(max_length=60,verbose_name="条件筛选->省份",default="")
    select_city = models.CharField(max_length=60,verbose_name="条件筛选->城市",default="")
    select_tags = models.CharField(max_length=1024,verbose_name="条件筛选->标签集合",default="[]")

    send_count = models.IntegerField(verbose_name="发送成功人数",default=0)
    send_count1 = models.IntegerField(verbose_name="预估发送人数", default=0)

    def save(self, *args, **kwargs):

        ut =  UtilTime()
        if not self.createtime:
            self.createtime = ut.timestamp
        return super(AccMsgCustomer, self).save(*args, **kwargs)

    class Meta:
        verbose_name = '客服消息表'
        verbose_name_plural = verbose_name
        db_table = 'accmsgcustomer'

class AccMsgCustomerLinkAcc(models.Model):
    """
    客服消息公众号关联表
    """
    id = models.BigAutoField(primary_key=True)
    msgid = models.BigIntegerField(default=0,verbose_name="消息表ID")
    accid = models.BigIntegerField(default=0,verbose_name="公众号ID")
    send_flag = models.CharField(max_length=60,verbose_name="发送成功 0-发送成功,1-发送失败(失败理由),2-未开始",default="2")
    send_count = models.IntegerField(verbose_name="发送成功人数",default=0)
    send_count1 = models.IntegerField(verbose_name="预估发送人数", default=0)

    class Meta:
        verbose_name = '客服消息公众号关联表'
        verbose_name_plural = verbose_name
        db_table = 'accmsgcustomerlinkacc'

class AccMsgMould(models.Model):
    """
    模板消息表
    """

    id = models.BigAutoField(primary_key=True)
    accid = models.BigIntegerField(verbose_name="公众号ID")
    name = models.CharField(max_length=60,default="",verbose_name="消息名称")
    mould_id = models.CharField(max_length=60,verbose_name="模板ID",default="")
    mould_name = models.CharField(max_length=60,default="",verbose_name="模板名称")
    mould_data = models.CharField(max_length=4089,default="{}",verbose_name="模板数据")
    mould_skip = models.CharField(max_length=255,verbose_name="跳转",default="")
    sendtime = models.BigIntegerField(default=0, verbose_name="发送时间")
    send_count = models.IntegerField(verbose_name="发送人数", default=0)
    status = models.CharField(max_length=1,verbose_name="""
                                            发送状态
                                                '0'-已发送,
                                                '1'-未发送,
                                                '2'-发送中,
                                                '3'-发送终止,
                                                '4'-发送失败
                                            """,default='1')

    type = models.CharField(max_length=1, verbose_name="群发粉丝 '0'-全部,'1'-选择标签,'2'-选择性别,'3'-选择地域",default='0')
    select_sex = models.CharField(max_length=1,verbose_name="条件筛选->性别,值为1时是男性，值为2时是女性，值为0时是未知")
    select_tags = models.CharField(max_length=1024,verbose_name="条件筛选->标签集合",default="[]")
    select_province = models.CharField(max_length=60,verbose_name="条件筛选->省份",default="")
    select_city = models.CharField(max_length=60,verbose_name="条件筛选->城市",default="")

    createtime = models.BigIntegerField(default=0)

    def save(self, *args, **kwargs):

        if not self.createtime:
            ut = UtilTime()
            self.createtime = ut.timestamp
        return super(AccMsgMould, self).save(*args, **kwargs)

    class Meta:
        verbose_name = '模板消息表'
        verbose_name_plural = verbose_name
        db_table = 'accmsgmould'


class AccMsgMass(models.Model):
    """
    群发消息
    """

    id = models.BigAutoField(primary_key=True)
    accid = models.BigIntegerField(verbose_name="公众号ID")
    sendtime = models.BigIntegerField(default=0, verbose_name="发送时间")
    send_count = models.IntegerField(verbose_name="发送人数", default=0)
    status = models.CharField(max_length=1,verbose_name="""
                                            发送状态
                                                '0'-已发送,
                                                '1'-未发送,
                                                '2'-发送中,
                                                '3'-发送终止,
                                                '4'-发送失败
                                            """,default='1')

    type = models.CharField(max_length=1,verbose_name="群发粉丝 '0'-按条件筛选,'1'-全部粉丝")
    select_sex = models.CharField(max_length=1, verbose_name="条件筛选->性别,值为1时是男性，值为2时是女性，值为0时是未知")
    select_followtime = models.CharField(max_length=60, verbose_name="条件筛选->关注时间", default="")
    select_province = models.CharField(max_length=60, verbose_name="条件筛选->省份", default="")
    select_city = models.CharField(max_length=60, verbose_name="条件筛选->城市", default="")
    select_tags = models.CharField(max_length=1024, verbose_name="条件筛选->标签集合", default="[]")
    power = models.CharField(max_length=1,verbose_name="微信后台权限 0-出现在微信后台已群发消息,1-不出现在微信后台已群发消息",default='1')
    repeat_send = models.CharField(max_length=1,verbose_name="转发继续发送 0-是,1-否",default='0')
    mobile = models.CharField(max_length=20,verbose_name="手机号",default="")
    listids = models.CharField(max_length=1024,verbose_name="IDs",default='[]')

    msgtype =models.CharField(max_length=1,verbose_name="类型,1-图文,2-图片,3-文字,4-音频,5-视频")

    createtime = models.BigIntegerField(default=0)

    def save(self, *args, **kwargs):

        if not self.createtime:
            ut = UtilTime()
            self.createtime = ut.timestamp
        return super(AccMsgMass, self).save(*args, **kwargs)

    class Meta:
        verbose_name = '群发消息'
        verbose_name_plural = verbose_name
        db_table = 'accmsgmass'


class AccCount(models.Model):
    """
    数据统计
    """

    id = models.BigAutoField(primary_key=True)
    accid = models.BigIntegerField(verbose_name="公众号ID")
    xz_num = models.IntegerField(verbose_name="新增数量", default=0)
    qg_num = models.IntegerField(verbose_name="取关数量",default=0)
    hy_num = models.IntegerField(verbose_name="活跃数量",default=0)
    tot_fs_num = models.IntegerField(verbose_name="总粉丝数量",default=0)
    yd_num = models.IntegerField(verbose_name="阅读数量", default=0)
    jz_num = models.IntegerField(verbose_name="净增数量",default=0)

    jz_add_rate = models.DecimalField(verbose_name="新增比例", max_digits=18,decimal_places=6, default=0.0)
    xz_add_rate = models.DecimalField(verbose_name="新增比例", max_digits=18,decimal_places=6, default=0.0)
    qg_add_rate = models.DecimalField(verbose_name="取关比例", max_digits=18,decimal_places=6, default=0.0)
    hy_add_rate = models.DecimalField(verbose_name="活跃比例", max_digits=18, decimal_places=6, default=0.0)
    tot_fs_add_rate = models.DecimalField(verbose_name="总粉丝比例", max_digits=18, decimal_places=6, default=0.0)
    yd_add_rate = models.DecimalField(verbose_name="阅读比例", max_digits=18, decimal_places=6, default=0.0)

    seven_day_fs_num = models.IntegerField(verbose_name="7天内互动粉丝数量", default=0)
    fifteen_day_fs_num = models.IntegerField(verbose_name="15天内互动粉丝数量", default=0)

    date = models.CharField(max_length=10,default="",verbose_name="日期")

    def save(self, *args, **kwargs):
        if not self.date:
            ut = UtilTime()
            self.date = ut.arrow_to_string(format_v="YYYY-MM-DD")
        return super(AccCount, self).save(*args, **kwargs)

    class Meta:
        verbose_name = '数据统计'
        verbose_name_plural = verbose_name
        db_table = 'acccount'


class AccActionCount(models.Model):
    """
    粉丝动作统计
    """

    id = models.BigAutoField(primary_key=True)
    accid = models.BigIntegerField(verbose_name="公众号ID")
    openid = models.CharField(max_length=60, verbose_name="粉丝openid")
    action =  models.CharField(max_length=1,verbose_name="0-粉丝消息,1-新关注,2-关注,3-取关,4-扫描二维码,5-菜单点击")

    createtime = models.BigIntegerField(default=0)

    count = None
    sex = None

    def save(self, *args, **kwargs):

        if not self.createtime:
            ut = UtilTime()
            self.createtime = ut.timestamp
        return super(AccActionCount, self).save(*args, **kwargs)

    class Meta:
        verbose_name = '粉丝动作统计'
        verbose_name_plural = verbose_name
        db_table = 'accactioncount'