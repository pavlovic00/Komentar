package com.cubes.komentar.pavlovic.ui.details.rvitems;

import com.cubes.komentar.pavlovic.ui.details.DetailAdapter;

public interface RecyclerViewItemDetail {

    int getType();

    void bind(DetailAdapter.ViewHolder holder);

    default String getCommentsId() {
        return null;
    }

    default void updateLike() {
    }

    default void updateDislike() {
    }

}
