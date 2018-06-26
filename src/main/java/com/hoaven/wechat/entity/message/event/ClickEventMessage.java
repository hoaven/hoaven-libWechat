package com.hoaven.wechat.entity.message.event;

import com.hoaven.wechat.entity.message.AcceptableXmlMessage;
import com.hoaven.wechat.entity.message.EventMessage;
import lombok.Getter;
import lombok.Setter;

/**
 * Created hehuanchun on 2016/10/20.
 */
@Getter
@Setter
public class ClickEventMessage extends EventMessage implements AcceptableXmlMessage {
    public ClickEventMessage() {
        this.setEvent(Event.CLICK);
    }

    private String eventKey;
}
