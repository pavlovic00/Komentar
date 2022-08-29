package com.cubes.komentar.pavlovic.data.domain;

public class Vote {

    public String commentId;
    public boolean vote;


    public Vote(String commentId, boolean vote) {
        this.commentId = commentId;
        this.vote = vote;
    }
}
