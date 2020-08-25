

import os

# #腾讯对象存储
# TX_SECRET_ID = os.getenv("TX_SECRET_ID",None)
# TX_SECRET_KEY = os.getenv("TX_SECRET_KEY",None)



TX_WECHAT_TOKEN = os.getenv("TX_WECHAT_TOKEN","eNoUNRR4e7V85KLb")

import os

BASEURL = os.getenv("BASEURL","http://localhost:9006")
VERSION = os.getenv("VERSION","v1")
APIURL = "{}/{}{}".format(BASEURL,VERSION,"/api")
TASKURL = os.getenv("TASKURL","http://localhost:9887")
