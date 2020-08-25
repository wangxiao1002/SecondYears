

import os
import sys
import django
from django.db import transaction

pathname = os.path.dirname(os.path.abspath(__file__))
sys.path.insert(0, pathname)
sys.path.insert(0,os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

os.environ.setdefault("DJANGO_SETTINGS_MODULE", "project.settings")

django.setup()

import json
from app.wechat.models import AccTag
from lib.utils.wechat.user import WeChatAccTag
from lib.utils.log import logger
from lib.utils.mytime import UtilTime


def handler(accid,res):

    for item in res:
        with transaction.atomic():
            try:
                atObj = AccTag.objects.get(id=item['id'])
                atObj.name = item['name']
                atObj.fans_count = item['count']
                atObj.wechat_fans_count = item['count']
                atObj.umark = '0'
                atObj.save()
            except AccTag.DoesNotExist:
                try:
                    AccTag.objects.create(**dict(
                        id = item['id'],
                        accid = accid,
                        name = item['name'],
                        fans_count = item['count'],
                        wechat_fans_count = item['count'],
                        umark = '0'
                    ))
                except Exception as e:
                    logger.error(str(e))

def sync(accid):

    logger.info("[{}]正在处理[{}]公众号标签同步...".format(UtilTime().arrow_to_string(),accid))
    AccTag.objects.filter(accid=accid).update(umark='1')

    res = WeChatAccTag(accid=accid,isAccessToken=True).get_tag_list()

    handler(accid,res['tags'])

    logger.info("公众号[{}]标签同步[{}]处理完毕!".format(accid,UtilTime().arrow_to_string()))

if __name__ == '__main__':

    """
        同步粉丝列表
    """

    accid = sys.argv[1]

    try:
        sync(accid)
    except Exception as e:
        logger.error(str(e))