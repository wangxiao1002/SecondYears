package com.sy.notifyserver.domain;

/**
 * 直播间
 * @author wangxiao
 * @since 1.1
 */
public class WebSocketRoom {

    private String code;
    private String title;
    private String userId;
    private String address;
    private String typeId;
    private double roomLng;
    private double roomLat;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public double getRoomLng() {
        return roomLng;
    }

    public void setRoomLng(double roomLng) {
        this.roomLng = roomLng;
    }

    public double getRoomLat() {
        return roomLat;
    }

    public void setRoomLat(double roomLat) {
        this.roomLat = roomLat;
    }
}
