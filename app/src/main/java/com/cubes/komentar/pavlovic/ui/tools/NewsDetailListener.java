package com.cubes.komentar.pavlovic.ui.tools;

import com.cubes.komentar.pavlovic.data.model.News;
import com.cubes.komentar.pavlovic.data.model.Tags;
import com.cubes.komentar.pavlovic.data.source.response.ResponseDetail;

public interface NewsDetailListener {

    void onNewsCLicked(News news);

    void onTagClicked(Tags tags);

    void onPutCommentClicked(ResponseDetail.ResponseDetailData data);

    void onAllCommentClicked(ResponseDetail.ResponseDetailData data);

}
