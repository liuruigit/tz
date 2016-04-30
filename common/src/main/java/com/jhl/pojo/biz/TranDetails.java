package com.jhl.pojo.biz;

import com.jhl.annotation.PrimaryKey;


/**
*
* 类名称：TranDetails.java
* 类描述：
* @author Hally
* 作者单位：
* 联系方式：18929353864
* 创建时间：2016-03-01
* @version 1.0
*/
public class TranDetails {
	@PrimaryKey
	private int id;
	/** 交易类型 */
	private String tranType;
	/** 商户交易时间 */
	private String tranDate;
	/** 交易完成时间 */
	private String tranDated;
	/** 商户订单号 */
	private String orderId;
	/** 批次号 */
	private String num;
	/** 代收付平台订单号 */
	private String repOrderId;
	/** 交易金额 */
	private Double tranAmout;
	/** 币种 */
	private String currency;
	/** 客户交易银行账号 */
	private String bankNo;
	/** 客户交易银行账户名称 */
	private String bankName;
	/** 商户交易虚拟账号 */
	private String userFicNo;
	/** 交易结果码 */
	private String resultNo;
	/** 交易结果描述 */
	private String resultDesc;
	/** 手续费 */
	private Double pound;

	public String getTranType() {
		return tranType;
	}

	public void setTranType(String tranType) {
		this.tranType = tranType;
	}

	public String getTranDate() {
		return tranDate;
	}

	public void setTranDate(String tranDate) {
		this.tranDate = tranDate;
	}

	public String getTranDated() {
		return tranDated;
	}

	public void setTranDated(String tranDated) {
		this.tranDated = tranDated;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getRepOrderId() {
		return repOrderId;
	}

	public void setRepOrderId(String repOrderId) {
		this.repOrderId = repOrderId;
	}

	public Double getTranAmout() {
		return tranAmout;
	}

	public void setTranAmout(Double tranAmout) {
		this.tranAmout = tranAmout;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getUserFicNo() {
		return userFicNo;
	}

	public void setUserFicNo(String userFicNo) {
		this.userFicNo = userFicNo;
	}

	public String getResultNo() {
		return resultNo;
	}

	public void setResultNo(String resultNo) {
		this.resultNo = resultNo;
	}

	public String getResultDesc() {
		return resultDesc;
	}

	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}

	public Double getPound() {
		return pound;
	}

	public void setPound(Double pound) {
		this.pound = pound;
	}
}










