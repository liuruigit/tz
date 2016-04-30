package com.jhl.pojo.biz;

import com.jhl.annotation.PrimaryKey;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
*
* 类名称：ContentType.java
* 类描述：
* @author Hally
* 作者单位：
* 联系方式：18929353864
* 创建时间：2016-01-29
* @version 1.0
*/
public class ContentType {
	@PrimaryKey
	private int id;
	/** 网站内容KEY */
	private String type_key;
	/** 网站内容类型描述 */
	private String type_des;

	public String getType_key() {
		return type_key;
	}

	public void setType_key(String type_key) {
		this.type_key = type_key;
	}

	public String getType_des() {
		return type_des;
	}

	public void setType_des(String type_des) {
		this.type_des = type_des;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}










