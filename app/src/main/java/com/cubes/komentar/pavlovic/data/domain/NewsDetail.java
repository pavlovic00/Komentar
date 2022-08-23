package com.cubes.komentar.pavlovic.data.domain;

import java.util.ArrayList;

public class NewsDetail {

    public int id;
    public String image;
    public String source;
    public Category category;
    public String title;
    public int commentsCount;
    public boolean commentEnabled;
    public String createdAt;
    public String url;

    public ArrayList<Tags> tags;
    public ArrayList<News> relatedNews;
    public ArrayList<Comment> topComments;
}
