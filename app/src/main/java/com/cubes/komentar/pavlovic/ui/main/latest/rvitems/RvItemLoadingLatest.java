package com.cubes.komentar.pavlovic.ui.main.latest.rvitems;

import com.cubes.komentar.R;
import com.cubes.komentar.pavlovic.ui.main.latest.LatestAdapter;
import com.cubes.komentar.pavlovic.ui.tools.listener.LoadingNewsListener;

public class RvItemLoadingLatest implements RecyclerViewItemLatest {

    private final LoadingNewsListener listener;


    public RvItemLoadingLatest(LoadingNewsListener listener) {
        this.listener = listener;
    }

    @Override
    public int getType() {
        return R.layout.rv_item_loading;
    }

    @Override
    public void bind(LatestAdapter.ViewHolder holder) {

        listener.loadMoreNews();
    }
}
