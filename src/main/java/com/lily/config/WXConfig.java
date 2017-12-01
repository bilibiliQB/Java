package com.lily.config;

public class WXConfig {

	private String token;

	private String encodingaeskey;

	private String appid;

	private String appsecret;
	
	public WXConfig(String token, String encodingaeskey, String appid, String appsecret) {
		super();
		this.token = token;
		this.encodingaeskey = encodingaeskey;
		this.appid = appid;
		this.appsecret = appsecret;
	}

	public String getToken() {
		return token;
	}

	public String getEncodingaeskey() {
		return encodingaeskey;
	}

	public String getAppid() {
		return appid;
	}

	public String getAppsecret() {
		return appsecret;
	}

}
