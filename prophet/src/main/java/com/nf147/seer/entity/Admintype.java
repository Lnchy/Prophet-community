package com.nf147.seer.entity;

public class Admintype {
    private Integer adminTypeId;

    private String adminTypeName;

    private Integer adminInterestId;

    public Integer getAdminTypeId() {
        return adminTypeId;
    }

    public void setAdminTypeId(Integer adminTypeId) {
        this.adminTypeId = adminTypeId;
    }

    public String getAdminTypeName() {
        return adminTypeName;
    }

    public void setAdminTypeName(String adminTypeName) {
        this.adminTypeName = adminTypeName == null ? null : adminTypeName.trim();
    }

    public Integer getAdminInterestId() {
        return adminInterestId;
    }

    public void setAdminInterestId(Integer adminInterestId) {
        this.adminInterestId = adminInterestId;
    }
}