package com.lily.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lily.util.HttpUtil;

@Component
@PropertySource("classpath:config.properties")
public class TuLingApi {

	public static final String TEXT = "100000";

	public static final String LINK = "200000";

	public static final String NEWS = "302000";

	private static final String APIURL = "http://www.tuling123.com/openapi/api";

	private static String KEY;

	@Value("${tuling.key}")
	public void setKEY(String key) {
		KEY = key;
	}

	public Map<String, String> getResult(String content, String userid) {
		TextRequest tx = new TextRequest(KEY, content, userid);
		Gson gs = new Gson();
		// 把实体类转成json
		String xmlStr = gs.toJson(tx);
		try {
			String result = HttpUtil.doPost(APIURL, xmlStr);
			System.out.println(result);
			// 把结果转成map集合
			Map<String, String> map = gs.fromJson(result, new TypeToken<Map<String, String>>() {
			}.getType());
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
