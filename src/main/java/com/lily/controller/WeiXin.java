package com.lily.controller;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lily.api.TuLingApi;
import com.lily.message.response.TextResponse;
import com.lily.util.CryptoUtil;
import com.lily.util.MessageUtil;

@Controller
public class WeiXin {

	/*
	 * @ResponseBody
	 * 
	 * @RequestMapping(value = "Verify", method = RequestMethod.GET) public
	 * String verify(HttpServletRequest request, String echostr, String
	 * timestamp, String nonce, String signature) { if
	 * (CryptoUtil.checkSignature(signature, timestamp, nonce)) { return
	 * echostr; } else { return null; } }
	 */

	@ResponseBody
	@RequestMapping(value = "Verify", method = RequestMethod.POST)
	public String verify(HttpServletRequest request, String timestamp, String nonce, String signature,
			String encrypt_type, String msg_signature) {
		// 密文
		String encrypt_msg = MessageUtil.read(request);

		if (CryptoUtil.checkSignature(signature, timestamp, nonce)) {
			try {
				String data = CryptoUtil.decryptMsg(msg_signature, timestamp, nonce, encrypt_msg);
				Map<String, String> map = MessageUtil.xmlToMap(data);
				String toUserName = (String) map.get("ToUserName");
				String fromUserName = (String) map.get("FromUserName");
				String msgType = (String) map.get("MsgType");
				String message = null;
				TuLingApi tl = new TuLingApi();
				if (MessageUtil.MESSAGE_TYPE_TEXT.equals(msgType)) {
					// 收到文本信息
					String content = (String) map.get("Content");
					// 往第三方发送消息并返回数据
					Map<String, String> rs = tl.getResult(content, fromUserName);
					String code = rs.get("code");
					if (code.equals(TuLingApi.TEXT)) {
						TextResponse text = new TextResponse();
						text.setFromUserName(toUserName);
						text.setToUserName(fromUserName);
						text.setMsgType(MessageUtil.MESSAGE_TYPE_TEXT);
						text.setCreateTime(new Date().getTime());
						text.setContent(rs.get("text"));
						message = MessageUtil.messageToXML(text);
					} else {
						TextResponse text = new TextResponse();
						text.setFromUserName(toUserName);
						text.setToUserName(fromUserName);
						text.setMsgType(MessageUtil.MESSAGE_TYPE_TEXT);
						text.setCreateTime(new Date().getTime());
						text.setContent("识别不了的信息");
						message = MessageUtil.messageToXML(text);
					}
				} else if (MessageUtil.MESSAGE_TYPE_EVENT.equals(msgType)) {
					String event = map.get("Event");
					if (event.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
						TextResponse text = new TextResponse();
						text.setFromUserName(toUserName);
						text.setToUserName(fromUserName);
						text.setMsgType(MessageUtil.MESSAGE_TYPE_TEXT);
						text.setCreateTime(new Date().getTime());
						text.setContent("感谢您的关注！");
						message = MessageUtil.messageToXML(text);
					}
				}
				encrypt_msg = CryptoUtil.ecryptMsg(message, "", nonce);
				return encrypt_msg;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
	}
}
