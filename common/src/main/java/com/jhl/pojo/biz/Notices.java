package com.jhl.pojo.biz;

import com.jhl.annotation.PrimaryKey;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.Date;

/**
*
* 类名称：Notices.java
* 类描述：
* @author Hally
* 作者单位：
* 联系方式：18929353864
* 创建时间：2016-01-24
* @version 1.0
*/
public class Notices {
	@PrimaryKey
	/** id */
	private Integer noticesId;
	/** 标题 */
	private String title;
	/** 富文本内容 */
	private String content;
	/** 轮播图片URL */
	private String pictureUrl;
	/** 创建日期 */
	private Date createTime;
	/** 逻辑删除标志位：1正常0删除 */
	private Integer deleteState;
    /**
     * 内容分类
     */
    private String contentType;

    /**
     * 置顶排序
     */
    private Integer index;

	public Integer getNoticesId() {
		return noticesId;
	}

	public void setNoticesId(Integer noticesId) {
		this.noticesId = noticesId;
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

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
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

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}










