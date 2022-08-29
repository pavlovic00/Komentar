package com.cubes.komentar.pavlovic.ui.main.search.rvitems;

import com.cubes.komentar.R;
import com.cubes.komentar.pavlovic.ui.main.search.SearchAdapter;
import com.cubes.komentar.pavlovic.ui.tools.listener.LoadingNewsListener;

public class RvItemLoadingSearch implements RecyclerViewItemSearch {

    private final LoadingNewsListener listener;


    public RvItemLoadingSearch(LoadingNewsListener listener) {
        this.listener = listener;
    }

    @Override
    public int getType() {
        return R.layout.rv_item_loading;
    }

    @Override
    public void bind(SearchAdapter.ViewHolder holder) {

        listener.loadMoreNews();
    }
}
