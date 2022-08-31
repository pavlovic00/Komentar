package com.cubes.komentar.pavlovic.data.domain;

import java.util.ArrayList;

public class Comment {

    public int likes;
    public int dislikes;
    public String createdAt;
    public String newsId;
    public String name;
    public String parentCommentId;
    public String commentId;
    public String content;
    public ArrayList<Comment> children;

    public Vote vote;
}
