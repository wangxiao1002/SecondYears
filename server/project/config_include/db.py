

import os

DBHOST = os.environ.get('DBHOST1', 'localhost')
DBPORT = os.environ.get('DBPORT1', '3306')
DBNAME = os.environ.get('DBNAME1', 'hdb')
DBUSER = os.environ.get('DBUSER1', 'root')
DBPASS = os.environ.get('DBPASS1', '123456')

DATABASES = {
    'default': {
        'ENGINE': 'django.db.backends.mysql',
        'NAME': DBNAME,
        'USER': DBUSER,
        'PASSWORD': DBPASS,
        'HOST': DBHOST,
        'PORT': DBPORT,
    }
}


REDISPASSWORD = os.environ.get('REDISPASS', '123456')
REDISURL =  os.environ.get('REDISHOST', 'localhost')
REDISPORT = os.environ.get('REDISPORT', '6379')

CACHES = {
    "default": {
        "BACKEND": "django_redis.cache.RedisCache",
        "LOCATION": "redis://{}:{}/0".format(REDISURL, REDISPORT),
        "OPTIONS": {
            "CLIENT_CLASS": "django_redis.client.DefaultClient",
            'CONNECTION_POOL_KWARGS': {"max_connections": 100},
            "PASSWORD": REDISPASSWORD
        }
    },
    "token": {
        "BACKEND": "django_redis.cache.RedisCache",
        "LOCATION": "redis://{}:{}/1".format(REDISURL, REDISPORT),
        "OPTIONS": {
            "CLIENT_CLASS": "django_redis.client.DefaultClient",
            'CONNECTION_POOL_KWARGS': {"max_connections": 100},
            "PASSWORD": REDISPASSWORD
        }
    },
    "cache": {
        "BACKEND": "django_redis.cache.RedisCache",
        "LOCATION": "redis://{}:{}/2".format(REDISURL, REDISPORT),
        "OPTIONS": {
            "CLIENT_CLASS": "django_redis.client.DefaultClient",
            'CONNECTION_POOL_KWARGS': {"max_connections": 100},
            "PASSWORD": REDISPASSWORD
        }
    },
    "wechatTicket": {
        "BACKEND": "django_redis.cache.RedisCache",
        "LOCATION": "redis://{}:{}/3".format(REDISURL, REDISPORT),
        "OPTIONS": {
            "CLIENT_CLASS": "django_redis.client.DefaultClient",
            'CONNECTION_POOL_KWARGS': {"max_connections": 100},
            "PASSWORD": REDISPASSWORD
        }
    }
}