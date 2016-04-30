package com.jhl.entity.biz;

import java.util.List;
import java.util.Date;

/**
*
* 类名称：UserLogin.java
* 类描述：
* @author Hally
* 作者单位：
* 联系方式：18929353864
* 创建时间：2016-01-17
* @version 1.0
*/
public class UserLogin {

	/** id */
	private Integer id;
	/** 账号ID */
	private Integer userId;
	/** 登录设备 */
	private String imei;
	/** 登录时间 */
	private Date time;
	/** Token */
	private String token;
	/** 登录类型 */
	private Integer type;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}










