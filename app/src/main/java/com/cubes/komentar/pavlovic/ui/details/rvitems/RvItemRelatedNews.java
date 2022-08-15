package com.cubes.komentar.pavlovic.ui.details.rvitems;

import android.graphics.Color;

import com.cubes.komentar.databinding.RvItemSmallBinding;
import com.cubes.komentar.pavlovic.data.model.News;
import com.cubes.komentar.pavlovic.ui.details.DetailNewsAdapter;
import com.cubes.komentar.pavlovic.ui.tools.NewsDetailListener;
import com.squareup.picasso.Picasso;

public class RvItemRelatedNews implements RecyclerViewItemDetail {

    private final News news;
    private final NewsDetailListener newsListener;


    public RvItemRelatedNews(News news, NewsDetailListener newsListener) {
        this.news = news;
        this.newsListener = newsListener;
    }

    @Override
    public int getType() {
        return 8;
    }

    @Override
    public void bind(DetailNewsAdapter.DetailNewsViewHolder holder) {

        RvItemSmallBinding binding = (RvItemSmallBinding) holder.binding;

        binding.textViewTitle.setText(news.title);
        binding.date.setText(news.created_at);
        binding.textViewCategory.setText(news.category.name);
        binding.textViewCategory.setTextColor(Color.parseColor(news.category.color));

        Picasso.get().load(news.image).into(binding.imageView);

        holder.itemView.setOnClickListener(view -> newsListener.onNewsCLicked(news));
    }
}
