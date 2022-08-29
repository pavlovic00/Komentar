package com.cubes.komentar.pavlovic.data.domain;

import java.util.ArrayList;

public class Comment {

    public int likes;
    public int dislikes;
    public String createdAt;
    public String news;
    public String name;
    public String parentComment;
    public String id;
    public String content;
    public ArrayList<Comment> children;

    public Vote vote;
}
