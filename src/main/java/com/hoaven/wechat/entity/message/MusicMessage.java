package com.hoaven.wechat.entity.message;

import lombok.Getter;
import lombok.Setter;

/**
 * Created hehuanchun on 2016/10/20.
 */
@Getter
@Setter
public class MusicMessage extends WeChatMessage implements ReturnableXmlMessage, SendableApiMessage {

    public MusicMessage() {
        this.setMsgType(MsgType.music);
    }

    private Music music = new Music();

    public String toReturnXML() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String toReturnJSON() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Getter
    @Setter
    public static class Music {
        private String title;
        private String description;
        private String musicurl;
        private String hqmusicurl;
        private String thumb_media_id;
    }
}
