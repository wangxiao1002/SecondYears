

from requests import request

from lib.utils.log import logger

from project.settings import ServerUrl
from lib.utils.exceptions import PubErrorCustom

def get_token(request):
    return request.META.get('HTTP_AUTHORIZATION')

def send_request_other(url, method='get', params=None, data=None ,headers={}):
    logger.info("请求参数: url:{} header:{} body:{} params:{}".format(url,headers,data,params))

    try:
        result = request(method, url, params=params, json =data, verify=False,headers=headers)
        status_code = result.status_code
        result = result.json()
        if str(status_code) == '200':
            return result
    except Exception as ex:
        logger.error('{0} 调用失败:{1}'.format(url, ex))
        raise PubErrorCustom('{0}'.format(ex))

def send_request(url, token=None, method='get', params=None, data=None ,headers={}):
    logger.info("请求参数: url:{} header:{} body:{}".format(url,headers,data))
    print(ServerUrl+url)
    try:
        result = request(method, ServerUrl+url, params=params, json =data, verify=False,headers=headers)
        # status_code = result.status_code
        result = result.json()
        logger.info(result)
        if str(result.get('rescode')) == '10000' :
            return result.get('data') if 'data' in result else {}
        raise PubErrorCustom(result.get('msg'))
    except Exception as ex:
        logger.error('{0} 调用失败:{1}'.format(url, ex))
        raise PubErrorCustom('{0}'.format(ex))


def getUserIdBySsoServer(rolecode=None):
    return send_request(url="/generator/generator/get", params={
        "type": '0',
        "rolecode": rolecode
    }).get('id')

def getOrderIdBySsoServer():
    return send_request(url="/generator/generator/get", params={
        "type": '1'
    }).get('id')