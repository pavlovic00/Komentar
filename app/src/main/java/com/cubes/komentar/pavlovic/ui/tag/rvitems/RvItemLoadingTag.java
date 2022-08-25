package com.cubes.komentar.pavlovic.ui.tag.rvitems;

import com.cubes.komentar.R;
import com.cubes.komentar.pavlovic.ui.tag.TagsAdapter;
import com.cubes.komentar.pavlovic.ui.tools.listener.LoadingNewsListener;

public class RvItemLoadingTag implements RecyclerViewItemTag {

    private final LoadingNewsListener listener;


    public RvItemLoadingTag(LoadingNewsListener listener) {
        this.listener = listener;
    }

    @Override
    public int getType() {
        return R.layout.rv_item_loading;
    }

    @Override
    public void bind(TagsAdapter.ViewHolder holder) {

        listener.loadMoreNews();
    }
}
