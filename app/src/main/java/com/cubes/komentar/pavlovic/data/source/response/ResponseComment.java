package com.cubes.komentar.pavlovic.data.source.response;

import com.cubes.komentar.pavlovic.data.model.Vote;

import java.util.ArrayList;

public class ResponseComment extends ParentResponse{

    public ArrayList<Comment> data;


    public static class Comment {

        public int negative_votes;
        public int positive_votes;
        public String created_at;
        public String news;
        public String name;
        public String parent_comment;
        public String id;
        public String content;
        public ArrayList<Comment> children;

        public int voted = 0;
        public Vote vote;
    }

}
