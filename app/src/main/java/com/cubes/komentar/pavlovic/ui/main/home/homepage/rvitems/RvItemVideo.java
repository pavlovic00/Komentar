package com.cubes.komentar.pavlovic.ui.main.home.homepage.rvitems;

import android.content.Intent;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cubes.komentar.databinding.RvRecyclerviewVideoBinding;
import com.cubes.komentar.pavlovic.data.model.News;
import com.cubes.komentar.pavlovic.ui.main.home.homepage.HomepageAdapter;
import com.cubes.komentar.pavlovic.ui.main.video.VideoAdapter;

import java.util.ArrayList;

public class RvItemVideo implements RecyclerViewItemHomepage {

    private final ArrayList<News> videoList;


    public RvItemVideo(ArrayList<News> videoList) {
        this.videoList = videoList;
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
        VideoAdapter adapter;
        binding.recyclerViewVideo.setAdapter(adapter = new VideoAdapter(videoList));

        adapter.setNewsListener(news -> {
            Intent i = new Intent();
            i.setAction(Intent.ACTION_SEND);
            i.putExtra(Intent.EXTRA_TEXT, news.url);
            i.setType("text/plain");
            Intent shareIntent = Intent.createChooser(i, null);
            holder.itemView.getContext().startActivity(shareIntent);
        });

    }
}
