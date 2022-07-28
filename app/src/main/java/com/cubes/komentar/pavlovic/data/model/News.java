package com.cubes.komentar.pavlovic.data.model;

import java.io.Serializable;

public class News implements Serializable {

    public int id;
    public String image;
    public String  image_source;
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

    public News(int id, String image, String image_source, String author_name,
                String source, Category category, String title, String description,
                int comment_enabled, int comments_count, int shares_count, String created_at,
                String url, String click_type) {
        this.id = id;
        this.image = image;
        this.image_source = image_source;
        this.author_name = author_name;
        this.source = source;
        this.category = category;
        this.title = title;
        this.description = description;
        this.comment_enabled = comment_enabled;
        this.comments_count = comments_count;
        this.shares_count = shares_count;
        this.created_at = created_at;
        this.url = url;
        this.click_type = click_type;
    }
}
