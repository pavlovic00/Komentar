package com.cubes.komentar.pavlovic.ui.main.home.homepage;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

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

        public ViewBinding binding;

        public NewsViewHolder(@NonNull ViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
