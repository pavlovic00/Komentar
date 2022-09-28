package com.cubes.komentar.pavlovic.ui.tools.listener;

import com.cubes.komentar.pavlovic.data.domain.Comment;
import com.cubes.komentar.pavlovic.data.domain.NewsDetail;
import com.cubes.komentar.pavlovic.data.domain.Tags;

public interface DetailsListener {

    void onNewsClickedVP(int newsId, int[] newsIdList);

    void onSaveClicked(int id, String title);

    void onTagClicked(Tags tags);

    void onPutCommentClicked(NewsDetail data);

    void onAllCommentClicked(NewsDetail data);

    void onCommentClicked(Comment comment);

    void like(Comment comment);

    void dislike(Comment comment);

    default boolean isSaved(int id) {
        return false;
    }

    default void onCommentNewsClicked(int id) {

    }

    default void onShareNewsClicked(String url) {

    }

}
