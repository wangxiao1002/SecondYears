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
## 敏感词
Dfa

