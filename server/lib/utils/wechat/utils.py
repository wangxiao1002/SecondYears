
from cryptokit import AESCrypto
import struct,base64,socket,random,string,json
from lib.utils.wechat import ierror


class PKCS7Encoder():
    """提供基于PKCS7算法的加解密接口"""

    block_size = 32

    def encode(self, text):
        """ 对需要加密的明文进行填充补位
        @param text: 需要进行填充补位操作的明文
        @return: 补齐明文字符串
        """
        text_length = len(text)
        # 计算需要填充的位数
        amount_to_pad = self.block_size - (text_length % self.block_size)
        if amount_to_pad == 0:
            amount_to_pad = self.block_size
        # 获得补位所用的字符
        pad = chr(amount_to_pad).encode()
        return text + pad * amount_to_pad

    def decode(self, decrypted):
        """删除解密后明文的补位字符
        @param decrypted: 解密后的明文
        @return: 删除补位字符后的明文
        """
        pad = ord(decrypted[-1])
        if pad < 1 or pad > 32:
            pad = 0
        return decrypted[:-pad]

class Prpcrypt(object):
    """提供接收和推送给公众平台消息的加解密接口"""

    def __init__(self, key):
        #self.key = base64.b64decode(key+"=")
        self.key = key
        # 设置加解密模式为AES的CBC模式
        self.mode = 'cbc'

    def encrypt(self, text, appid):
        """对明文进行加密
        @param text: 需要加密的明文
        @return: 加密得到的字符串
        """
        # 16位随机字符串添加到明文开头
        len_str = struct.pack("I", socket.htonl(len(text.encode())))
        text = self.get_random_str() + len_str + text.encode() + appid
        # 使用自定义的填充方式对明文进行补位填充
        pkcs7 = PKCS7Encoder()
        text = pkcs7.encode(text)
        # 加密
        cryptor = AESCrypto(self.key, self.key[:16])
        try:
            ciphertext = cryptor.encrypt(text, mode='cbc')
            # 使用BASE64对加密后的字符串进行编码
            return ierror.WXBizMsgCrypt_OK, base64.b64encode(
                ciphertext).decode('utf8')
        except Exception as e:
            #print(e)
            return ierror.WXBizMsgCrypt_EncryptAES_Error, None

    def decrypt(self, text, appid):
        """对解密后的明文进行补位删除
        @param text: 密文
        @return: 删除填充补位后的明文
        """
        try:
            cryptor = AESCrypto(self.key, self.key[:16])
            # 使用BASE64对密文进行解码，然后AES-CBC解密
            plain_text = cryptor.decrypt(base64.b64decode(text))
        except Exception as e:
            # print(e)
            raise Exception(ierror.WXBizMsgCrypt_DecryptAES_Error)
        try:
            plain_text = "<xml>" + plain_text.split("<xml>")[1]
            xml_content=plain_text.split("</xml>")[0]+'</xml>'
            from_appid=plain_text.split("</xml>")[1]
        except Exception as e:
            raise Exception(ierror.WXBizMsgCrypt_IllegalBuffer)
        if from_appid != appid:
            raise Exception(ierror.WXBizMsgCrypt_ValidateAppid_Error)
        return xml_content

    def get_random_str(self):
        """ 随机生成16位字符串
        @return: 16位字符串
        """
        rule = string.ascii_letters + string.digits
        str = random.sample(rule, 16)
        return "".join(str).encode()

