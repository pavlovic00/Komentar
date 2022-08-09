package com.cubes.komentar.pavlovic.data.source.response;

import com.cubes.komentar.pavlovic.data.model.Category;
import com.cubes.komentar.pavlovic.data.model.News;
import com.cubes.komentar.pavlovic.data.model.Tags;

import java.util.ArrayList;

public class ResponseDetail extends ParentResponse {

    public ResponseDetailData data;


    public static class ResponseDetailData {

        public int id;
        public String image;
        public String image_source;
        public String author_name;
        public String source;
        public Category category;
        public String title;
        public String description;
        public int comment_enabled;
        public int comments_count;
        public int shares_count;
        public String created_at;
        public String url;
        public String click_type;

        public ArrayList<Tags> tags;
        public ArrayList<News> related_news;
        public ArrayList<News> category_news;
        public ArrayList<ResponseComment.Comment> comments_top_n;
    }
}
