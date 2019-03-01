package com.nf147.prophet.entity;

import java.util.Date;

public class Reply {
    private Integer replyId;

    private Integer replyIssueId;

    private Integer replyReplyId;

    private String replyTips;

    private Integer replyUserId;

    private Date replyTime;

    private Integer replyPraise;

    private Byte replyInvalid;

    private String replyContent;

    private int praiseNum;

    private User userInfo;

    public User getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(User userInfo) {
        this.userInfo = userInfo;
    }

    public int getPraiseNum() {
        return praiseNum;
    }

    public void setPraiseNum(int praiseNum) {
        this.praiseNum = praiseNum;
    }

    public Integer getReplyId() {
        return replyId;
    }

    public void setReplyId(Integer replyId) {
        this.replyId = replyId;
    }

    public Integer getReplyIssueId() {
        return replyIssueId;
    }

    public void setReplyIssueId(Integer replyIssueId) {
        this.replyIssueId = replyIssueId;
    }

    public Integer getReplyReplyId() {
        return replyReplyId;
    }

    public void setReplyReplyId(Integer replyReplyId) {
        this.replyReplyId = replyReplyId;
    }

    public String getReplyTips() {
        return replyTips;
    }

    public void setReplyTips(String replyTips) {
        this.replyTips = replyTips == null ? null : replyTips.trim();
    }

    public Integer getReplyUserId() {
        return replyUserId;
    }

    public void setReplyUserId(Integer replyUserId) {
        this.replyUserId = replyUserId;
    }

    public Date getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(Date replyTime) {
        this.replyTime = replyTime;
    }

    public Integer getReplyPraise() {
        return replyPraise;
    }

    public void setReplyPraise(Integer replyPraise) {
        this.replyPraise = replyPraise;
    }

    public Byte getReplyInvalid() {
        return replyInvalid;
    }

    public void setReplyInvalid(Byte replyInvalid) {
        this.replyInvalid = replyInvalid;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent == null ? null : replyContent.trim();
    }
}