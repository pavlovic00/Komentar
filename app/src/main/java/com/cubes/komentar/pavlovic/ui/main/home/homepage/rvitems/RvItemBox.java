package com.cubes.komentar.pavlovic.ui.main.home.homepage.rvitems;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cubes.komentar.databinding.RvItemRvBinding;
import com.cubes.komentar.pavlovic.data.model.News;
import com.cubes.komentar.pavlovic.ui.main.home.homepage.HomepageAdapter;
import com.cubes.komentar.pavlovic.ui.main.home.homepage.NewsForHomepageAdapter;
import com.cubes.komentar.pavlovic.ui.tools.NewsListener;

import java.util.ArrayList;

public class RvItemBox implements RecyclerViewItemHomepage {

    private final String title;
    private final ArrayList<News> news;
    private final NewsListener newsListener;


    public RvItemBox(String title, ArrayList<News> news, NewsListener newsListener) {
        this.title = title;
        this.news = news;
        this.newsListener = newsListener;
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
        binding.recyclerView.setAdapter(new NewsForHomepageAdapter(news, newsListener));

    }
}
