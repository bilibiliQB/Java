package com.lily.message.event;
/*
 * ----自定义菜单事件----
 * 用户点击自定义菜单后，微信会把点击事件推送给开发者
 * 请注意，点击菜单弹出子菜单，不会产生上报。
 */
public class MenuEventMessage extends BaseEventMessage{
    //事件Key值，与自定义菜单接口中Key值对应
    private String EventKey;

    public String getEventKey() {
        return EventKey;
    }

    public void setEventKey(String eventKey) {
        EventKey = eventKey;
    }
}
