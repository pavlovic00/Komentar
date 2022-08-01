package com.cubes.komentar.pavlovic.ui.main.home.homepage.rvitems;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cubes.komentar.databinding.RvRecyclerviewVideoBinding;
import com.cubes.komentar.pavlovic.data.model.News;
import com.cubes.komentar.pavlovic.ui.main.home.homepage.HomepageAdapter;
import com.cubes.komentar.pavlovic.ui.main.video.VideoAdapter;

import java.util.ArrayList;

public class RvItemVideo implements RecyclerViewItemHomepage {

    public ArrayList<News> videoList;
    public Context context;

    public RvItemVideo(ArrayList<News> videoList, Context context) {
        this.videoList = videoList;
        this.context = context;
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
        binding.recyclerViewVideo.setAdapter(new VideoAdapter(context, videoList));

    }
}
