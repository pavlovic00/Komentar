package com.cubes.komentar.pavlovic.ui.main.home.homepage.rvitems;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cubes.komentar.databinding.RvItemRvBinding;
import com.cubes.komentar.pavlovic.data.model.News;
import com.cubes.komentar.pavlovic.ui.main.home.homepage.HomepageAdapter;
import com.cubes.komentar.pavlovic.ui.main.home.homepage.NewsForHomepageAdapter;

import java.util.ArrayList;

public class RvItemSportBox implements RecyclerViewItemHomepage {

    private String title;
    private ArrayList<News> news;
    private Context context;

    public RvItemSportBox(String title, ArrayList<News> news, Context context) {
        this.title = title;
        this.news = news;
        this.context = context;
    }

    @Override
    public int getType() {
        return 8;
    }

    @Override
    public void bind(HomepageAdapter.HomepageViewHolder holder) {

        RvItemRvBinding binding = (RvItemRvBinding) holder.binding;
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext(),
                RecyclerView.VERTICAL, false));
        binding.recyclerView.setAdapter(new NewsForHomepageAdapter(context, news));

    }
}
