package com.hoaven.wechat.entity.message;

import lombok.Getter;
import lombok.Setter;

/**
 * Created hehuanchun on 2016/10/20.
 */
@Getter
@Setter
public class EventMessage extends WeChatMessage implements AcceptableXmlMessage {
    Event event;

    public EventMessage() {
        this.setMsgType(MsgType.event);
    }

    public enum Event {
        subscribe,
        unsubscribe,
        SCAN,
        LOCATION,
        CLICK,
        VIEW,

        kf_create_session,
        kf_close_session,
        kf_switch_session,

        TEMPLATESENDJOBFINISH,
        visitor_scan,
        scancode_push,
        scancode_waitmsg,
        pic_sysphoto,
        pic_photo_or_album,
        pic_weixin,
        location_select,
        enter_agent,
        click,
        batch_job_result;
    }
}
