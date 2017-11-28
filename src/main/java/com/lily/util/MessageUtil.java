package com.lily.util;

import com.lily.message.response.Article;
import com.lily.message.response.ImageResponse;
import com.lily.message.response.MusicResponse;
import com.lily.message.response.NewsResponse;
import com.lily.message.response.TextResponse;
import com.lily.message.response.VideoResponse;
import com.lily.message.response.VoiceResponse;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class MessageUtil {
	// --------------------------------------------------------------

	public static final String MESSAGE_TYPE_TEXT = "text";

	public static final String MESSAGE_TYPE_IMAGE = "image";

	public static final String MESSAGE_TYPE_VOICE = "voice";

	public static final String MESSAGE_TYPE_VIDEO = "video";

	public static final String MESSAGE_TYPE_SHORTVIDEO = "shortvideo";

	public static final String MESSAGE_TYPE_LOCATION = "location";

	public static final String MESSAGE_TYPE_LINK = "link";

	public static final String MESSAGE_TYPE_MUSIC = "music";

	public static final String MESSAGE_TYPE_NEWS = "news";

	// ---------------------------------------------------------------

	public static final String MESSAGE_TYPE_EVENT = "event";

	// 事件类型：subscribe(订阅)
	public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";
	// 事件类型：unsubscribe(取消订阅)
	public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";
	// 事件类型：scan(用户已关注时的扫描带参数的二维码)
	public static final String EVENT_TYPE_SCAN = "SCAN";
	// 事件类型：LOCATION(上报地理位置)
	public static final String EVENT_TYPE_LOCATION = "LOCATION";
	// 事件类型：CLICK(自定义菜单)
	public static final String EVENT_TYPE_CLICK = "CLICK";

	// ---------------------------------------------------------------

	private static XStream xstream = new XStream(new XppDriver() {
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {
				boolean cdata = true;

				public void startNode(String name, @SuppressWarnings("rawtypes") Class clazz) {
					super.startNode(name, clazz);
				}

				protected void writeText(QuickWriter writer, String text) {
					if (cdata) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					} else {
						writer.write(text);
					}
				}
			};
		}
	});

	/*
	 * 把密文从request中取出
	 * 
	 * @param request
	 */
	public static String read(HttpServletRequest request) {
		StringBuffer strs = new StringBuffer();
		String str = null;
		try {
			InputStreamReader isr = new InputStreamReader(request.getInputStream(), "UTF-8");
			BufferedReader read = new BufferedReader(isr);
			while ((str = read.readLine()) != null) {
				strs.append(str);
			}
			read.close();
			isr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return strs.toString();
	}

	// 不加密时直接从request中取出成map
	public static Map<String, String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException {
		Map<String, String> map = new HashMap<String, String>();
		SAXReader reader = new SAXReader();
		InputStream ins = null;
		try {
			ins = request.getInputStream();
			Document doc = reader.read(ins);
			Element root = doc.getRootElement();
			@SuppressWarnings("unchecked")
			List<Element> list = root.elements();
			for (Element e : list) {
				map.put(e.getName(), e.getText());
				System.out.println(e.getName() + "--->" + e.getText());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ins.close();
		}
		return map;
	}

	// 把xml格式的字符串转化为map集合
	public static Map<String, String> xmlToMap(String xmlStr) throws DocumentException {
		Map<String, String> map = new HashMap<String, String>();
		try {
			Document doc = DocumentHelper.parseText(xmlStr);
			Element root = doc.getRootElement();
			@SuppressWarnings("unchecked")
			List<Element> list = root.elements();
			for (Element e : list) {
				map.put(e.getName(), e.getText());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	// 文字 --> XML
	public static String messageToXML(TextResponse text) {
		xstream.alias("xml", text.getClass());
		return xstream.toXML(text);
	}

	// 图片 --> XML
	public static String messageToXML(ImageResponse image) {
		xstream.alias("xml", image.getClass());
		return xstream.toXML(image);
	}

	// 音乐 --> XML
	public static String messageToXML(MusicResponse music) {
		xstream.alias("xml", music.getClass());
		return xstream.toXML(music);
	}

	// 语音 --> XML
	public static String messageToXML(VoiceResponse voice) {
		xstream.alias("xml", voice.getClass());
		return xstream.toXML(voice);
	}

	// 视频 --> XML
	public static String messageToXML(VideoResponse video) {
		xstream.alias("xml", video.getClass());
		return xstream.toXML(video);
	}

	// 图文消息 --> XML
	public static String messageToXML(NewsResponse news) {
		xstream.alias("xml", news.getClass());
		xstream.alias("item", Article.class);
		return xstream.toXML(news);
	}
}
