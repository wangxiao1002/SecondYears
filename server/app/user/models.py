from django.db import models

from lib.utils.mytime import UtilTime
from lib.utils.string_extension import md5pass

class Login(models.Model):

    userid=models.IntegerField()
    createtime=models.BigIntegerField(default=0)
    ip = models.CharField(max_length=255,default='')
    user_agent = models.CharField(max_length=255,default='')

    def save(self, *args, **kwargs):
        if not self.createtime:
            self.createtime = UtilTime().timestamp
        return super(Login, self).save(*args, **kwargs)

    class Meta:
        verbose_name = '用户登录表'
        verbose_name_plural = verbose_name
        db_table = 'login'

class Users(models.Model):

    id=  models.BigAutoField(primary_key=True)

    userid = models.BigIntegerField(verbose_name="用户ID")
    rolecode = models.IntegerField(verbose_name="角色代码")

    name = models.CharField(max_length=120, verbose_name="名称", default='', null=True)
    passwd = models.CharField(max_length=60,verbose_name='密码',default='')
    pay_passwd = models.CharField(max_length=60,verbose_name='支付密码',default='')
    addr = models.CharField(max_length=255, verbose_name="地址", default='', null=True)
    sex = models.CharField(max_length=10, verbose_name="性别", default='', null=True)

    uuid =  models.CharField(max_length=60, verbose_name="用户小程序ID/用户账号", default='', null=True)
    mobile = models.CharField(max_length=60, verbose_name="手机号", default='', null=True)

    pic=models.CharField(max_length=255,verbose_name="头像地址",default='')

    status = models.CharField(max_length=1,default='0',verbose_name="状态:0-正常，1-到期,2-冻结",null=True)

    bal = models.DecimalField(max_digits=18,decimal_places=6,default=0.000,verbose_name="余额")
    createtime=models.BigIntegerField(default=0)

    def save(self, *args, **kwargs):

        if not self.createtime:
            self.createtime = UtilTime().timestamp

        if not self.passwd:
            self.passwd = md5pass('123456')
        if not self.pay_passwd:
            self.pay_passwd = md5pass('123456')
        return super(Users, self).save(*args, **kwargs)

    class Meta:
        verbose_name = '用户表'
        verbose_name_plural = verbose_name
        db_table = 'user'

class Role(models.Model):

    id = models.AutoField(primary_key=True)
    rolecode =  models.CharField(max_length=4,default='')
    roletype = models.CharField(max_length=1,default='0',verbose_name="1-管理员,2-机构,3-商户,4-用户")
    name = models.CharField(max_length=60,default='')

    """
    1000 - 系统管理员
    1001 - 普通管理员 
    2001 - 机构代理
    3001 - 商户管理员
    3002 - 商户销售
    4001 - 微信(大众)用户
    4002 - 微信(医生)
    4003 - 微信(老师)
    """

    class Meta:
        verbose_name = '角色表'
        verbose_name_plural = verbose_name
        db_table = 'role'