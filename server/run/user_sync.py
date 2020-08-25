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
from app.wechat.models import AccLinkUser
from lib.utils.wechat.user import WechatAccUser
from lib.utils.log import logger
from lib.utils.mytime import UtilTime


def handler(accid,openids):

    for openid in openids:

        logger.info("处理[{}]中".format(openid))

        userinfo = WechatAccUser(accid=accid,isAccessToken=True).get_info(openid)

        logger.info("获取列表[{}]".format(userinfo))

        if userinfo['subscribe'] == 0:
            continue

        with transaction.atomic():
            try:
                accUserObj = AccLinkUser.objects.get(accid=accid,openid=openid)
                accUserObj.tags = json.dumps(userinfo['tagid_list']).replace(" ","")
                accUserObj.nickname = userinfo['nickname']
                accUserObj.sex = userinfo['sex']
                accUserObj.city = userinfo['city']
                accUserObj.province = userinfo['province']
                accUserObj.country = userinfo['country']
                accUserObj.headimgurl = userinfo['headimgurl']
                accUserObj.subscribe_time = userinfo['subscribe_time']
                accUserObj.subscribe_scene = userinfo['subscribe_scene']
                accUserObj.umark='0'
                accUserObj.save()

            except AccLinkUser.DoesNotExist:
                try:
                    AccLinkUser.objects.create(**dict(
                        accid=accid,
                        openid = userinfo['openid'],
                        tags=json.dumps(userinfo['tagid_list']).replace(" ",""),
                        nickname = userinfo['nickname'],
                        sex = userinfo['sex'],
                        city = userinfo['city'],
                        province = userinfo['province'],
                        country = userinfo['country'],
                        headimgurl = userinfo['headimgurl'],
                        subscribe_time = userinfo['subscribe_time'],
                        subscribe_scene = userinfo['subscribe_scene'],
                        umark = '0'
                    ))
                except Exception as e:
                    logger.error(str(e))

def sync(accid):

    logger.info("[{}]正在处理[{}]公众号粉丝同步...".format(UtilTime().arrow_to_string(),accid))
    AccLinkUser.objects.filter(accid=accid).update(umark='1')

    count = 0
    total = 0
    next_openid=False
    while count ==0 or count < total:

        res = WechatAccUser(accid=accid,isAccessToken=True).get_user_list(next_openid)
        logger.info("获取列表[{}]".format(res))
        handler(accid, res['data']['openid'])

        count+=res['count']
        total = res['total']
        next_openid=res['next_openid']

        print(count,total)

    logger.info("公众号[{}]粉丝同步[{}]处理完毕!".format(accid,UtilTime().arrow_to_string()))

if __name__ == '__main__':

    """
        同步粉丝列表
    """

    accid = sys.argv[1]

    try:
        sync(accid)
    except Exception as e:
        logger.error(str(e))