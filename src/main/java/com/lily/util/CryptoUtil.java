package com.lily.util;

import java.io.UnsupportedEncodingException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.weixin.aes.AesException;
import com.weixin.aes.WXBizMsgCrypt;

@Component
@PropertySource("classpath:config.properties")
public class CryptoUtil {

	// TOKEN
	private static String TOKEN;

	@Value("${wx.token}")
	private void setTOKEN(String token) {
		TOKEN = token;
	}

	// 微信生成的 ASEKey
	private static String ENCODINGAESKEY;

	@Value("${wx.encodingaeskey}")
	private void setENCODINGAESKEY(String encodingaeskey) {
		ENCODINGAESKEY = encodingaeskey;
	}

	// 应用的AppId
	private static String APPID;

	@Value("${wx.appid}")
	private void setAPPID(String appid) {
		APPID = appid;
	}

	// 解密微信发过来的密文
	public static String decryptMsg(String msgSignature, String timeStamp, String nonce, String encrypt_msg) {
		WXBizMsgCrypt pc;
		String result = "";
		try {
			pc = new WXBizMsgCrypt(TOKEN, ENCODINGAESKEY, APPID);
			result = pc.decryptMsg(msgSignature, timeStamp, nonce, encrypt_msg);
		} catch (AesException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 加密发给微信的消息
	public static String ecryptMsg(String replayMsg, String timeStamp, String nonce) {
		WXBizMsgCrypt pc;
		String result = "";
		try {
			pc = new WXBizMsgCrypt(TOKEN, ENCODINGAESKEY, APPID);
			result = pc.encryptMsg(replayMsg, timeStamp, nonce);
		} catch (AesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public static boolean checkSignature(String signature, String timestamp, String nonce) {
		String[] list = { TOKEN, timestamp, nonce };

		Arrays.sort(list);

		StringBuffer strs = new StringBuffer();
		for (String s : list) {
			strs.append(s);
		}
		String mid = getSha1(strs.toString());
		return signature.equals(mid);
	}

	public static String getSha1(String str) {
		if ((str == null) || (str.length() == 0)) {
			return null;
		}
		char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
			mdTemp.update(str.getBytes("UTF-8"));

			byte[] md = mdTemp.digest();
			int j = md.length;
			char[] buf = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				buf[(k++)] = hexDigits[(byte0 >>> 4 & 0xF)];
				buf[(k++)] = hexDigits[(byte0 & 0xF)];
			}
			return new String(buf);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

}
