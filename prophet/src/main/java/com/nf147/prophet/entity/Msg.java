package com.nf147.prophet.entity;

import java.util.Date;

public class Msg {
    private Integer msgId;

    private Integer msgUserFromId;

    private Integer msgUserAcceptId;

    private Date msgTime;

    private String msgContent;

    public Integer getMsgId() {
        return msgId;
    }

    public void setMsgId(Integer msgId) {
        this.msgId = msgId;
    }

    public Integer getMsgUserFromId() {
        return msgUserFromId;
    }

    public void setMsgUserFromId(Integer msgUserFromId) {
        this.msgUserFromId = msgUserFromId;
    }

    public Integer getMsgUserAcceptId() {
        return msgUserAcceptId;
    }

    public void setMsgUserAcceptId(Integer msgUserAcceptId) {
        this.msgUserAcceptId = msgUserAcceptId;
    }

    public Date getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(Date msgTime) {
        this.msgTime = msgTime;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent == null ? null : msgContent.trim();
    }
}