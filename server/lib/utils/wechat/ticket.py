
from lib.utils.wechat.base import WechatBase

from lib.utils.wechat.utils import Prpcrypt

class WechatRequestValid(WechatBase):

    def checkSignatrue(self,timestamp,nonce,signature):
        sortlist = [self.token, timestamp, nonce]
        newsignature = self.sha1(sortlist)
        if newsignature != signature:
            raise Exception("checkSignatrue Error!")

    def run(self,timestamp,nonce,signature):
        # print("时间戳{}-随机数{}-签名{}".format(timestamp,nonce,signature))
        self.checkSignatrue(timestamp,nonce,signature)


class WechatMsgValid(WechatBase):

    def checkSignatrue(self,timestamp,nonce,signature,encrypt):
        # print("时间戳{}-随机数{}-签名{}-内容{}".format(timestamp,nonce,signature,encrypt))
        sortlist = [self.token, timestamp, nonce,encrypt]
        newsignature = self.sha1(sortlist)
        if newsignature != signature:
            raise Exception("checkSignatrue Error!")

    def run(self,timestamp,nonce,signature):

        encrypt = self.xml_tree.find("Encrypt").text

        self.checkSignatrue(timestamp,nonce,signature,encrypt)

        pc = Prpcrypt(self.key)
        xml_content = pc.decrypt(encrypt.encode('utf-8'), self.appid)

        return xml_content


if __name__=='__main__':
    xmltext="""
    <xml>
    <AppId><![CDATA[wxbc8cf6d177029077]]></AppId>
    <Encrypt><![CDATA[PT20Q9XOOwqmsrQjuAKrp2VPLp/fLgr2JDKVn5r+WJzMpTlCXy0fGn2o9WmDJ98XzgkqVV7A1hDKIiy1w2tzc+1Fpm1L/A9F4kAfrusjlBReBx6Xd2S1FHOFpfPsnQeN4daA4aaCY3GXanSQkpAkQyzRY6ZQ0YNpTcnDUU6Z0JB3/aHee5pTqdahMPVr0AAlaRQ5LWt2rSF+GT1Wypn6uVyhJnKNwqRjhxkPdqAdgMxgYzcmA1t2w7eEZKPbEZh4vHaOWAU8ebZnn34CqAufDSBztg2l4Sd9wh5rrdybxH0McaHle4HEFh3uNkkmcKtT2Suyuc78NrBAvdhf6+pUzLE4C09hDYzyBRo62viNYhBn7tMSfB2/jGm80ipd5uEG1kHUo2+WtRIunZ64PNYYwwCZFaKEOdQkfcqxj8MwQ/2cK/ElsNqr3zA9/WA57achEE2T+Ic6eLs5fNAdTZX6Vg==]]></Encrypt>
</xml>
    """
    print(WechatMsgValid(xmltext=xmltext).run("1589133078","1549019745","d160217f77e518773710024e9c0a8077f19e51e5"))

