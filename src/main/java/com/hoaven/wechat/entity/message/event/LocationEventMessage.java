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
public class LocationEventMessage extends EventMessage implements AcceptableXmlMessage {
    public LocationEventMessage() {
        this.setEvent(Event.LOCATION);
    }

    private String latitude;
    private String longitude;
    private String precision;
}
