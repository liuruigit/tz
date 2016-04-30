package com.jhl.dto;

/**
 * Created by Administrator on 2016/2/19.
 */
public class InvestDto extends BaseDto {

    String projectId;
    String amount;
    String proName;
    String status;
    String couponId;

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "InvestDto{" +
                "projectId='" + projectId + '\'' +
                ", amount='" + amount + '\'' +
                ", proName='" + proName + '\'' +
                ", status='" + status + '\'' +
                ", couponId='" + couponId + '\'' +
                "} " + super.toString();
    }
}
