package com.hoaven.wechat.entity.message;

import com.alibaba.fastjson.JSONObject;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created hehuanchun on 2016/10/20.
 */
@Getter
@Setter
public class NewsMessage extends WeChatMessage implements ReturnableXmlMessage, SendableApiMessage {

    public NewsMessage() {
        this.setMsgType(MsgType.news);
    }

    private News news = new News();

    public String toReturnXML() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String toReturnJSON() {
        JSONObject jo = new JSONObject();
        jo.put("touser", this.getOpenId());
        jo.put("msgtype", "news");
        jo.put("news", news);
        return jo.toString();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class News {

        private List<Article> articles = new ArrayList<Article>();

        public void addArticle(final Article article) {
            articles.add(article);
        }

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Article {

            private String title;
            private String description;
            private String url;
            private String picurl;

        }
    }
}
