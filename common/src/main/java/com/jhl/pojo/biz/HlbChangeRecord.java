package com.jhl.pojo.biz;

import com.jhl.annotation.PrimaryKey;

import java.util.Date;


/**
*
* 类名称：HlbChangeRecord.java
* 类描述：
* @author Hally
* 作者单位：
* 联系方式：18929353864
* 创建时间：2016-03-26
* @version 1.0
*/
public class HlbChangeRecord {
	@PrimaryKey
	private int id;
	/** 用户ID */
	private int accId;
	/** 葫芦币数量 */
	private Double amount;
	/** 葫芦币变更数量 */
	private Double changeAmount;
	/** 描述 */
	private String desc;
	/** 交易时间 */
	private Date createTime;
	/** 逻辑删除 */
	private Integer deleteState;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAccId() {
		return accId;
	}

	public void setAccId(int accId) {
		this.accId = accId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getChangeAmount() {
		return changeAmount;
	}

	public void setChangeAmount(Double changeAmount) {
		this.changeAmount = changeAmount;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
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
}










