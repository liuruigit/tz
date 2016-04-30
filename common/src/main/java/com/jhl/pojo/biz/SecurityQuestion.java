package com.jhl.pojo.biz;

import com.jhl.annotation.PrimaryKey;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.Date;


/**
*
* 类名称：SecurityQuestion.java
* 类描述：
* @author Hally
* 作者单位：
* 联系方式：18929353864
* 创建时间：2016-03-02
* @version 1.0
*/
public class SecurityQuestion {
	@PrimaryKey
	private int id;
	/** 用户 */
	private Integer accId;
	/** 问题 */
	private String question;
	/** 答案 */
	private String answer;
	/** 更新时间 */
	private Date updateTime;
	/** 创建时间 */
	private Date createTime;
	/** 逻辑删除标志位 */
	private Integer deleteState;

	public Integer getAccId() {
		return accId;
	}

	public void setAccId(Integer accId) {
		this.accId = accId;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
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

	public Integer getDeleteState() {
		return deleteState;
	}

	public void setDeleteState(Integer deleteState) {
		this.deleteState = deleteState;
	}
}










