package com.cubes.komentar.pavlovic.ui.main.latest.rvitems;

import android.graphics.Color;

import com.cubes.komentar.R;
import com.cubes.komentar.databinding.RvItemBigBinding;
import com.cubes.komentar.pavlovic.data.domain.News;
import com.cubes.komentar.pavlovic.ui.main.latest.LatestAdapter;
import com.cubes.komentar.pavlovic.ui.tools.listener.NewsListener;
import com.squareup.picasso.Picasso;

public class RvItemBigLatest implements RecyclerViewItemLatest {

    private final News news;
    private final NewsListener newsListener;


    public RvItemBigLatest(News news, NewsListener newsListener) {
        this.news = news;
        this.newsListener = newsListener;
    }

    @Override
    public int getType() {
        return R.layout.rv_item_big;
    }

    @Override
    public void bind(LatestAdapter.ViewHolder holder) {

        RvItemBigBinding binding = (RvItemBigBinding) holder.binding;

        binding.textViewTitle.setText(news.title);
        binding.date.setText(news.createdAt);
        binding.textViewCategory.setText(news.category.name);
        binding.textViewCategory.setTextColor(Color.parseColor(news.category.color));

        Picasso.get().load(news.image).into(binding.imageView);

        holder.itemView.setOnClickListener(view -> newsListener.onNewsClicked(news));
    }
}
