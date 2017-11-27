package com.lily.message.event;
/*
 * 扫描二维码事件
 * 用户未关注时，进行关注后的事件推送
 * Event	事件类型，subscribe
 * 用户已关注时的事件推送
 * Event	事件类型，SCAN
 */
public class QRCodeEventMessage extends BaseEventMessage {
	// 事件Key值
	private String EventKey;
	
	private String Ticket;

	public String getEventKey() {
		return EventKey;
	}

	public void setEventKey(String eventKey) {
		EventKey = eventKey;
	}

	public String getTicket() {
		return Ticket;
	}

	public void setTicket(String ticket) {
		Ticket = ticket;
	}
}
