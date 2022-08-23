package com.cubes.komentar.pavlovic.ui.main.home.homepage.rvitems;

import android.graphics.Color;

import com.cubes.komentar.R;
import com.cubes.komentar.databinding.RvItemSmallBinding;
import com.cubes.komentar.pavlovic.data.domain.News;
import com.cubes.komentar.pavlovic.ui.main.home.homepage.HomepageAdapter;
import com.cubes.komentar.pavlovic.ui.tools.NewsListener;
import com.squareup.picasso.Picasso;

public class RvItemSmall implements RecyclerViewItemHomepage {

    private final News news;
    private final NewsListener newsListener;


    public RvItemSmall(News news, NewsListener newsListener) {
        this.news = news;
        this.newsListener = newsListener;
    }

    @Override
    public int getType() {
        return R.layout.rv_item_small;
    }

    @Override
    public void bind(HomepageAdapter.ViewHolder holder) {

        RvItemSmallBinding binding = (RvItemSmallBinding) holder.binding;

        binding.textViewTitle.setText(news.title);
        binding.date.setText(news.createdAt);
        binding.textViewCategory.setText(news.category.name);
        binding.textViewCategory.setTextColor(Color.parseColor(news.category.color));

        Picasso.get().load(news.image).into(binding.imageView);

        holder.itemView.setOnClickListener(view -> newsListener.onNewsClicked(news));
    }
}
