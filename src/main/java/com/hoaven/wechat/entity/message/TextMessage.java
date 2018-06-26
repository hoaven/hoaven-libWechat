package com.hoaven.wechat.entity.message;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

/**
 * Created hehuanchun on 2016/10/20.
 * 文本消息
 */
@Getter
@Setter
public class TextMessage extends WeChatMessage implements ReturnableXmlMessage, SendableApiMessage, AcceptableXmlMessage {
    public TextMessage() {
        this.setMsgType(MsgType.text);
    }

    //消息id，64位整型
    private String msgId;
    //文本消息内容
    private String content;

    public String toReturnXML() {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        sb.append("<ToUserName><![CDATA[" + this.getOpenId() + "]]></ToUserName>");
        sb.append("<FromUserName><![CDATA[" + this.getWeixinId() + "]]></FromUserName>");
        sb.append("<CreateTime>" + this.getCreateTime() + "</CreateTime>");
        sb.append("<MsgType><![CDATA[text]]></MsgType>");
        sb.append("<Content><![CDATA[" + this.getContent() + "]]></Content>");
        sb.append("</xml>");
        return sb.toString();
    }

    public String toReturnJSON() {
        JSONObject jo = new JSONObject();
        jo.put("touser", this.getOpenId());
        jo.put("msgtype", this.getMsgType().name());
        JSONObject contentJo = new JSONObject();
        contentJo.put("content", content);
        jo.put(this.getMsgType().name(), contentJo);
        return jo.toString();
    }

}
