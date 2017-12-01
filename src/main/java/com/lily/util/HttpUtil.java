package com.lily.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class HttpUtil {

	private static Logger logger = Logger.getLogger(HttpUtil.class);

	public static String doGet(String url) {
		try {
			CloseableHttpClient client = HttpClients.createDefault();
			// 发送get请求
			HttpGet request = new HttpGet(url);
			// 设置请求和传输超时时间
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();
			request.setConfig(requestConfig);
			
			HttpResponse response = client.execute(request);
			/** 请求发送成功，并得到响应 **/
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				/** 读取服务器返回过来的json字符串数据 **/
				String strResult = EntityUtils.toString(response.getEntity(),"UTF-8");
				return strResult;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * post请求(用于key-value格式的参数)
	 */
	public static String doPost(String url, Map<String, String> params) {
		// BufferedReader in = null;
		try {
			CloseableHttpClient client = HttpClients.createDefault();
			// 实例化HTTP方法
			HttpPost request = new HttpPost(url);
			request.setHeader("Accept", "application/json");
			request.setHeader("Content-Type", "application/json");
			
			// 设置请求和传输超时时间  
	        RequestConfig requestConfig = RequestConfig.custom()  
	                .setSocketTimeout(2000).setConnectTimeout(2000).build();  
	        request.setConfig(requestConfig);

			// 设置参数
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			for (Iterator<String> iter = params.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String value = String.valueOf(params.get(name));
				nvps.add(new BasicNameValuePair(name, value));
			}
			
			request.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

			HttpResponse response = client.execute(request);
			
			int code = response.getStatusLine().getStatusCode();
			if (code == HttpStatus.SC_OK) { // 请求成功
				HttpEntity responseEntity = response.getEntity();
				String jsonString = EntityUtils.toString(responseEntity,"UTF-8");
				return jsonString;
		     /**
			  * in = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "utf-8"));
			  * StringBuffer sb = new StringBuffer("");
			  * String line = "";
			  * String NL = System.getProperty("line.separator");
			  * while ((line = in.readLine()) != null) {
			  *	sb.append(line + NL);
			  * }
			  *  in.close();
			  * return sb.toString();
			  */
			} else { 
				// 请求失败
				System.out.println("状态码：" + code);
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
	}

	/**
	 * post请求（用于请求json格式的参数）
	 */
	public static String doPost(String url, String params) throws Exception {

		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);// 创建httpPost
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json");
		String charSet = "UTF-8";
		StringEntity entity = new StringEntity(params, charSet);
		httpPost.setEntity(entity);
		CloseableHttpResponse response = null;

		try {

			response = httpclient.execute(httpPost);
			StatusLine status = response.getStatusLine();
			int state = status.getStatusCode();
			if (state == HttpStatus.SC_OK) {
				HttpEntity responseEntity = response.getEntity();
				String jsonString = EntityUtils.toString(responseEntity,"UTF-8");
				return jsonString;
			} else {
				logger.error("请求返回:" + state + "(" + url + ")");
			}
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
