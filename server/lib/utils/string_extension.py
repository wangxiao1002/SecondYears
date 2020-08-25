# coding: utf-8
import uuid
import os
import time
from datetime import datetime, timedelta
from urllib.parse import urlparse

import hashlib
import binascii


def cast_endtime(end: str):
    try:
        return datetime.strptime(end, '%Y-%m-%d') + timedelta(days=1) if end else ''
    except:
        return ''


def thousandth(value):
    '''
    千分位的数值
    :param value:
    :return:
    '''
    return "{:,}".format(float(value)) if value else 0


def safe_str(value):
    return str(value) if value else ''


def safe_float(value):
    return float(value) if value else 0


def safe_int(value):
    try:
        return int(value)
    except:
        return 0


def cast_none(value):
    return value if value else 0


def increase_rate(old, new):
    '''
    计算增长率
    :param old:
    :param new:
    :return:
    '''
    old = safe_float(old)
    new = safe_float(new)

    if old == 0 and new == 0:
        return 0
    if new == 0:
        return -100
    return '%.0f' % ((new - old) / new * 100)


def split_field(value: str, length=20):
    if value and len(value) > length:
        return value[0:length]
    return value


def get_uuid():
    return str(time.time()).replace('.', '')
    # return str(uuid.uuid3()).replace('-', '')


def generate_orderno():
    return datetime.now().strftime('%Y%m%d') + str(int(datetime.utcnow().timestamp()))


def format_time(time):
    '''
    格式化时间
    :param time:
    :return:
    '''
    return time.strftime('%Y-%m-%d %H:%M:%S') if time else ''


def formatdate(time):
    '''
    格式化日期
    :param time:
    :return:
    '''
    return time.strftime('%Y-%m-%d') if time else ''


def safe_dict_value(dict_list, field_name):
    return dict_list[0][field_name] if len(dict_list) > 0 else ''


def safe_object_value(dict_list, field_name):
    return getattr(dict_list[0], field_name) if len(dict_list) > 0 else ''


def safe_dict_values(dict_list, *field_names):
    d = dict.fromkeys(field_names, '')

    if len(dict_list) > 0:
        value = dict_list[0]
        for field_name in field_names:
            d[field_name] = value[field_name]
    return d


def cache_seconds(now: datetime, minutes: int):
    '''
    计算缓存时间秒数
    :param now:
    :param minutes:
    :return:
    '''
    return ((now + timedelta(minutes=minutes)) - now).seconds


def parse_url(url):
    '''
    去除域名以及/前缀
    :param url:
    :return:
    '''
    if url is None:
        return ''
    parse = urlparse(url)
    url = parse.path
    return url.lstrip('/')

def md5pass(str):
    return hashlib.md5(str.encode("utf8")).hexdigest()

def get_token():
    return binascii.hexlify(os.urandom(80)).decode()

