package com.cubes.komentar.pavlovic.data.model;

import java.util.ArrayList;

public class NewsDetailApi {

    public int id;
    public String image;
    public String image_source;
    public String author_name;
    public String source;
    public CategoryApi category;
    public String title;
    public String description;
    public int comment_enabled;
    public int comments_count;
    public int shares_count;
    public String created_at;
    public String url;
    public String click_type;

    public ArrayList<TagsApi> tags;
    public ArrayList<NewsApi> related_news;
    public ArrayList<NewsApi> category_news;
    public ArrayList<CommentApi> comments_top_n;
}
