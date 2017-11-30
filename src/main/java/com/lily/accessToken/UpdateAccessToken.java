package com.lily.accessToken;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lily.service.AccessTokenService;
import com.lily.util.HttpUtil;

@Component
@PropertySource("classpath:config.properties")
public class UpdateAccessToken {

	private static Logger logger = Logger.getLogger(UpdateAccessToken.class);

	private static AccessTokenService accessTokenService;
	
	@Autowired
	private void setAccessTokenService(AccessTokenService accessTokenService) {
		UpdateAccessToken.accessTokenService = accessTokenService;
	}

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

	public static void updateAccessToken() throws AccessTokenException {
		StringBuffer url = new StringBuffer();
		url.append("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=");
		url.append(APPID);
		url.append("&secret=");
		url.append(APPSECRET);
		String result = HttpUtil.doGet(url.toString());
		Gson gs = new Gson();
		Map<String, String> map = gs.fromJson(result, new TypeToken<Map<String, String>>() {
		}.getType());
		String access_token = map.get("access_token");
		if (access_token != null) {
			logger.info("access_token为:" + access_token);
			// 把所得到的结果存入数据库
			// SaveToDataBase(access_token);
		} else {
			int errcode = Integer.parseInt(map.get("errcode"));
			if (errcode == AccessTokenException.SystemBusyError) {
				throw new AccessTokenException(AccessTokenException.SystemBusyError);
			} else if (errcode == AccessTokenException.ValidateAppSecretError) {
				throw new AccessTokenException(AccessTokenException.ValidateAppSecretError);
			} else if (errcode == AccessTokenException.ValidateGrantTypeError) {
				throw new AccessTokenException(AccessTokenException.ValidateGrantTypeError);
			} else if (errcode == AccessTokenException.ValidateIPError) {
				throw new AccessTokenException(AccessTokenException.ValidateIPError);
			} else if (errcode == AccessTokenException.ValidateAppIdError) {
				throw new AccessTokenException(AccessTokenException.ValidateAppIdError);
			}
		}

	}

	private static void SaveToDataBase(String access_token) {
		int num = accessTokenService.updateAccessToken(access_token);
		if (num == 0) {
			logger.error("access_token存到数据库时发生错误");
		}
	}

}
