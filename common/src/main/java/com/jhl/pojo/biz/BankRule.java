package com.jhl.pojo.biz;

import com.jhl.annotation.PrimaryKey;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.Date;


/**
*
* 类名称：BankRule.java
* 类描述：
* @author Hally
* 作者单位：
* 联系方式：18929353864
* 创建时间：2016-03-02
* @version 1.0
*/
public class BankRule {
	@PrimaryKey
	private int id;
	/** 银行名称 */
	private String bankName;
	/** 银行简称 */
	private String bankShortName;
	/** 单笔限额 */
	private Double limit;
	/** 单日限额 */
	private Double dayLimit;
	/** 起始时间 */
	private Date timeLimitBegin;
	/** 结束时间 */
	private Date timeLimitEnd;
	/** 渠道名称 */
	private String channelName;
	/** 逻辑删除标志位：1正常0删除 */
	private Integer deleteState;

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankShortName() {
		return bankShortName;
	}

	public void setBankShortName(String bankShortName) {
		this.bankShortName = bankShortName;
	}

	public Double getLimit() {
		return limit;
	}

	public void setLimit(Double limit) {
		this.limit = limit;
	}

	public Double getDayLimit() {
		return dayLimit;
	}

	public void setDayLimit(Double dayLimit) {
		this.dayLimit = dayLimit;
	}

	public Date getTimeLimitBegin() {
		return timeLimitBegin;
	}

	public void setTimeLimitBegin(Date timeLimitBegin) {
		this.timeLimitBegin = timeLimitBegin;
	}

	public Date getTimeLimitEnd() {
		return timeLimitEnd;
	}

	public void setTimeLimitEnd(Date timeLimitEnd) {
		this.timeLimitEnd = timeLimitEnd;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public Integer getDeleteState() {
		return deleteState;
	}

	public void setDeleteState(Integer deleteState) {
		this.deleteState = deleteState;
	}
}










