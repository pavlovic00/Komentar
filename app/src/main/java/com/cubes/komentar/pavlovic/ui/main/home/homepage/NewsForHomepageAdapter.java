package com.cubes.komentar.pavlovic.ui.main.home.homepage;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cubes.komentar.databinding.RvItemBigBinding;
import com.cubes.komentar.databinding.RvItemSmallBinding;
import com.cubes.komentar.pavlovic.data.model.News;
import com.cubes.komentar.pavlovic.ui.details.NewsDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsForHomepageAdapter extends RecyclerView.Adapter<NewsForHomepageAdapter.NewsViewHolder> {

    public ArrayList<News> newsList;
    private final int limit = 5;


    public NewsForHomepageAdapter(ArrayList<News> newsList) {
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == 0) {
            RvItemBigBinding binding = RvItemBigBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new NewsViewHolder(binding);
        } else {
            RvItemSmallBinding binding = RvItemSmallBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new NewsViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {

        News news = newsList.get(position);

        if (position == 0) {
            holder.bindingBig.textViewTitle.setText(news.title);
            holder.bindingBig.textViewDate.setText(news.created_at);
            holder.bindingBig.textViewCategory.setText(news.category.name);
            holder.bindingBig.textViewCategory.setTextColor(Color.parseColor(news.category.color));

            Picasso.get().load(news.image).into(holder.bindingBig.imageView);
        } else {
            holder.bindingSmall.textViewTitle.setText(news.title);
            holder.bindingSmall.textViewDate.setText(news.created_at);
            holder.bindingSmall.textViewCategory.setText(news.category.name);
            holder.bindingSmall.textViewCategory.setTextColor(Color.parseColor(news.category.color));

            Picasso.get().load(news.image).into(holder.bindingSmall.imageView);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startDetailIntent = new Intent(view.getContext(), NewsDetailActivity.class);
                startDetailIntent.putExtra("id", news.id);
                view.getContext().startActivity(startDetailIntent);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getItemCount() {

        if (newsList == null) {
            return 0;
        } else if (newsList.size() > limit) {
            return limit;
        } else {
            return newsList.size();
        }
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {

        private RvItemSmallBinding bindingSmall;
        private RvItemBigBinding bindingBig;

        public NewsViewHolder(RvItemSmallBinding bindingSmall) {
            super(bindingSmall.getRoot());
            this.bindingSmall = bindingSmall;
        }

        public NewsViewHolder(RvItemBigBinding bindingBig) {
            super(bindingBig.getRoot());
            this.bindingBig = bindingBig;
        }
    }
}
