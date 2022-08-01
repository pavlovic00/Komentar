package com.cubes.komentar.pavlovic.data.response;

import java.io.Serializable;
import java.util.ArrayList;

public class ResponseComment extends ParentResponse{


    public ArrayList<ResponseCommentData> data;

    public static class ResponseCommentData implements Serializable {

        public boolean open;
        public int negative_votes;
        public int positive_votes;
        public String created_at;
        public String news;
        public String name;
        public String parent_comment;
        public String id;
        public String content;
        public ArrayList<ResponseCommentData> children;

        public ResponseCommentData(int negative_votes, int positive_votes, String created_at, String news, String name, String parent_comment, String id, String content, ArrayList<ResponseCommentData> children) {
            this.open = true;
            this.negative_votes = negative_votes;
            this.positive_votes = positive_votes;
            this.created_at = created_at;
            this.news = news;
            this.name = name;
            this.parent_comment = parent_comment;
            this.id = id;
            this.content = content;
            this.children = children;
        }
    }

    public static class ResponseCommentChildren {

        public int negative_votes;
        public int positive_votes;
        public String created_at;
        public String news;
        public String name;
        public String parent_comment;
        public String id;
        public String content;
        public ArrayList<ResponseCommentChildren> children;

    }
}
