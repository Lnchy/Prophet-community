package com.nf147.prophet.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

public class Issue {
    private Integer issueId;

    private Integer issueInterestId;

    private String issueTitle;

    private Integer issueUserId;

    private Byte issueAnonymous;

    private Byte issueEssence;

    private Byte issueBan;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date issueCreateTime;

    private Integer issueReply;

    private Integer issueViews;

    private String issueContent;

    private Reply reply;

    public Reply getReply() {
        return reply;
    }

    public void setReply(Reply reply) {
        this.reply = reply;
    }

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

    public Byte getIssueEssence() {
        return issueEssence;
    }

    public void setIssueEssence(Byte issueEssence) {
        this.issueEssence = issueEssence;
    }

    public Byte getIssueBan() {
        return issueBan;
    }

    public void setIssueBan(Byte issueBan) {
        this.issueBan = issueBan;
    }

    public Date getIssueCreateTime() {
        return issueCreateTime;
    }

    public void setIssueCreateTime(Date issueCreateTime) {
        this.issueCreateTime = issueCreateTime;
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

    public String getIssueContent() {
        return issueContent;
    }

    public void setIssueContent(String issueContent) {
        this.issueContent = issueContent == null ? null : issueContent.trim();
    }

}