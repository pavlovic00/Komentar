package com.cubes.komentar.pavlovic.ui.main.latest.rvitems;

import com.cubes.komentar.pavlovic.ui.main.latest.LatestAdapter;

public interface RecyclerViewItemLatest {

    int getType();

    void bind(LatestAdapter.ViewHolder holder);

}
