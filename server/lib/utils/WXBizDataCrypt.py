import base64
import json

from cryptokit import AESCrypto

class WXBizDataCrypt:
    def __init__(self, appId, sessionKey):
        self.appId = appId
        self.sessionKey = sessionKey

    def decrypt(self, encryptedData, iv):
        # base64 decode
        sessionKey = base64.b64decode(self.sessionKey)
        encryptedData = base64.b64decode(encryptedData)
        iv = base64.b64decode(iv)

        cipher = AESCrypto(sessionKey, iv)
        decrypted = json.loads(cipher.decrypt(encryptedData))

        if decrypted['watermark']['appid'] != self.appId:
            raise Exception('Invalid Buffer')

        return decrypted
