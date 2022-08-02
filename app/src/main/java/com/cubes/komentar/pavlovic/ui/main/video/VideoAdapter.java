package com.cubes.komentar.pavlovic.ui.main.video;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cubes.komentar.databinding.RvItemLoadingBinding;
import com.cubes.komentar.databinding.RvItemVideoBinding;
import com.cubes.komentar.pavlovic.data.model.News;
import com.cubes.komentar.pavlovic.data.tools.LoadingNewsListener;
import com.cubes.komentar.pavlovic.data.tools.NewsListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private Context context;
    public ArrayList<News> newsList;
    private boolean isLoading;
    private boolean isFinished;
    private NewsListener newsListener;
    private LoadingNewsListener loadingNewsListener;
    private int page;


    public VideoAdapter(Context context, ArrayList<News> newsList) {
        this.context = context;
        this.newsList = newsList;
        this.page = 2;
        this.isLoading = false;
        this.isFinished = false;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == 0) {
            RvItemVideoBinding binding = RvItemVideoBinding.inflate(LayoutInflater.from(context), parent, false);
            return new VideoViewHolder(binding);
        } else {
            RvItemLoadingBinding binding = RvItemLoadingBinding.inflate(LayoutInflater.from(context), parent, false);
            return new VideoViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {

        if (position == newsList.size()) {

            //pod znakom pitanja.
            if (isFinished) {
                holder.bindingLoading.progressBar.setVisibility(View.GONE);
                holder.bindingLoading.loading.setVisibility(View.GONE);
            }
            if (!isLoading & !isFinished & loadingNewsListener != null) {
                isLoading = true;
                loadingNewsListener.loadMoreNews(page);
            }

        } else {
            if (newsList.size() > 0) {
                News news = newsList.get(position);

                holder.binding.textViewTitle.setText(news.title);
                holder.binding.textViewDate.setText(news.created_at);
                holder.binding.textViewCategory.setText(news.category.name);
                holder.binding.textViewCategory.setTextColor(Color.parseColor(news.category.color));
                Picasso.get().load(news.image).into(holder.binding.imageView);

                holder.binding.imageViewPlay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, news.url);
                        sendIntent.setType("text/plain");
                        Intent shareIntent = Intent.createChooser(sendIntent, null);
                        context.startActivity(shareIntent);
                    }
                });
            }
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (position == newsList.size()) {
            return 1;
        } else {
            return 0;
        }

    }

    @Override
    public int getItemCount() {
        if (newsList == null) {
            return 0;
        }
        if (newsList.size() > 20){
            return newsList.size() + 1;
        }
        else{
            return newsList.size();
        }
    }

    public void setLoadingNewsListener(LoadingNewsListener loadingNewsListener) {
        this.loadingNewsListener = loadingNewsListener;
    }

    public void setNewsListener(NewsListener newsListener) {
        this.newsListener = newsListener;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;

    }

    public void addNewsList(ArrayList<News> newsList) {
        this.newsList.addAll(newsList);
        this.isLoading = false;
        this.page = this.page + 1;
        notifyDataSetChanged();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {

        private RvItemVideoBinding binding;
        private RvItemLoadingBinding bindingLoading;

        public VideoViewHolder(RvItemVideoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public VideoViewHolder(RvItemLoadingBinding bindingLoading) {
            super(bindingLoading.getRoot());
            this.bindingLoading = bindingLoading;
        }
    }

}
