package com.jhl.entity.biz;

import java.util.Date;

/**
*
* 类名称：User.java
* 类描述：
* @author Hally
* 作者单位：
* 联系方式：18929353864
* 创建时间：2016-01-15
* @version 1.0
*/
public class User {

	/** 账号 */
	private String accName;
	/** 密码 */
	private String pwd;
	/** 交易密码 */
	private String tradePwd;
	/** 手势密码 */
	private String linePwd;
	/** token */
	private String token;
	/** 手机号 */
	private String mobile;
	/** 邮箱 */
	private String email;
	/** 创建时间 */
	private Date createTime;
	/** 上次登录时间 */
	private Date lastLoginTime;
	/** 状态 */
	private Integer status;
	/** 1正常0删除 */
	private Integer deleteState;
	/** 身份证号 */
	private String certNo;
	/** 真实姓名 */
	private String realName;
	/** 是否实名 */
	private Integer isRealName;
	/** 余额 */
	private Double money;
	/** 冻结金额 */
	private Double freezeMoney;
	/** 上次登录设备 */
	private String lastLoginImei;

	public String getAccName() {
		return accName;
	}

	public void setAccName(String accName) {
		this.accName = accName;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getTradePwd() {
		return tradePwd;
	}

	public void setTradePwd(String tradePwd) {
		this.tradePwd = tradePwd;
	}

	public String getLinePwd() {
		return linePwd;
	}

	public void setLinePwd(String linePwd) {
		this.linePwd = linePwd;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getDeleteState() {
		return deleteState;
	}

	public void setDeleteState(Integer deleteState) {
		this.deleteState = deleteState;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Integer getIsRealName() {
		return isRealName;
	}

	public void setIsRealName(Integer isRealName) {
		this.isRealName = isRealName;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Double getFreezeMoney() {
		return freezeMoney;
	}

	public void setFreezeMoney(Double freezeMoney) {
		this.freezeMoney = freezeMoney;
	}

	public String getLastLoginImei() {
		return lastLoginImei;
	}

	public void setLastLoginImei(String lastLoginImei) {
		this.lastLoginImei = lastLoginImei;
	}
}










