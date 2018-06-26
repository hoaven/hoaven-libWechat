package com.hoaven.wechat.entity.message;

import lombok.Getter;
import lombok.Setter;

/**
 * Created hehuanchun on 2016/10/20.
 * 链接消息
 */
@Getter
@Setter
public class LinkMessage extends WeChatMessage implements AcceptableXmlMessage {
    public LinkMessage() {
        this.setMsgType(MsgType.link);
    }

    //消息id，64位整型
    private String msgId;
    //消息标题
    private String title;
    //消息描述
    private String description;
    //消息链接
    private String url;
}
