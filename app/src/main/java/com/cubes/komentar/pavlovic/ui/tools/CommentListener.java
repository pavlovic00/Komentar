package com.cubes.komentar.pavlovic.ui.tools;

import com.cubes.komentar.pavlovic.data.domain.Comment;

public interface CommentListener {

    void onCommentClicked(Comment comment);

    void like(Comment comment);

    void dislike(Comment comment);

}
