package com.lily.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:config.properties")
public class AccessTokenUtil {

	private static String accessToken = null;

	// 应用的AppId
	private static String APPID;

	@Value("${wx.appid}")
	private void setAPPID(String appid) {
		APPID = appid;
	}

	// APPSECRET
	private static String APPSECRET;
	
	@Value("${wx.appsecret}")
	private void setAPPSECRET(String appsecret) {
		APPSECRET = appsecret;
	}

	public synchronized static String getAccessToken() {
		if (accessToken == null) {

		}
		return null;
	}
	/*
	 * public static String get() { String path =
	 * "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	 * path.replace("APPID", APPID); path.replace("APPSECRET", APPSECRET);
	 * return HttpUtil.doGet(path); }
	 */

}
