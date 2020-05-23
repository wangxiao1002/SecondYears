package com.sy.notifyserver.websocket;

import java.time.LocalDateTime;

/**
 * socketMessage
 * @author wangxiao
 * @since 1.1
 */
public class SocketMessage {

    private String fromId;

    private String message;

    private SocketMessageType type;

    private LocalDateTime createDateTime;

    private SocketMessage () {
        this.createDateTime = LocalDateTime.now();
    }

    public static SocketMessage build () {
        return new SocketMessage();
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SocketMessageType getType() {
        return type;
    }

    public void setType(SocketMessageType type) {
        this.type = type;
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(LocalDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }


    public SocketMessage fromId(String fromId) {
        this.fromId = fromId;
        return this;
    }
    public SocketMessage message(String message) {
        this.message = message;
        return this;
    }
    public SocketMessage type(SocketMessageType type) {
        this.type = type;
        return this;
    }

}
