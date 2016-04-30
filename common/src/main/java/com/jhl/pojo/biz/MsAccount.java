package com.jhl.pojo.biz;

import com.jhl.annotation.PrimaryKey;

import java.util.Date;


/**
*
* 类名称：MsAccount.java
* 类描述：
* @author Hally
* 作者单位：
* 联系方式：18929353864
* 创建时间：2016-03-01
* @version 1.0
*/
public class MsAccount {
	@PrimaryKey
	private int id;
	/** 用户名 */
	private String accName;
	/** 密码 */
	private String pwd;
	/** 交易密码 */
	private String tradePwd;
	/** 手势密码 */
	private String linePwd;
	/** token */
	private String token;
	/** 更新时间 */
	private Date updateTime;
	/** 创建时间 */
	private Date createTime;
	/** 账号状态 */
	private Integer status;
	/** 逻辑删除标志位：1正常0删除 */
	private Integer deleteStatus;
	/** 推荐人 */
	private Integer recommendId;
	/** 电话 */
	private String mobile;
	/** 邮箱 */
	private String email;
	/** 身份证号 */
	private String certNo;
	/** 真实姓名 */
	private String realName;
	/** 投资准备 */
	private Integer prepareStep;
	/** 等级 */
	private Integer lv;
	/** 积分 */
	private Integer point;
	/** 余额 */
	private Integer money;
	/** 冻结金额 */
	private Integer frozenMoney;
	/** 投资金额 */
	private Integer investMoney;
	/** 摘要 */
	private String digest;
	/** 版本号 */
	private Integer version;
	/** 累计收益 */
	private Integer accIncome;
	/** vip等级 */
	private Integer vip;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getDeleteStatus() {
		return deleteStatus;
	}

	public void setDeleteStatus(Integer deleteStatus) {
		this.deleteStatus = deleteStatus;
	}

	public Integer getRecommendId() {
		return recommendId;
	}

	public void setRecommendId(Integer recommendId) {
		this.recommendId = recommendId;
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

	public Integer getPrepareStep() {
		return prepareStep;
	}

	public void setPrepareStep(Integer prepareStep) {
		this.prepareStep = prepareStep;
	}

	public Integer getLv() {
		return lv;
	}

	public void setLv(Integer lv) {
		this.lv = lv;
	}

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}

	public Integer getFrozenMoney() {
		return frozenMoney;
	}

	public void setFrozenMoney(Integer frozenMoney) {
		this.frozenMoney = frozenMoney;
	}

	public Integer getInvestMoney() {
		return investMoney;
	}

	public void setInvestMoney(Integer investMoney) {
		this.investMoney = investMoney;
	}

	public String getDigest() {
		return digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getAccIncome() {
		return accIncome;
	}

	public void setAccIncome(Integer accIncome) {
		this.accIncome = accIncome;
	}

	public Integer getVip() {
		return vip;
	}

	public void setVip(Integer vip) {
		this.vip = vip;
	}
}










