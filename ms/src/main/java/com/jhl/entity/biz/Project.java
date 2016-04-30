package com.jhl.entity.biz;

import java.util.Date;

/**
*
* 类名称：Project.java
* 类描述：
* @author Hally
* 作者单位：
* 联系方式：18929353864
* 创建时间：2016-01-14
* @version 1.0
*/
public class Project {

	/** 项目名称 */
	private String name;
	/** 产品编号 */
	private String no;
	/** 保障级别 */
	private Integer guarantee;
	/** 付息方式 */
	private Integer payInterestWay;
	/** 融资金额 */
	private Double amount;
	/** 结算金额 */
	private Double finalAmount;
	/** 已融资金额 */
	private Double selledAmount;
	/** 市场价 */
	private Double marketPrice;
	/** 起投金额 */
	private Double min;
	/** 累加金额 */
	private Double step;
	/** 限投金额 */
	private Double limit;
	/** 预期年化收益率 */
	private String annualRate;
	/** 服务费率 */
	private Double serviceRate;
	/** 开放购买时间 */
	private Date openDate;
	/** 结束时间 */
	private Date endDate;
	/** 售罄时间 */
	private Date soldOutDate;
	/** 状态 */
	private Integer status;
	/** 项目描述 */
	private String desr;
	/** 创建时间 */
	private String createTime;
	/** 是否推荐 */
	private Integer recommend;
	/** 预留信息1 */
	private String extra1;
	/** 预留信息2 */
	private String extra2;
	/** 预留信息3 */
	private String extra3;

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getSoldOutDate() {
		return soldOutDate;
	}

	public void setSoldOutDate(Date soldOutDate) {
		this.soldOutDate = soldOutDate;
	}

	public Double getFinalAmount() {
		return finalAmount;
	}

	public void setFinalAmount(Double finalAmount) {
		this.finalAmount = finalAmount;
	}

	public String getAnnualRate() {
		return annualRate;
	}

	public void setAnnualRate(String annualRate) {
		this.annualRate = annualRate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public Integer getGuarantee() {
		return guarantee;
	}

	public void setGuarantee(Integer guarantee) {
		this.guarantee = guarantee;
	}

	public Integer getPayInterestWay() {
		return payInterestWay;
	}

	public void setPayInterestWay(Integer payInterestWay) {
		this.payInterestWay = payInterestWay;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getSelledAmount() {
		return selledAmount;
	}

	public void setSelledAmount(Double selledAmount) {
		this.selledAmount = selledAmount;
	}

	public Double getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(Double marketPrice) {
		this.marketPrice = marketPrice;
	}

	public Double getMin() {
		return min;
	}

	public void setMin(Double min) {
		this.min = min;
	}

	public Double getStep() {
		return step;
	}

	public void setStep(Double step) {
		this.step = step;
	}

	public Double getLimit() {
		return limit;
	}

	public void setLimit(Double limit) {
		this.limit = limit;
	}

	public Double getServiceRate() {
		return serviceRate;
	}

	public void setServiceRate(Double serviceRate) {
		this.serviceRate = serviceRate;
	}

	public Date getOpenDate() {
		return openDate;
	}

	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getDesr() {
		return desr;
	}

	public void setDesr(String desr) {
		this.desr = desr;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getRecommend() {
		return recommend;
	}

	public void setRecommend(Integer recommend) {
		this.recommend = recommend;
	}

	public String getExtra1() {
		return extra1;
	}

	public void setExtra1(String extra1) {
		this.extra1 = extra1;
	}

	public String getExtra2() {
		return extra2;
	}

	public void setExtra2(String extra2) {
		this.extra2 = extra2;
	}

	public String getExtra3() {
		return extra3;
	}

	public void setExtra3(String extra3) {
		this.extra3 = extra3;
	}
}










