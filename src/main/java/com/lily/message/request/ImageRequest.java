package com.lily.message.request;

public class ImageRequest extends BaseRequest {
	// 图片链接
	private String PicUrl;
	// 图片消息媒体ID
	private String MediaId;

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}
}
