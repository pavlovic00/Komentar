package com.cubes.komentar.pavlovic.ui.main.menu.rvitems;

import com.cubes.komentar.R;
import com.cubes.komentar.databinding.RvItemSaveNewsBinding;
import com.cubes.komentar.pavlovic.data.domain.SaveNews;
import com.cubes.komentar.pavlovic.ui.main.menu.SaveNewsAdapter;
import com.cubes.komentar.pavlovic.ui.tools.MyMethodsClass;
import com.cubes.komentar.pavlovic.ui.tools.listener.NewsListener;

import java.util.ArrayList;

public class RvItemSaveNews implements RecyclerViewItemSaveNews {

    private final SaveNews news;
    private final NewsListener newsListener;
    private final int[] newsListId;


    public RvItemSaveNews(NewsListener newsListener, SaveNews news, ArrayList<SaveNews> newsList) {
        this.newsListener = newsListener;
        this.news = news;
        this.newsListId = MyMethodsClass.initSaveNewsIdList(newsList);
    }

    @Override
    public int getType() {
        return R.layout.rv_item_save_news;
    }

    @Override
    public void bind(SaveNewsAdapter.ViewHolder holder) {

        RvItemSaveNewsBinding binding = (RvItemSaveNewsBinding) holder.binding;

        binding.title.setText(news.title);

        binding.delete.setOnClickListener(view -> newsListener.onUnSaveClicked(news.id, news.title));

        holder.itemView.setOnClickListener(view -> newsListener.onNewsClickedVP(news.id, newsListId));
    }
}
