package com.cubes.komentar.pavlovic.data.response.responsecomment;

import java.util.ArrayList;

public class ResponseCommentChildren {

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
