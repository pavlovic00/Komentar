package com.cubes.komentar.pavlovic.ui.tools;

import com.cubes.komentar.pavlovic.data.model.CommentApi;

public interface CommentListener {

    void onCommentClicked(CommentApi comment);

    void like(CommentApi comment);

    void dislike(CommentApi comment);

}
