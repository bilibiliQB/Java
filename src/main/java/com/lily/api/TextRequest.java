package com.lily.api;

public class TextRequest {

	private String key;
	
	private String info;
	
	private String userid;
	
	public TextRequest() {
		
	}

	public TextRequest(String key, String info, String userid) {
		this.key = key;
		this.info = info;
		this.userid = userid;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
}
