package com.cubes.komentar.pavlovic.data.domain;

import java.util.ArrayList;

public class NewsDetail {

    public int id;
    public String image;
    public String source;
    public Category category;
    public String title;
    public int comments_count;
    public String created_at;
    public String url;

    public ArrayList<Tags> tags;
    public ArrayList<News> related_news;
    public ArrayList<Comment> comments_top_n;
}
