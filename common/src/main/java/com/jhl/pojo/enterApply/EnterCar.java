package com.jhl.pojo.enterApply;

import com.jhl.annotation.PrimaryKey;
import com.jhl.annotation.ForbidUpdate;
import com.jhl.annotation.Transient;

import java.util.Date;

/**
 * Created by hallywu on 15/9/24.
 */
public class EnterCar {

    @Transient
    public static final int STATUS_CREATE=0;
    @Transient
    public static final int STATUS_PERMIT=1;

    @PrimaryKey
    private Integer id;
    private String carNo;
    private String carLisence;
    private String mobile;
    private String userName;
    private Integer userId;
    @ForbidUpdate
    private Integer deleteState = 1;
    private Integer status;
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public String getCarLisence() {
        return carLisence;
    }

    public void setCarLisence(String carLisence) {
        this.carLisence = carLisence;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getDeleteState() {
        return deleteState;
    }

    public void setDeleteState(Integer deleteState) {
        this.deleteState = deleteState;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
