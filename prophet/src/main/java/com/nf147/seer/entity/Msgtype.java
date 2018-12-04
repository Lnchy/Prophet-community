package com.nf147.seer.entity;

public class Msgtype {
    private Integer msgTypeId;

    private String msgTypeName;

    public Integer getMsgTypeId() {
        return msgTypeId;
    }

    public void setMsgTypeId(Integer msgTypeId) {
        this.msgTypeId = msgTypeId;
    }

    public String getMsgTypeName() {
        return msgTypeName;
    }

    public void setMsgTypeName(String msgTypeName) {
        this.msgTypeName = msgTypeName == null ? null : msgTypeName.trim();
    }
}