package com.hoaven.wechat.entity;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

/**
 * Created hehuanchun on 2016/10/21.
 */
@Setter
@Getter
public class WeChatEntity {
    //返回码,0或null为正确返回
    private String errcode;
    //返回的msg(json)
    private String errmsg;

    public String toErrorString(){
        return JSON.toJSONString(this);
    }
    public boolean isSuccess(){
        return errcode == null || "0".equals(errcode);
    }
}
