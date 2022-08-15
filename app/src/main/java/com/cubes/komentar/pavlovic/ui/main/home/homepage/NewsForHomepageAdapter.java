package com.cubes.komentar.pavlovic.ui.main.home.homepage;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.cubes.komentar.databinding.RvItemBigBinding;
import com.cubes.komentar.databinding.RvItemSmallBinding;
import com.cubes.komentar.pavlovic.data.model.News;
import com.cubes.komentar.pavlovic.ui.tools.NewsListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsForHomepageAdapter extends RecyclerView.Adapter<NewsForHomepageAdapter.NewsViewHolder> {

    private final ArrayList<News> newsList;
    private final NewsListener newsListener;


    public NewsForHomepageAdapter(ArrayList<News> newsList, NewsListener newsListener) {
        this.newsList = newsList;
        this.newsListener = newsListener;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ViewBinding binding;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == 0) {
            binding = RvItemBigBinding.inflate(inflater, parent, false);
        } else {
            binding = RvItemSmallBinding.inflate(inflater, parent, false);
        }
        return new NewsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {

        News news = newsList.get(position);

        if (position == 0) {

            RvItemBigBinding bindingBig = (RvItemBigBinding) holder.binding;

            bindingBig.textViewTitle.setText(news.title);
            bindingBig.date.setText(news.created_at);
            bindingBig.textViewCategory.setText(news.category.name);
            bindingBig.textViewCategory.setTextColor(Color.parseColor(news.category.color));

            Picasso.get().load(news.image).into(bindingBig.imageView);
        } else {

            RvItemSmallBinding bindingSmall = (RvItemSmallBinding) holder.binding;
            bindingSmall.textViewTitle.setText(news.title);
            bindingSmall.date.setText(news.created_at);
            bindingSmall.textViewCategory.setText(news.category.name);
            bindingSmall.textViewCategory.setTextColor(Color.parseColor(news.category.color));

            Picasso.get().load(news.image).into(bindingSmall.imageView);
        }

        holder.itemView.setOnClickListener(view -> newsListener.onNewsClicked(news));
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getItemCount() {

        int limit = 5;

        if (newsList == null) {
            return 0;
        } else if (newsList.size() > limit) {
            return limit;
        } else {
            return newsList.size();
        }
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {

        public ViewBinding binding;

        public NewsViewHolder(@NonNull ViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
