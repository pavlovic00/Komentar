package com.cubes.komentar.pavlovic.ui.comments.rvitems;

import com.cubes.komentar.pavlovic.ui.comments.CommentAdapter;

public interface RecyclerViewItemComment {

    int getType();

    void bind(CommentAdapter.ViewHolder holder);

    String getCommentsId();

    void updateLike();

    void updateDislike();

}
