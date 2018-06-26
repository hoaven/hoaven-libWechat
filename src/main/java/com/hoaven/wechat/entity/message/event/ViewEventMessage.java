package com.hoaven.wechat.entity.message.event;

import com.hoaven.wechat.entity.message.AcceptableXmlMessage;
import com.hoaven.wechat.entity.message.EventMessage;
import lombok.Getter;
import lombok.Setter;

/**
 * Created hehuanchun on 2016/10/20.
 * 点击菜单跳转链接时的事件推送
 */
@Getter
@Setter
public class ViewEventMessage extends EventMessage implements AcceptableXmlMessage {
    public ViewEventMessage() {
        this.setEvent(Event.VIEW);
    }

    //事件KEY值，设置的跳转URL
    private String eventKey;
}
