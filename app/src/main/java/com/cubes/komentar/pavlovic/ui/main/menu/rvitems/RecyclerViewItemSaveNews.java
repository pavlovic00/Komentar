package com.cubes.komentar.pavlovic.ui.main.menu.rvitems;

import com.cubes.komentar.pavlovic.data.domain.SaveNews;
import com.cubes.komentar.pavlovic.ui.main.menu.SaveNewsAdapter;

public interface RecyclerViewItemSaveNews {

    int getType();

    void bind(SaveNewsAdapter.ViewHolder holder);

    SaveNews getNews();
}
