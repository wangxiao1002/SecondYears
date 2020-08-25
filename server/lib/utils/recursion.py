

class Recursion(object):

    def __init__(self,**kwargs):

        #头部数据
        self.headObj = kwargs.get("headObj")

        #查询几层(含头部) 大于等于99的时候为无限层级
        self.queryNumber = kwargs.get("queryNumber")

        #模型obj
        self.objModle = kwargs.get('objModle')

        #对应模型的唯一IDKey
        self.idKey = kwargs.get('idKey')

        #对应模型的唯一上级IDKey
        self.idLastKey = kwargs.get('idLastKey')

        #模型序列化类
        self.serialiers = kwargs.get("serialiers")

class RecursionForModle(Recursion):

    """
        Model递归查询
    """

    #深度方向查询
    def depthHandler(self,child=None):
        """
        广度查询
        :param child: 传单个child
        :return:
        """
        if child and self.queryNumber:
            child['child'] = list()
            if child['level'] == self.queryNumber and self.queryNumber<99:
                return

            obj=(self.objModle).objects.filter(**{
                self.idLastKey : child.get(self.idKey)
            })

            if obj.exists():
                for itemObj in obj:
                    serialiersObj = self.serialiersHandler(itemObj)
                    child['child'].append(serialiersObj)
                    self.depthHandler(child=serialiersObj)

    #广度方向查询
    def durationHandler(self,child=None):
        """
        广度查询
        :param child: 传多个child 传入list
        :return:
        """
        current=[]

        for itemChild in child:
            itemChild.child = list()
            if itemChild.level == self.queryNumber and self.queryNumber<99:
                continue

            obj=(self.objModle).objects.filter(**{
                self.idLastKey : getattr(itemChild, self.idKey)
            })

            if obj.exists():
                for itemObj in obj:
                    itemChild.child.append(itemObj)
                    current.append(itemObj)
        if len(current):
            self.durationHandler(current)

    def serialiersHandler(self,obj,many=False):
        """
        序列化处理
        :return:
        """
        return self.serialiers(obj,many=many).data


    def run(self):
        baseObj = self.serialiersHandler(self.headObj)
        self.depthHandler(baseObj)
        return baseObj
