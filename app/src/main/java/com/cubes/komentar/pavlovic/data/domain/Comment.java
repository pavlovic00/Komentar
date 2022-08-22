package com.cubes.komentar.pavlovic.data.domain;

import java.util.ArrayList;

public class Comment {

    public int negative_votes;
    public int positive_votes;
    public String created_at;
    public String news;
    public String name;
    public String parent_comment;
    public String id;
    public String content;
    public ArrayList<Comment> children;

    public Vote vote;
}
