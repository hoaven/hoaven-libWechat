package com.hoaven.wechat.entity.message.event;

import com.hoaven.wechat.entity.message.AcceptableXmlMessage;
import com.hoaven.wechat.entity.message.EventMessage;
import lombok.Getter;
import lombok.Setter;

/**
 * Created hehuanchun on 2016/10/20.
 * 取消关注事件
 */
@Getter
@Setter
public class UnsubscribeEventMessage extends EventMessage implements AcceptableXmlMessage {
    public UnsubscribeEventMessage() {
        this.setEvent(Event.unsubscribe);
    }
}
