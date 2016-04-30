package com.jhl.pojo.weixin.req;

/**
 * 请求消息之音频消息
 * @author liurui
 * @2016年3月5日 @上午2:30:33
 */
public class VoiceMessage extends BaseMessage {
	// 媒体ID
	private String MediaId;
	// 语音格式
	private String Format;
	public String getMediaId() {
		return MediaId;
	}
	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}
	public String getFormat() {
		return Format;
	}
	public void setFormat(String format) {
		Format = format;
	}



}
