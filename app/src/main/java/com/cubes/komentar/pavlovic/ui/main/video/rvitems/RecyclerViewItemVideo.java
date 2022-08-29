package com.cubes.komentar.pavlovic.ui.main.video.rvitems;

import com.cubes.komentar.pavlovic.ui.main.video.VideoAdapter;

public interface RecyclerViewItemVideo {

    int getType();

    void bind(VideoAdapter.ViewHolder holder);

}
