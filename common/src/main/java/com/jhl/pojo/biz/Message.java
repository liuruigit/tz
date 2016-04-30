package com.jhl.pojo.biz;

import com.jhl.annotation.PrimaryKey;
import com.jhl.annotation.Transient;

import java.util.Date;

/**
*
* 类名称：Message.java
* 类描述：
* @author Hally
* 作者单位：
* 联系方式：18929353864
* 创建时间：2016-01-30
* @version 1.0
 *
*/
public class Message {
	@PrimaryKey
	private int id;
	/** 用户ID */
	private int accId;
	/** 系统消息 0系统1个人*/
	private int systemMsg;
	/** 标题 */
	private String title;
	/** 富文本内容 */
	private String content;
	/** 创建日期 */
	private Date createTime;
	/** 逻辑删除标志位：1正常0删除 */
	private Integer deleteState;
	/** 是否阅读的标志位:0未读1已读 */
	private Integer isRead;

	@Transient
	public static final int IS_READ_NO = 0;
	@Transient
	public static final int IS_READ_YES = 1;
	@Transient
	public static final int SYS = 0;
	@Transient
	public static final int PERSONAL = 1;

	public Integer getIsRead() {
		return isRead;
	}

	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAccId() {
		return accId;
	}

	public void setAccId(int accId) {
		this.accId = accId;
	}

	public int getSystemMsg() {
		return systemMsg;
	}

	public void setSystemMsg(int systemMsg) {
		this.systemMsg = systemMsg;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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










