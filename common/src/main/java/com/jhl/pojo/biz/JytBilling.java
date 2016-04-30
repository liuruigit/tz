package com.jhl.pojo.biz;

import com.jhl.annotation.PrimaryKey;


/**
*
* 类名称：JytBilling.java
* 类描述：
* @author Hally
* 作者单位：
* 联系方式：18929353864
* 创建时间：2016-03-01
* @version 1.0
*/
public class JytBilling {
	@PrimaryKey
	private int id;
	/** 对账日期 */
	private String billingDate;
	/** 商户号 */
	private String userId;
	/** 交易总笔数 */
	private Double count;
	/** 交易总金额 */
	private Double amount;
	/** 成功总笔数 */
	private Double succCount;
	/** 成功总金额 */
	private Double succAmount;
	/** 失败总笔数 */
	private Double failCount;
	/** 失败总金额 */
	private Double failAmount;

	public String getBillingDate() {
		return billingDate;
	}

	public void setBillingDate(String billingDate) {
		this.billingDate = billingDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Double getCount() {
		return count;
	}

	public void setCount(Double count) {
		this.count = count;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getSuccCount() {
		return succCount;
	}

	public void setSuccCount(Double succCount) {
		this.succCount = succCount;
	}

	public Double getSuccAmount() {
		return succAmount;
	}

	public void setSuccAmount(Double succAmount) {
		this.succAmount = succAmount;
	}

	public Double getFailCount() {
		return failCount;
	}

	public void setFailCount(Double failCount) {
		this.failCount = failCount;
	}

	public Double getFailAmount() {
		return failAmount;
	}

	public void setFailAmount(Double failAmount) {
		this.failAmount = failAmount;
	}

}










