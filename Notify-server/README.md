#Notify-Server 消息服务模块
**原本要将邮件提醒功能要迁移过来，但是想起来邮件需要用户数据信息等信息，要将这个服务单独化，负责消息和IM功能**
## 主要功能
1. 直播聊天室(webSocket 实现)
2. 消息通知（公告、私信）(netty-socketIo 实现)
3. 主要是学习netty
## 消除大量的if-else
**心里特难受，写了这么长时间的java 重新写还有大量if-else **

1.固定判断
```java
// 固定模式的if 例如微信公众号回调，使用Map 存储类型和函数，map.get().apply(参数)，Function(参数，返回)
    private Map<MessageType, Function<Message,Map<String,Object>>> functionMap = new HashMap<>(8);
    // 调用地方
    public Map<String, Object> pushMessage(Message message) {
        return functionMap.get(message.getType()).apply(message);
    }
    // 具体函数
    private Map<String,Object> doMessage(Message message) {
        // 发送一种消息类型
        return null;
    }
    // 将函数存储进入
    functionMap.put(MessageType.ANNOUNCEMENT,this::doMessage);
    
```
2.责任链
* 将数据传递下去,用户并不关心数据传递过程 主要思想设置next 节点，当前节点不能满足数据处理时候传递下去
```java
abstract class Handler{
    private Handler next;
    public void setNext(Handler next){
        this.next=next; 
    }
    public Handler getNext(){ 
        return next; 
    }   
    //处理请求的方法
    public abstract void handleRequest(String request);       
}
//具体处理者角色1
class ConcreteHandler1 extends Handler{
    public void handleRequest(String request){
        if(request.equals("one")) {
            System.out.println("处理者1 处理");       
        } else {
            // next 节点处理（个人感觉 modelView试图解析使用责任链模式（具体源码还未看在另一仓库准备更新源码日记））
           getNext().handleRequest(request);    
        } 
    } 
}
```
## 敏感词
DFA 算法实现敏感词过滤（copy csdn 代码源地址找不到了 侵删）
## 直播封装
* 真个直播流程有很好得开源组件来完成,上周完成OBS+nginx-http-flv-module 直播推流测试
* 据业界人员说明，凡是大多数直播推流都是封装OBS，所以尝试用Java 封装外壳（或者放弃该模块）
* 首要问题 联系JNI 调用类库
* 直播带宽