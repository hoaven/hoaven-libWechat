package com.hoaven.wechat.entity.message;

/**
 * Created hehuanchun on 2016/10/20.
 * 可通过Api发送消息
 */
public interface SendableApiMessage {
    /**
     * active response type
     * @return
     */
    String toReturnJSON();
}
