package com.cubes.komentar.pavlovic.ui.main.video.rvitems;

import com.cubes.komentar.R;
import com.cubes.komentar.pavlovic.ui.main.video.VideoAdapter;
import com.cubes.komentar.pavlovic.ui.tools.listener.LoadingNewsListener;

public class RvItemLoadingVideo implements RecyclerViewItemVideo {

    private final LoadingNewsListener listener;


    public RvItemLoadingVideo(LoadingNewsListener listener) {
        this.listener = listener;
    }

    @Override
    public int getType() {
        return R.layout.rv_item_loading;
    }

    @Override
    public void bind(VideoAdapter.ViewHolder holder) {

        listener.loadMoreNews();
    }
}
