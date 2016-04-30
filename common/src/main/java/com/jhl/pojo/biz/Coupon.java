package com.jhl.pojo.biz;

import com.jhl.annotation.PrimaryKey;

import java.util.Date;

/**
 * Created by hallywu on 16/2/16.
 */
public class Coupon {

    @PrimaryKey
    private Integer id;

    /**
     * 用户ID
     */
    private String desc;
    /**
     * 描述
     */
    private String name;
    /**
     * 最小使用金额
     */
    private Integer min;
    /**
     * 逻辑删除标志位
     */
    private Integer deleteState;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 有效期起
     */
    private Date beginDate;
    /**
     * 有效期止
     */
    private Date endDate;

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getDeleteState() {
        return deleteState;
    }

    public void setDeleteState(Integer deleteState) {
        this.deleteState = deleteState;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
