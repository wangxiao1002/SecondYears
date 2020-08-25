
from cryptokit import AESCrypto
import base64

from lib.utils.exceptions import PubErrorCustom

def encrypt(word,key='LXJMTZCVFKQZNQ2J'):
    crypto = AESCrypto(key,key)
    data = crypto.encrypt(word,mode='cbc')
    return base64.b64encode(data)

def decrypt(word,key='LXJMTZCVFKQZNQ2J'):
    try:
        word=word.encode('utf-8')
        crypto = AESCrypto(key,key)
        data=base64.b64decode(word)
        return crypto.decrypt(data)
    except Exception as  e :
        print(str(e))
        raise PubErrorCustom("请求格式有误!")