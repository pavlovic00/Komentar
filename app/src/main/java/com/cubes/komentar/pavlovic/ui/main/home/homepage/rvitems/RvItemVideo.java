package com.cubes.komentar.pavlovic.ui.main.home.homepage.rvitems;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cubes.komentar.databinding.RvRecyclerviewVideoBinding;
import com.cubes.komentar.pavlovic.data.model.News;
import com.cubes.komentar.pavlovic.ui.main.home.homepage.HomepageAdapter;
import com.cubes.komentar.pavlovic.ui.main.video.VideoAdapter;
import com.cubes.komentar.pavlovic.ui.tools.VideoListener;

import java.util.ArrayList;

public class RvItemVideo implements RecyclerViewItemHomepage {

    private final ArrayList<News> videoList;
    private final VideoListener videoListener;


    public RvItemVideo(ArrayList<News> videoList, VideoListener videoListener) {
        this.videoList = videoList;
        this.videoListener = videoListener;
    }

    @Override
    public int getType() {
        return 6;
    }

    @Override
    public void bind(HomepageAdapter.HomepageViewHolder holder) {

        RvRecyclerviewVideoBinding binding = (RvRecyclerviewVideoBinding) holder.binding;

        binding.recyclerViewVideo.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext(),
                RecyclerView.VERTICAL, false));
        VideoAdapter adapter = new VideoAdapter(videoList, videoListener);
        binding.recyclerViewVideo.setAdapter(adapter);
    }
}
