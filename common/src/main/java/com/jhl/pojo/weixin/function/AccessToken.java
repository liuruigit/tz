package com.jhl.pojo.weixin.function;

/**
 * 微信通用接口凭证
 * @author liurui
 * @2016年3月5日 @下午5:42:09
 */
public class AccessToken {
	// 获取到的凭证   
	private String token;  
	// 凭证有效时间，单位：秒   
	private int expiresIn;
	//token的保存时间
	private Long date;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public int getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}
}
