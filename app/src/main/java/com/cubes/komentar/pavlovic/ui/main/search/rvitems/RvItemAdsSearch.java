package com.cubes.komentar.pavlovic.ui.main.search.rvitems;

import com.cubes.komentar.R;
import com.cubes.komentar.pavlovic.ui.main.search.SearchAdapter;

public class RvItemAdsSearch implements RecyclerViewItemSearch {


    public RvItemAdsSearch() {
    }

    @Override
    public int getType() {
        return R.layout.rv_item_ads_view;
    }

    @Override
    public void bind(SearchAdapter.ViewHolder holder) {

    }
}
