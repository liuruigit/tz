package com.jhl.pojo.biz;

import com.jhl.annotation.PrimaryKey;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;

/**
*
* 类名称：ProjectInfo.java
* 类描述：
* @author Hally
* 作者单位：
* 联系方式：18929353864
* 创建时间：2016-01-21
* @version 1.0
*/
public class ProjectInfo implements Serializable {
	@PrimaryKey
	private int id;
	/** 项目名称 */
	private String name;
	/** 资产类型 */
	private Integer propertyType;
	/** 地域 */
	private String location;
	/** 产权面积 */
	private Double area;
	/** 出售价格 */
	private String sellPrice;
	/** 市场价 */
	private String marketPrice;
	/** 产权证 */
	private Integer propertyCert;
	/** 土地证 */
	private Integer landCert;
	/** 资产权属 */
	private Integer propertyOwner;
	/** 物权情况 */
	private String propertyRight;
	/** 附加信息 */
	private String extraInfo;
	/** 买卖合同 */
	private String contract;
	/** 补充协议 */
	private String addedContract;
	/** 房源清单 */
	private String propertyList;
	/** 备案摘要 */
	private String record;
	/** 创建时间 */
	private Date createTime;
	/**
	 * 1正常0删除
	 */
	private Integer deleteState;

	public Integer getDeleteState() {
		return deleteState;
	}

	public void setDeleteState(Integer deleteState) {
		this.deleteState = deleteState;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(Integer propertyType) {
		this.propertyType = propertyType;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Double getArea() {
		return area;
	}

	public void setArea(Double area) {
		this.area = area;
	}

	public String getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(String sellPrice) {
		this.sellPrice = sellPrice;
	}

	public String getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(String marketPrice) {
		this.marketPrice = marketPrice;
	}

	public Integer getPropertyCert() {
		return propertyCert;
	}

	public void setPropertyCert(Integer propertyCert) {
		this.propertyCert = propertyCert;
	}

	public Integer getLandCert() {
		return landCert;
	}

	public void setLandCert(Integer landCert) {
		this.landCert = landCert;
	}

	public Integer getPropertyOwner() {
		return propertyOwner;
	}

	public void setPropertyOwner(Integer propertyOwner) {
		this.propertyOwner = propertyOwner;
	}

	public String getPropertyRight() {
		return propertyRight;
	}

	public void setPropertyRight(String propertyRight) {
		this.propertyRight = propertyRight;
	}

	public String getExtraInfo() {
		return extraInfo;
	}

	public void setExtraInfo(String extraInfo) {
		this.extraInfo = extraInfo;
	}

	public String getContract() {
		return contract;
	}

	public void setContract(String contract) {
		this.contract = contract;
	}

	public String getAddedContract() {
		return addedContract;
	}

	public void setAddedContract(String addedContract) {
		this.addedContract = addedContract;
	}

	public String getPropertyList() {
		return propertyList;
	}

	public void setPropertyList(String propertyList) {
		this.propertyList = propertyList;
	}

	public String getRecord() {
		return record;
	}

	public void setRecord(String record) {
		this.record = record;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}










