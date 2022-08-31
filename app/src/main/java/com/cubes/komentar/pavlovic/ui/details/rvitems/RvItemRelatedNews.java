package com.cubes.komentar.pavlovic.ui.details.rvitems;

import android.graphics.Color;

import com.cubes.komentar.R;
import com.cubes.komentar.databinding.RvItemSmallBinding;
import com.cubes.komentar.pavlovic.data.domain.News;
import com.cubes.komentar.pavlovic.ui.details.DetailsAdapter;
import com.cubes.komentar.pavlovic.ui.tools.MyMethodsClass;
import com.cubes.komentar.pavlovic.ui.tools.listener.DetailsListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RvItemRelatedNews implements RecyclerViewItemDetail {

    private final News news;
    private final DetailsListener newsListener;
    private final int[] newsListId;


    public RvItemRelatedNews(News news, DetailsListener newsListener, ArrayList<News> newsList) {
        this.news = news;
        this.newsListener = newsListener;
        this.newsListId = MyMethodsClass.initNewsIdList(newsList);
    }

    @Override
    public int getType() {
        return R.layout.rv_item_small;
    }

    @Override
    public void bind(DetailsAdapter.ViewHolder holder) {

        RvItemSmallBinding binding = (RvItemSmallBinding) holder.binding;

        binding.textViewTitle.setText(news.title);
        binding.date.setText(news.createdAt);
        binding.textViewCategory.setText(news.category.name);
        binding.textViewCategory.setTextColor(Color.parseColor(news.category.color));

        Picasso.get().load(news.image).into(binding.imageView);

        holder.itemView.setOnClickListener(view -> newsListener.onNewsClickedVP(news.id, newsListId));
    }
}
