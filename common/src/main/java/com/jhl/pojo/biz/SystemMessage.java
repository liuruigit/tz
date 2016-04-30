package com.jhl.pojo.biz;

import com.jhl.annotation.PrimaryKey;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.Date;


/**
*
* 类名称：SystemMessage.java
* 类描述：
* @author Hally
* 作者单位：
* 联系方式：18929353864
* 创建时间：2016-03-29
* @version 1.0
*/
public class SystemMessage {
	@PrimaryKey
	private int id;
	/** 消息内容 */
	private String body;
	/** 创建日期 */
	private Date createTime;
	/** 更新日期 */
	private Date updateTime;
	/** 逻辑删除标志位：1正常0删除 */
	private Integer deleteState;

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
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

	public Integer getDeleteState() {
		return deleteState;
	}

	public void setDeleteState(Integer deleteState) {
		this.deleteState = deleteState;
	}
}










