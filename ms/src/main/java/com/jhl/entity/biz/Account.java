package com.jhl.entity.biz;

import java.util.Date;

/**
*
* 类名称：Account.java
* 类描述：
* @author Hally
* 作者单位：
* 联系方式：18929353864
* 创建时间：2016-01-14
* @version 1.0
*/
public class Account {

	/** 账号 */
	private String accountName;
	/** 密码 */
	private String pwd;
	/** 账号类型0顾客1店主 */
	private Integer accType;
	/** 创建时间 */
	private Date createTime;
	/** 上次登录时间 */
	private Date lastLoginTime;

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public Integer getAccType() {
		return accType;
	}

	public void setAccType(Integer accType) {
		this.accType = accType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
}










