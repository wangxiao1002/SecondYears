
from django.db import models
from lib.utils.mytime import UtilTime

class Meterial(models.Model):

    """
    素材库
    """

    id = models.BigAutoField(primary_key=True)
    type = models.CharField(max_length=1, verbose_name="类型,1-图文,2-图片,4-音频,5-视频,6-缩略图")
    media_id = models.CharField(max_length=60,verbose_name="微信媒体库ID")

    accid = models.BigIntegerField()

    local_url = models.CharField(max_length=255,verbose_name="本地地址")

    url = models.CharField(max_length=255,verbose_name="图片url 仅图片")
    title = models.CharField(max_length=255,verbose_name="视频素材的标题",default="")
    introduction = models.TextField(verbose_name="视频素材的描述",default="")
    createtime = models.BigIntegerField(default=0)

    def save(self, *args, **kwargs):

        ut =  UtilTime()
        if not self.createtime:
            self.createtime = ut.timestamp
        return super(Meterial, self).save(*args, **kwargs)

    class Meta:
        verbose_name = '素材库'
        verbose_name_plural = verbose_name
        db_table = 'meterial'