package com.jhl.pojo.biz;

import com.jhl.annotation.PrimaryKey;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.Date;


/**
*
* 类名称：InviteRewardConfig.java
* 类描述：
* @author Hally
* 作者单位：
* 联系方式：18929353864
* 创建时间：2016-03-27
* @version 1.0
*/
public class InviteRewardConfig {
	@PrimaryKey
	private int id;
	/** 期限 */
	private String days;
	/** 投资金额（起） */
	private String rangeStart;
	/** 投资金额（止） */
	private String rangeEnd;
	/** 配置value */
	private String perc;
	/** 是否开启 */
	private String isOpen;
	private double limit;

	public double getLimit() {
		return limit;
	}

	public void setLimit(double limit) {
		this.limit = limit;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public String getRangeStart() {
		return rangeStart;
	}

	public void setRangeStart(String rangeStart) {
		this.rangeStart = rangeStart;
	}

	public String getRangeEnd() {
		return rangeEnd;
	}

	public void setRangeEnd(String rangeEnd) {
		this.rangeEnd = rangeEnd;
	}

	public String getPerc() {
		return perc;
	}

	public void setPerc(String perc) {
		this.perc = perc;
	}

	public String getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
	}

	@Override
	public String toString() {
		return "InviteRewardConfig{" +
				"days='" + days + '\'' +
				", id=" + id +
				", isOpen='" + isOpen + '\'' +
				", limit=" + limit +
				", perc='" + perc + '\'' +
				", rangeEnd='" + rangeEnd + '\'' +
				", rangeStart='" + rangeStart + '\'' +
				'}';
	}
}










