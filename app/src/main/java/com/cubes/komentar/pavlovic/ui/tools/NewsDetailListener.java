package com.cubes.komentar.pavlovic.ui.tools;

import com.cubes.komentar.pavlovic.data.model.CommentApi;
import com.cubes.komentar.pavlovic.data.model.NewsApi;
import com.cubes.komentar.pavlovic.data.model.NewsDetailApi;
import com.cubes.komentar.pavlovic.data.model.TagsApi;

public interface NewsDetailListener {

    void onNewsCLicked(NewsApi news);

    void onTagClicked(TagsApi tags);

    void onPutCommentClicked(NewsDetailApi data);

    void onAllCommentClicked(NewsDetailApi data);

    void onCommentClicked(CommentApi comment);

    void like(CommentApi comment);

    void dislike(CommentApi comment);

}
