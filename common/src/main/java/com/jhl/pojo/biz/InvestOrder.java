package com.jhl.pojo.biz;

import com.jhl.annotation.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

/**
*
* 类名称：InvestOrder.java
* 类描述：
* @author Hally
* 作者单位：
* 联系方式：18929353864
* 创建时间：2016-01-25
* @version 1.0
*/
public class InvestOrder implements Serializable{
	@PrimaryKey
	private Integer id;
	/** 用户ID */
	private int accId;
	/** 用户名称 */
	private String accName;
	/** 标的ID */
	private int proId;
	/** 投资金额 */
	private Long amount;
	/** 订单号 */
	private String no;
	/** 合同 */
	private String contractNo;
	/** 状态 */
	private int status;
	/** 删除状态 */
	private int deleteState = 1;
	/** 认购成功时间 */
//	private Date succTime;
	/** 支付方式 */
	private int payType;
	/** 投资券ID */
	private int couponId;
	/** 创建时间 */
	private Date createTime;
	/** 更新时间 */
//	private Date updateTime;
	/** 客户端版本号 */
	private String appVersion;

	public int getCouponId() {
		return couponId;
	}

	public void setCouponId(int couponId) {
		this.couponId = couponId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getAccId() {
		return accId;
	}

	public void setAccId(int accId) {
		this.accId = accId;
	}

	public String getAccName() {
		return accName;
	}

	public void setAccName(String accName) {
		this.accName = accName;
	}

	public int getProId() {
		return proId;
	}

	public void setProId(int proId) {
		this.proId = proId;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getDeleteState() {
		return deleteState;
	}

	public void setDeleteState(int deleteState) {
		this.deleteState = deleteState;
	}

	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	@Override
	public String toString() {
		return "InvestOrder{" +
				"id=" + id +
				", accId=" + accId +
				", accName='" + accName + '\'' +
				", proId=" + proId +
				", amount=" + amount +
				", no='" + no + '\'' +
				", contractNo='" + contractNo + '\'' +
				", status=" + status +
				", deleteState=" + deleteState +
				", payType=" + payType +
				", createTime=" + createTime +
				", appVersion='" + appVersion + '\'' +
				'}';
	}
}










