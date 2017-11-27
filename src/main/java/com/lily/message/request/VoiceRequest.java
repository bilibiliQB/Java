package com.lily.message.request;
// 语音消息
public class VoiceRequest extends BaseRequest {
	// 媒体ID
	private String MediaId;
	// 语音格式
	private String Format;
	// ----------当开通语音识别时----------
	
	private String Recognition;// 语音识别结果

	public String getRecognition() {
		return Recognition;
	}

	public void setRecognition(String recognition) {
		Recognition = recognition;
	}

	// -----------------------------------
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