package com.lily.message.request;
// 文本消息
public class TextRequest extends BaseRequest {
	// 消息内容
	private String Content; 

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
}
