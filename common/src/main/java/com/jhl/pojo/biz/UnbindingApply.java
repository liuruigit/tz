package com.jhl.pojo.biz;


import com.jhl.annotation.PrimaryKey;

import java.util.Date;

/**
*
* 类名称：UnbindingApply.java
* 类描述：
* @author Hally
* 作者单位：
* 联系方式：18929353864
* 创建时间：2016-02-26
* @version 1.0
*/
public class UnbindingApply {
	@PrimaryKey
	private int id;
	/** 用户ID */
	private Integer accId;
	/** 用户ID */
	private Integer lastUpdateId;
	/** 附件 */
	private String attach;
	/** 状态 */
	private Integer status;
	/** 创建日期 */
	private Date createTime;
	/** 更新日期 */
	private Date updateTime;
	/** 逻辑删除标志位 */
	private Integer deleteState = 1;

	public Integer getLastUpdateId() {
		return lastUpdateId;
	}

	public void setLastUpdateId(Integer lastUpdateId) {
		this.lastUpdateId = lastUpdateId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getAccId() {
		return accId;
	}

	public void setAccId(Integer accId) {
		this.accId = accId;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}










