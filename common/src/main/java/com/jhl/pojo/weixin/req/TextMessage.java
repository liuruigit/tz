package com.jhl.pojo.weixin.req;

/**
 * 请求消息之文本消息
 * @author liurui
 * @2016年3月5日 @上午2:21:52
 */

public class TextMessage extends BaseMessage{
	// 消息内容
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}



}
