package com.jhl.pojo.weixin.resp;

/**
 * 响应消息之音乐消息
 * @author liurui
 * @2016年3月5日 @上午2:37:21
 */
public class MusicMessage extends BaseMessage{
	// 音乐
	private Music Music;

	public Music getMusic() {
		return Music;
	}

	public void setMusic(Music music) {
		Music = music;
	}


}
