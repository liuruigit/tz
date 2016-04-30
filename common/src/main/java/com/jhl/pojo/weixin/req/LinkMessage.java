package com.jhl.pojo.weixin.req;

/**
 * 请求消息之链接消息
 * @author liurui
 * @2016年3月5日 @上午2:28:07
 */
public class LinkMessage extends BaseMessage{

	// 消息标题
	private String Title;
	// 消息描述
	private String Description;
	// 消息链接
	private String Url;
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getUrl() {
		return Url;
	}
	public void setUrl(String url) {
		Url = url;
	}



}
