package com.nf147.prophet.entity;

public class Imgpath {
    private Integer imgId;

    private String imgPath;

    private Integer imgUserId;

    public Integer getImgId() {
        return imgId;
    }

    public void setImgId(Integer imgId) {
        this.imgId = imgId;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath == null ? null : imgPath.trim();
    }

    public Integer getImgUserId() {
        return imgUserId;
    }

    public void setImgUserId(Integer imgUserId) {
        this.imgUserId = imgUserId;
    }
}