package com.cubes.komentar.pavlovic.ui.details.rvitems;

import com.cubes.komentar.pavlovic.ui.details.DetailsAdapter;

public interface RecyclerViewItemDetail {

    int getType();

    void bind(DetailsAdapter.ViewHolder holder);

    default String getCommentsId() {
        return null;
    }

    default void updateLike() {
    }

    default void updateDislike() {
    }

}
