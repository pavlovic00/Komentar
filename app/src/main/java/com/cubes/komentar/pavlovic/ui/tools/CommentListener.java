package com.cubes.komentar.pavlovic.ui.tools;

import com.cubes.komentar.databinding.RvItemCommentBinding;
import com.cubes.komentar.pavlovic.data.source.response.ResponseComment;

public interface CommentListener {

    void onCommentClicked(ResponseComment.Comment comment);

    void like(ResponseComment.Comment comment, RvItemCommentBinding bindingComment);

    void dislike(ResponseComment.Comment comment, RvItemCommentBinding bindingComment);

}
