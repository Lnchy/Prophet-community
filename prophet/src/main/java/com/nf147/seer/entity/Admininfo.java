package com.nf147.seer.entity;

import java.util.Date;

public class Admininfo {
    private Integer adminId;

    private Integer adminUserId;

    private Integer adminTypeId;

    private Date adminTime;

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public Integer getAdminUserId() {
        return adminUserId;
    }

    public void setAdminUserId(Integer adminUserId) {
        this.adminUserId = adminUserId;
    }

    public Integer getAdminTypeId() {
        return adminTypeId;
    }

    public void setAdminTypeId(Integer adminTypeId) {
        this.adminTypeId = adminTypeId;
    }

    public Date getAdminTime() {
        return adminTime;
    }

    public void setAdminTime(Date adminTime) {
        this.adminTime = adminTime;
    }
}