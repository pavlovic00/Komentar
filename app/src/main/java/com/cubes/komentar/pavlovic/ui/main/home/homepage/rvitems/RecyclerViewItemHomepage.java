package com.cubes.komentar.pavlovic.ui.main.home.homepage.rvitems;

import com.cubes.komentar.pavlovic.ui.main.home.homepage.HomepageAdapter;

public interface RecyclerViewItemHomepage {

    int getType();

    void bind(HomepageAdapter.HomepageViewHolder holder);

}
