package com.cubes.komentar.pavlovic.ui.main.search.rvitems;

import com.cubes.komentar.pavlovic.ui.main.search.SearchAdapter;

public interface RecyclerViewItemSearch {

    int getType();

    void bind(SearchAdapter.ViewHolder holder);

}
