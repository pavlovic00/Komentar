package com.cubes.komentar.pavlovic.ui.main.home.homepage.rvitems;

import android.graphics.Color;

import com.cubes.komentar.databinding.RvItemSmallBinding;
import com.cubes.komentar.pavlovic.data.model.News;
import com.cubes.komentar.pavlovic.ui.main.home.homepage.HomepageAdapter;
import com.cubes.komentar.pavlovic.ui.tools.NewsListener;
import com.squareup.picasso.Picasso;

public class RvItemTopNews implements RecyclerViewItemHomepage {

    private final News news;
    private final NewsListener newsListener;


    public RvItemTopNews(News news, NewsListener newsListener) {
        this.news = news;
        this.newsListener = newsListener;
    }

    @Override
    public int getType() {
        return 1;
    }

    @Override
    public void bind(HomepageAdapter.HomepageViewHolder holder) {

        RvItemSmallBinding binding = (RvItemSmallBinding) holder.binding;

        binding.textViewTitle.setText(news.title);
        binding.date.setText(news.created_at);
        binding.textViewCategory.setText(news.category.name);
        binding.textViewCategory.setTextColor(Color.parseColor(news.category.color));

        Picasso.get().load(news.image).into(binding.imageView);

        holder.itemView.setOnClickListener(view -> newsListener.onNewsClicked(news));
    }
}
