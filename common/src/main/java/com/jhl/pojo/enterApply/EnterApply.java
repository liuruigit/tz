package com.jhl.pojo.enterApply;

import com.jhl.annotation.ForbidUpdate;
import com.jhl.annotation.PrimaryKey;
import com.jhl.annotation.Transient;

import java.util.Date;

/**
 * Created by hallywu on 15/9/24.
 */
public class EnterApply {

    @Transient
    public static final int STATUS_CREATE=0;
    @Transient
    public static final int STATUS_PERMIT=1;

    @PrimaryKey
    private int id;
    private String enterpriseName;
    private String mobile;
    private String userName;
    private String certNo;
    private int userId;
    @ForbidUpdate
    private int deleteState = 1;
    private int status;
    private Date createTime = new Date();
    public String getEnterpriseName() {
        return enterpriseName;
    }

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getDeleteState() {
        return deleteState;
    }

    public void setDeleteState(int deleteState) {
        this.deleteState = deleteState;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
