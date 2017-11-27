package com.lily.message.request;
// 视频\短视频 消息
public class VideoRequest extends BaseRequest {
	// 视频消息媒体ID
	private String MediaId;
	// 视频消息缩略图的媒体ID
	private String ThumbMediaId;

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

	public String getThumbMediaId() {
		return ThumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		ThumbMediaId = thumbMediaId;
	}
}