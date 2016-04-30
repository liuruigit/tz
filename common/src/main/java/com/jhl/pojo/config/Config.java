package com.jhl.pojo.config;

import com.jhl.annotation.PrimaryKey;

/**
*
* 类名称：Config.java
* 类描述：
* @author Hally
* 作者单位：
* 联系方式：18929353864
* 创建时间：2016-01-23
* @version 1.0
*/
public class Config {
	@PrimaryKey
	private int id;
	/** 配置KEY */
	private String key;
	/** 配置value */
	private String value;
	/** 描述 */
	private String desr;
	/** 逻辑删除 */
	private String deleteState;

	public String getDeleteState() {
		return deleteState;
	}

	public void setDeleteState(String deleteState) {
		this.deleteState = deleteState;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDesr() {
		return desr;
	}

	public void setDesr(String desr) {
		this.desr = desr;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}










