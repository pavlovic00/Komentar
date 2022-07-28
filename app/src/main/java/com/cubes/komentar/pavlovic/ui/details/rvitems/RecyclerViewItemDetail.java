package com.cubes.komentar.pavlovic.ui.details.rvitems;

import com.cubes.komentar.pavlovic.ui.details.DetailNewsAdapter;

public interface RecyclerViewItemDetail {

    int getType();
    void bind(DetailNewsAdapter.DetailNewsViewHolder holder);

}
