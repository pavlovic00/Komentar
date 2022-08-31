package com.cubes.komentar.pavlovic.ui.main.search.rvitems;

import android.graphics.Color;

import com.cubes.komentar.R;
import com.cubes.komentar.databinding.RvItemSmallBinding;
import com.cubes.komentar.pavlovic.data.domain.News;
import com.cubes.komentar.pavlovic.ui.tools.MyMethodsClass;
import com.cubes.komentar.pavlovic.ui.main.search.SearchAdapter;
import com.cubes.komentar.pavlovic.ui.tools.listener.NewsListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RvItemSmallSearch implements RecyclerViewItemSearch {

    private final ArrayList<News> newsList;
    private final News news;
    private NewsListener newsListener;
    private int[] newsIdList;


    public RvItemSmallSearch(News news, NewsListener newsListener, ArrayList<News> newsList) {
        this.newsList = newsList;
        this.news = news;
        this.newsListener = newsListener;
        this.newsIdList = MyMethodsClass.initNewsIdList(newsList);
    }

    public RvItemSmallSearch(News news, ArrayList<News> list) {
        this.news = news;
        newsList = list;
    }

    @Override
    public int getType() {
        return R.layout.rv_item_small;
    }

    @Override
    public void bind(SearchAdapter.ViewHolder holder) {

        RvItemSmallBinding binding = (RvItemSmallBinding) holder.binding;

        binding.textViewTitle.setText(news.title);
        binding.date.setText(news.createdAt);
        binding.textViewCategory.setText(news.category.name);
        binding.textViewCategory.setTextColor(Color.parseColor(news.category.color));

        Picasso.get().load(news.image).into(binding.imageView);

        holder.itemView.setOnClickListener(view -> newsListener.onNewsClickedVP(news.id, newsIdList));
    }
}
