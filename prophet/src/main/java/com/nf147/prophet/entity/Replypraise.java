package com.nf147.prophet.entity;

import java.util.Date;

public class Replypraise {
    private Integer praiseId;

    private Integer praiseReplyId;

    private Integer praiseUserId;

    private Date praiseTime;

    public Integer getPraiseId() {
        return praiseId;
    }

    public void setPraiseId(Integer praiseId) {
        this.praiseId = praiseId;
    }

    public Integer getPraiseReplyId() {
        return praiseReplyId;
    }

    public void setPraiseReplyId(Integer praiseReplyId) {
        this.praiseReplyId = praiseReplyId;
    }

    public Integer getPraiseUserId() {
        return praiseUserId;
    }

    public void setPraiseUserId(Integer praiseUserId) {
        this.praiseUserId = praiseUserId;
    }

    public Date getPraiseTime() {
        return praiseTime;
    }

    public void setPraiseTime(Date praiseTime) {
        this.praiseTime = praiseTime;
    }
}