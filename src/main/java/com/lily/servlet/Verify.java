package com.lily.servlet;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lily.api.News;
import com.lily.api.TuLingApi;
import com.lily.message.response.Article;
import com.lily.message.response.NewsResponse;
import com.lily.message.response.TextResponse;
import com.lily.util.CryptoUtil;
import com.lily.util.MessageUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.dom4j.DocumentException;

public class Verify extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String echostr = request.getParameter("echostr");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String signature = request.getParameter("signature");
		PrintWriter out = response.getWriter();
		if (CryptoUtil.checkSignature(signature, timestamp, nonce)) {
			out.print(echostr);
		}
		out.close();
		out = null;
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml;charset=utf-8");

		String timeStamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String signature = request.getParameter("signature");
		String encrypt_type = request.getParameter("encrypt_type");

		if (encrypt_type == null || encrypt_type.equals("raw")) {
			// 当消息不加密时
			if (CryptoUtil.checkSignature(signature, timeStamp, nonce)) {
				PrintWriter out = response.getWriter();
				try {
					Map<String, String> map = MessageUtil.xmlToMap(request);
					String toUserName = (String) map.get("ToUserName");
					String fromUserName = (String) map.get("FromUserName");
					String msgType = (String) map.get("MsgType");
					String content = (String) map.get("Content");
					String message = null;
					if (msgType.equals(MessageUtil.MESSAGE_TYPE_TEXT)) {
						TextResponse text = new TextResponse();
						text.setFromUserName(toUserName);
						text.setToUserName(fromUserName);
						text.setMsgType(MessageUtil.MESSAGE_TYPE_TEXT);
						text.setCreateTime(new Date().getTime());
						text.setContent("你发送的消息是：" + content);
						message = MessageUtil.messageToXML(text);
						System.out.println(message);
					}
					out.print(message);
				} catch (DocumentException e) {
					e.printStackTrace();
				} finally {
					out.close();
				}
			}
		} else {
			// 当消息加密时

			// 微信加密签名
			String msgSignature = request.getParameter("msg_signature");

			// 密文
			String encrypt_msg = MessageUtil.read(request);

			if (CryptoUtil.checkSignature(signature, timeStamp, nonce)) {
				PrintWriter out = response.getWriter();
				try {
					String data = CryptoUtil.decryptMsg(msgSignature, timeStamp, nonce, encrypt_msg);
					Map<String, String> map = MessageUtil.xmlToMap(data);
					String toUserName = (String) map.get("ToUserName");
					String fromUserName = (String) map.get("FromUserName");
					String msgType = (String) map.get("MsgType");
					String message = null;
					TuLingApi tl = new TuLingApi();
					if (msgType.equals(MessageUtil.MESSAGE_TYPE_TEXT)) {
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
						} 
						/*
						else if (code.equals(TuLingApi.NEWS)) {
							String nStr = rs.get("list");
							System.out.println("----------" + nStr);
							Gson gs = new Gson();
							List<News> nlist = gs.fromJson(nStr, new TypeToken<List<News>>() {
							}.getType());
							News news = null;
							Article art = null;
							List<Article> arts = new ArrayList<Article>();
							NewsResponse nsr = new NewsResponse();
							int count = 0;
							if (nlist.size() > 8) {
								count = 8;
							} else {
								count = nlist.size();
							}
							nsr.setArticleCount(count);
							for (int i = 0; i < count; i++) {
								news = nlist.get(i);
								art = new Article(news.getSource(), news.getArticle(), "", news.getSource());
								arts.add(art);
							}
							nsr.setArticles(arts);
							nsr.setCreateTime(new Date().getTime());
							nsr.setFromUserName(toUserName);
							nsr.setToUserName(fromUserName);
							nsr.setMsgType(MessageUtil.MESSAGE_TYPE_NEWS);
							message = MessageUtil.messageToXML(nsr);
						}
						else if(code.equals(TuLingApi.LINK)){
							TextResponse text = new TextResponse();
							text.setFromUserName(toUserName);
							text.setToUserName(fromUserName);
							text.setMsgType(MessageUtil.MESSAGE_TYPE_TEXT);
							text.setCreateTime(new Date().getTime());
							text.setContent(rs.get("text") + "\n" + rs.get("url"));
							message = MessageUtil.messageToXML(text);
						}*/
						else {
							TextResponse text = new TextResponse();
							text.setFromUserName(toUserName);
							text.setToUserName(fromUserName);
							text.setMsgType(MessageUtil.MESSAGE_TYPE_TEXT);
							text.setCreateTime(new Date().getTime());
							text.setContent("识别不了的信息");
							message = MessageUtil.messageToXML(text);
						}
					} else if (msgType.equals(MessageUtil.MESSAGE_TYPE_EVENT)) {
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
					out.print(encrypt_msg);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					out.close();
				}
			}

		}

	}
}
