package com.nf147.prophet.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class User {
    private Integer userId;

    private String userName;

    @JsonFormat
    private String userPwd;

    private String userEmail;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date userBirthday;

    private String userPortraitUrl;

    private String userBackgroundImg;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd == null ? null : userPwd.trim();
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail == null ? null : userEmail.trim();
    }

    public Date getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(Date userBirthday) {
        this.userBirthday = userBirthday;
    }

    public String getUserPortraitUrl() {
        return userPortraitUrl;
    }

    public void setUserPortraitUrl(String userPortraitUrl) {
        this.userPortraitUrl = userPortraitUrl == null ? null : userPortraitUrl.trim();
    }

    public String getUserBackgroundImg() {
        return userBackgroundImg;
    }

    public void setUserBackgroundImg(String userBackgroundImg) {
        this.userBackgroundImg = userBackgroundImg == null ? null : userBackgroundImg.trim();
    }
}