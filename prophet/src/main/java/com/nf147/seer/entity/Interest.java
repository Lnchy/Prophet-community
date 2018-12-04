package com.nf147.seer.entity;

public class Interest {
    private Integer interestId;

    private String intersetName;

    private String intersetImg;

    private String intersetDescribe;

    public Integer getInterestId() {
        return interestId;
    }

    public void setInterestId(Integer interestId) {
        this.interestId = interestId;
    }

    public String getIntersetName() {
        return intersetName;
    }

    public void setIntersetName(String intersetName) {
        this.intersetName = intersetName == null ? null : intersetName.trim();
    }

    public String getIntersetImg() {
        return intersetImg;
    }

    public void setIntersetImg(String intersetImg) {
        this.intersetImg = intersetImg == null ? null : intersetImg.trim();
    }

    public String getIntersetDescribe() {
        return intersetDescribe;
    }

    public void setIntersetDescribe(String intersetDescribe) {
        this.intersetDescribe = intersetDescribe == null ? null : intersetDescribe.trim();
    }
}