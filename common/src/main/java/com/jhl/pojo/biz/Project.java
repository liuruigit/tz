package com.jhl.pojo.biz;

import com.jhl.annotation.PrimaryKey;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;

/**
 * 类名称：Project.java
 * 类描述：标的实体类
 *
 * @author Hally
 *         作者单位：
 *         联系方式：18929353864
 *         创建时间：2016-01-14
 * @version 1.0
 */
public class Project implements Serializable {
    @PrimaryKey
    private int id;
    /**
     * 产品编号
     */
    private String no;
    /**
     * 保障级别
     */
    private Integer guarantee;
    /**
     * 付息方式
     */
    private Integer payInterestWay;
    /**
     * 融资金额
     */
    private Long amount;
    /**
     * 结算金额
     */
    private Long finalAmount;
    /**
     * 已融资金额
     */
    private Long selledAmount;
    /**
     * 起投金额
     */
    private Long min;
    /**
     * 累加金额
     */
    private Long step;
    /**
     * 限投金额
     */
    private Long limit;
    /**
     * 预期年化收益率
     */
    private Double annualRate;
    /**
     * 服务费率
     */
    private Double serviceRate;
    /**
     * 开放购买时间
     */
    private Long openDate;
    /**
     * 关闭时间
     */
    private Long endDate;
    /**
     * 满标时间
     */
    private Date soldOutDate;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 是否推荐
     */
    private Integer recommend;

    /**
     * 是否推荐
     */
    private Integer vipLimit;

    /**
     * 预留信息1
     */
    private String extra1;
    /**
     * 预留信息2
     */
    private String extra2;
    /**
     * 预留信息3
     */
    private String extra3;
    /**
     * 1正常0删除
     */
    private Integer deleteState;

    public Integer getDeleteState() {
        return deleteState;
    }

    public void setDeleteState(Integer deleteState) {
        this.deleteState = deleteState;
    }

    public enum Status {
        /**
         * 新建
         */
        CREATED(0),
        /**
         * 可购买
         */
        INIT(1),
        /**
         * 已满标
         */
        FULL(2),
        /**
         * 待结算
         */
        WAIT_BALANCED(3),
        /**
         * 已结算 - 合作方
         */
        BALANCED(4),
        /**
         * 已偿还 - 财务
         */
        REPAY(5),
        /**
         * 已过期 - 财务
         */
        OUT_OF_DATE(9);

        int value;

        Status(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public Integer getVipLimit() {
        return vipLimit;
    }

    public void setVipLimit(Integer vipLimit) {
        this.vipLimit = vipLimit;
    }

    /**
     * 是否正在融资中
     *
     * @return
     */
    public boolean investing() {
        return this.getStatus() != null && this.getStatus().equals(Status.INIT.getValue());
    }

    /**
     * 是否已经满标
     *
     * @return
     */
    public boolean isFull() {
        return this.getAmount().equals(this.getSelledAmount());
    }

    public Double getAnnualRate() {
        return annualRate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAnnualRate(Double annualRate) {
        this.annualRate = annualRate;
    }

    public Date getSoldOutDate() {
        return soldOutDate;
    }

    public void setSoldOutDate(Date soldOutDate) {
        this.soldOutDate = soldOutDate;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public Integer getGuarantee() {
        return guarantee;
    }

    public void setGuarantee(Integer guarantee) {
        this.guarantee = guarantee;
    }

    public Integer getPayInterestWay() {
        return payInterestWay;
    }

    public void setPayInterestWay(Integer payInterestWay) {
        this.payInterestWay = payInterestWay;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(Long finalAmount) {
        this.finalAmount = finalAmount;
    }

    public Long getSelledAmount() {
        return selledAmount;
    }

    public void setSelledAmount(Long selledAmount) {
        this.selledAmount = selledAmount;
    }

    public Long getMin() {
        return min;
    }

    public void setMin(Long min) {
        this.min = min;
    }

    public Long getStep() {
        return step;
    }

    public void setStep(Long step) {
        this.step = step;
    }

    public Long getLimit() {
        return limit;
    }

    public void setLimit(Long limit) {
        this.limit = limit;
    }

    public Double getServiceRate() {
        return serviceRate;
    }

    public void setServiceRate(Double serviceRate) {
        this.serviceRate = serviceRate;
    }

    public Long getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Long openDate) {
        this.openDate = openDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getRecommend() {
        return recommend;
    }

    public void setRecommend(Integer recommend) {
        this.recommend = recommend;
    }

    public String getExtra1() {
        return extra1;
    }

    public void setExtra1(String extra1) {
        this.extra1 = extra1;
    }

    public String getExtra2() {
        return extra2;
    }

    public void setExtra2(String extra2) {
        this.extra2 = extra2;
    }

    public String getExtra3() {
        return extra3;
    }

    public void setExtra3(String extra3) {
        this.extra3 = extra3;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}










