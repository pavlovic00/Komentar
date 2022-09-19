package com.cubes.komentar.pavlovic.ui.main.home.homepage.rvitems;

import android.graphics.Color;

import com.cubes.komentar.R;
import com.cubes.komentar.databinding.RvItemBigBinding;
import com.cubes.komentar.pavlovic.data.domain.News;
import com.cubes.komentar.pavlovic.ui.main.home.homepage.HomepageAdapter;
import com.cubes.komentar.pavlovic.ui.tools.MyMethodsClass;
import com.cubes.komentar.pavlovic.ui.tools.listener.NewsListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RvItemBig implements RecyclerViewItemHomepage {

    private final News news;
    private final NewsListener newsListener;
    private final int[] newsListId;


    public RvItemBig(News news, NewsListener newsListener, ArrayList<News> newsList) {
        this.news = news;
        this.newsListener = newsListener;
        this.newsListId = MyMethodsClass.initNewsIdList(newsList);
    }

    @Override
    public int getType() {
        return R.layout.rv_item_big;
    }

    @Override
    public void bind(HomepageAdapter.ViewHolder holder) {

        RvItemBigBinding binding = (RvItemBigBinding) holder.binding;

        binding.textViewTitle.setText(news.title);
        binding.date.setText(news.createdAt);
        binding.textViewCategory.setText(news.category.name);
        binding.textViewCategory.setTextColor(Color.parseColor(news.category.color));

        Picasso.get().load(news.image).into(binding.imageView);

        if (news.isSaved) {
            binding.unSave.setImageResource(R.drawable.ic_save);
        } else {
            binding.unSave.setImageResource(R.drawable.ic_un_save);
        }

        binding.unSave.setOnClickListener(view -> {
            if (news.isSaved) {
                binding.unSave.setImageResource(R.drawable.ic_un_save);
                newsListener.onUnSaveClicked(news.id, news.title);
                news.isSaved = false;
            } else {
                binding.unSave.setImageResource(R.drawable.ic_save);
                newsListener.onSaveClicked(news.id, news.title);
                news.isSaved = true;
            }
        });

        holder.itemView.setOnClickListener(view -> newsListener.onNewsClickedVP(news.id, newsListId));
    }
}
