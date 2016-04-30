package com.jhl.pojo.weixin.req;

/**
 * 请求消息之图片消息
 * @author liurui
 * @2016年3月5日 @上午2:23:37
 */
public class ImageMessage extends BaseMessage{

	// 图片链接
	private String PicUrl;

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}



}
