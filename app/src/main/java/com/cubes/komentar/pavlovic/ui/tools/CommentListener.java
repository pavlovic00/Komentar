package com.cubes.komentar.pavlovic.ui.tools;

import com.cubes.komentar.pavlovic.data.source.response.ResponseComment;

public interface CommentListener {

    void onNewsCLicked(ResponseComment.Comment comment);

    void like(String commentId);

    void dislike(String commentId);

}