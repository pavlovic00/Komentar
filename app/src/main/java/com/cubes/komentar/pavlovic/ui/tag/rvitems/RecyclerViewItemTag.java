package com.cubes.komentar.pavlovic.ui.tag.rvitems;

import com.cubes.komentar.pavlovic.ui.tag.TagsAdapter;

public interface RecyclerViewItemTag {

    int getType();

    void bind(TagsAdapter.ViewHolder holder);

}
