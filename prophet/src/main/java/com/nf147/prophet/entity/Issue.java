package com.nf147.prophet.entity;

import java.util.Date;

public class Issue {
    private Integer issueId;

    private Integer issueInterestId;

    private String issueTitle;

    private Integer issueUserId;

    private Byte issueAnonymous;

    private Date issueTime;

    private Integer issueReply;

    private Integer issueViews;

    public Integer getIssueId() {
        return issueId;
    }

    public void setIssueId(Integer issueId) {
        this.issueId = issueId;
    }

    public Integer getIssueInterestId() {
        return issueInterestId;
    }

    public void setIssueInterestId(Integer issueInterestId) {
        this.issueInterestId = issueInterestId;
    }

    public String getIssueTitle() {
        return issueTitle;
    }

    public void setIssueTitle(String issueTitle) {
        this.issueTitle = issueTitle == null ? null : issueTitle.trim();
    }

    public Integer getIssueUserId() {
        return issueUserId;
    }

    public void setIssueUserId(Integer issueUserId) {
        this.issueUserId = issueUserId;
    }

    public Byte getIssueAnonymous() {
        return issueAnonymous;
    }

    public void setIssueAnonymous(Byte issueAnonymous) {
        this.issueAnonymous = issueAnonymous;
    }

    public Date getIssueTime() {
        return issueTime;
    }

    public void setIssueTime(Date issueTime) {
        this.issueTime = issueTime;
    }

    public Integer getIssueReply() {
        return issueReply;
    }

    public void setIssueReply(Integer issueReply) {
        this.issueReply = issueReply;
    }

    public Integer getIssueViews() {
        return issueViews;
    }

    public void setIssueViews(Integer issueViews) {
        this.issueViews = issueViews;
    }
}