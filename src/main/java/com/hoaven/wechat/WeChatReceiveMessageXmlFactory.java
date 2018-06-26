package com.hoaven.wechat;


import com.hoaven.wechat.entity.message.*;
import com.hoaven.wechat.entity.message.event.*;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

public class WeChatReceiveMessageXmlFactory {
    private WeChatReceiveMessageXmlFactory() {
    }

    /**
     * 将接收到的xml转为对象
     * @param xml
     * @return
     */
    public static AcceptableXmlMessage create(final String xml) {
        Map<String, String> resultMap = convertXmlToMap(xml);
        String openId = resultMap.get("FromUserName");
        String weixinId = resultMap.get("ToUserName");
        Long createTime = Long.valueOf(resultMap.get("CreateTime"));
        WeChatMessage.MsgType msgType = WeChatMessage.MsgType.valueOf(resultMap.get("MsgType"));
        AcceptableXmlMessage message = null;
        switch (msgType) {
            case event:
                String event = resultMap.get("Event");
                switch (EventMessage.Event.valueOf(event)) {
                    case subscribe:
                        SubscribeEventMessage subscribeEventMessage = new SubscribeEventMessage();
                        subscribeEventMessage.setCreateTime(createTime);
                        subscribeEventMessage.setEvent(EventMessage.Event.valueOf(event));
                        subscribeEventMessage.setOpenId(openId);
                        subscribeEventMessage.setWeixinId(weixinId);
                        subscribeEventMessage.setEventKey(resultMap.get("EventKey"));
                        subscribeEventMessage.setTicket(resultMap.get("Ticket"));
                        message = subscribeEventMessage;
                        break;
                    case unsubscribe:
                        UnsubscribeEventMessage unsubscribeEventMessage = new UnsubscribeEventMessage();
                        unsubscribeEventMessage.setCreateTime(createTime);
                        unsubscribeEventMessage.setEvent(EventMessage.Event.valueOf(event));
                        unsubscribeEventMessage.setOpenId(openId);
                        unsubscribeEventMessage.setWeixinId(weixinId);
                        message = unsubscribeEventMessage;
                        break;
                    case SCAN:
                        ScanEventMessage scanEventMessage = new ScanEventMessage();
                        scanEventMessage.setCreateTime(createTime);
                        scanEventMessage.setEvent(EventMessage.Event.valueOf(event));
                        scanEventMessage.setOpenId(openId);
                        scanEventMessage.setWeixinId(weixinId);
                        scanEventMessage.setEventKey(resultMap.get("EventKey"));
                        scanEventMessage.setTicket(resultMap.get("Ticket"));
                        message = scanEventMessage;
                        break;
                    case CLICK:
                        ClickEventMessage clickEventMessage = new ClickEventMessage();
                        clickEventMessage.setCreateTime(createTime);
                        clickEventMessage.setEvent(EventMessage.Event.valueOf(event));
                        clickEventMessage.setEventKey(resultMap.get("EventKey"));
                        clickEventMessage.setOpenId(openId);
                        clickEventMessage.setWeixinId(weixinId);
                        message = clickEventMessage;
                        break;
                    case VIEW:
                        ViewEventMessage viewEventMessage = new ViewEventMessage();
                        viewEventMessage.setCreateTime(createTime);
                        viewEventMessage.setEvent(EventMessage.Event.valueOf(event));
                        viewEventMessage.setOpenId(openId);
                        viewEventMessage.setWeixinId(weixinId);
                        viewEventMessage.setEventKey(resultMap.get("EventKey"));
                        message = viewEventMessage;
                        break;
                    case LOCATION:
                        LocationEventMessage locationEventMessage = new LocationEventMessage();
                        locationEventMessage.setCreateTime(createTime);
                        locationEventMessage.setEvent(EventMessage.Event.valueOf(event));
                        locationEventMessage.setOpenId(openId);
                        locationEventMessage.setWeixinId(weixinId);
                        locationEventMessage.setLatitude(resultMap.get("Latitude"));
                        locationEventMessage.setLongitude(resultMap.get("Longitude"));
                        locationEventMessage.setPrecision(resultMap.get("Precision"));
                        message = locationEventMessage;
                        break;
                    case kf_create_session:
                    case kf_close_session:
                    case kf_switch_session:
                        KfSessionEventMessage kfSessionEventMessage = new KfSessionEventMessage();
                        kfSessionEventMessage.setCreateTime(createTime);
                        kfSessionEventMessage.setEvent(EventMessage.Event.valueOf(event));
                        kfSessionEventMessage.setOpenId(openId);
                        kfSessionEventMessage.setWeixinId(weixinId);
                        kfSessionEventMessage.setKfAcount(resultMap.get("KfAccount"));
                        kfSessionEventMessage.setFromKfAccount(resultMap.get("FromKfAccount"));
                        kfSessionEventMessage.setToKfAccount(resultMap.get("ToKfAccount"));
                        message = kfSessionEventMessage;
                        break;
                    default:
                        EventMessage eventMessage = new EventMessage();
                        eventMessage.setCreateTime(createTime);
                        eventMessage.setEvent(EventMessage.Event.valueOf(event));
                        eventMessage.setOpenId(openId);
                        eventMessage.setWeixinId(weixinId);
                        message = eventMessage;
                }
                break;
            case text:
                String content = resultMap.get("Content");
                String msgId = resultMap.get("MsgId");
                TextMessage textMessage = new TextMessage();
                textMessage.setCreateTime(createTime);
                textMessage.setContent(content);
                textMessage.setMsgId(msgId);
                textMessage.setOpenId(openId);
                textMessage.setWeixinId(weixinId);
                message = textMessage;
                break;
            case image:
                ImageMessage imageMessage = new ImageMessage();
                imageMessage.setOpenId(openId);
                imageMessage.setWeixinId(weixinId);
                imageMessage.setCreateTime(createTime);
                imageMessage.setMsgId(resultMap.get("MsgId"));
                imageMessage.setMediaId(resultMap.get("MediaId"));
                imageMessage.setPicUrl(resultMap.get("PicUrl"));
                message = imageMessage;
                break;
        }
        return message;
    }

    /**
     * 将接收到的xml转为Map
     * @param xml
     * @return
     */
    public static Map<String, String> convertXmlToMap(final String xml) {
        Map<String, String> resultMap = new HashMap<String, String>();
        Document doc = null;
        SAXReader reader = new SAXReader();
        try {
            doc = reader.read(new StringReader(xml));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        for (Object item : doc.getRootElement().elements()) {
            Element element = (Element) item;
            resultMap.put(element.getName(), element.getText());
        }
        return resultMap;
    }

}
