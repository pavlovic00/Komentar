package com.cubes.komentar.pavlovic.ui.details.rvitems;

import com.cubes.komentar.pavlovic.ui.details.NewsDetailAdapter;

public interface RecyclerViewItemDetail {

    int getType();

    void bind(NewsDetailAdapter.ViewHolder holder);

}
