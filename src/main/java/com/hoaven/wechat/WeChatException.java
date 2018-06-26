package com.hoaven.wechat;

/**
 * 自定义异常
 * Created hehuanchun on 2016/10/21.
 */
public class WeChatException extends RuntimeException {
    public WeChatException(String message) {
        super(message);
    }

    public WeChatException(Throwable cause) {
        super(cause);
    }

    public WeChatException(String message, Throwable cause) {
        super(message, cause);
    }

}
