package com.cubes.komentar.pavlovic.ui.tools.listener;

import com.cubes.komentar.pavlovic.data.domain.Comment;
import com.cubes.komentar.pavlovic.data.domain.NewsDetail;
import com.cubes.komentar.pavlovic.data.domain.Tags;

public interface NewsDetailListener {

    void onNewsClickedVP(int newsId, String newsUrl, int[] newsIdList);

    void onTagClicked(Tags tags);

    void onPutCommentClicked(NewsDetail data);

    void onAllCommentClicked(NewsDetail data);

    void onCommentClicked(Comment comment);

    void like(Comment comment);

    void dislike(Comment comment);

}
