package com.cubes.komentar.pavlovic.data.source.response;

public class RequestComment extends ParentResponse {

    public RequestBody data;


    public static class RequestBody {

        private String news;
        private String reply_id;
        private String name;
        private String email;
        private String content;

        public RequestBody(String news, String reply_id, String name, String email, String content) {
            this.news = news;
            this.reply_id = reply_id;
            this.name = name;
            this.email = email;
            this.content = content;
        }

        public RequestBody(String news, String name, String email, String content) {
            this.name = name;
            this.email = email;
            this.content = content;
            this.news = news;
        }

    }

}
