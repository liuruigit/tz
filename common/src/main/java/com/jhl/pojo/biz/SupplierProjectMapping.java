package com.jhl.pojo.biz;

import com.jhl.annotation.PrimaryKey;


/**
*
* 类名称：SupplierProjectMapping.java
* 类描述：
* @author Hally
* 作者单位：
* 联系方式：18929353864
* 创建时间：2016-04-04
* @version 1.0
*/
public class SupplierProjectMapping {
	@PrimaryKey
	private int id;
	/** 统一社会信用代码 */
	private String gsCode;
	/** 经营场所 */
	private String location;
	/** 甲方名称 */
	private String name;
	/** 合伙人认缴出资 */
	private String totalAmount;
	/** 合伙人实缴 */
	private String realTotalAmount;
	/** 甲方已实缴 */
	private String supplierRealAmount;
	/** 甲方持有财产份额 */
	private String supplierHoldPerc;
	/** 配置value */
	private String proId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGsCode() {
		return gsCode;
	}

	public void setGsCode(String gsCode) {
		this.gsCode = gsCode;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getRealTotalAmount() {
		return realTotalAmount;
	}

	public void setRealTotalAmount(String realTotalAmount) {
		this.realTotalAmount = realTotalAmount;
	}

	public String getSupplierRealAmount() {
		return supplierRealAmount;
	}

	public void setSupplierRealAmount(String supplierRealAmount) {
		this.supplierRealAmount = supplierRealAmount;
	}

	public String getSupplierHoldPerc() {
		return supplierHoldPerc;
	}

	public void setSupplierHoldPerc(String supplierHoldPerc) {
		this.supplierHoldPerc = supplierHoldPerc;
	}

	public String getProId() {
		return proId;
	}

	public void setProId(String proId) {
		this.proId = proId;
	}
}










