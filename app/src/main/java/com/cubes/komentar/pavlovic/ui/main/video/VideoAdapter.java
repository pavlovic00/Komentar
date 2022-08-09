package com.cubes.komentar.pavlovic.ui.main.video;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.cubes.komentar.databinding.RvItemLoadingBinding;
import com.cubes.komentar.databinding.RvItemVideoBinding;
import com.cubes.komentar.pavlovic.data.model.News;
import com.cubes.komentar.pavlovic.data.source.response.ResponseNewsList;
import com.cubes.komentar.pavlovic.ui.tools.LoadingNewsListener;
import com.cubes.komentar.pavlovic.ui.tools.NewsListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    public ArrayList<News> newsList = new ArrayList<>();
    private boolean isLoading;
    private boolean isFinished;
    private NewsListener newsListener;
    private LoadingNewsListener loadingNewsListener;


    public VideoAdapter() {
    }

    public VideoAdapter(ArrayList<News> newsList) {
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ViewBinding binding;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == 0) {
            binding = RvItemVideoBinding.inflate(inflater, parent, false);
        } else {
            binding = RvItemLoadingBinding.inflate(inflater, parent, false);
        }
        return new VideoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {

        if (position == newsList.size()) {

            RvItemLoadingBinding bindingLoading = (RvItemLoadingBinding) holder.binding;

            if (isFinished) {
                bindingLoading.progressBar.setVisibility(View.GONE);
                bindingLoading.loading.setVisibility(View.GONE);
            }
            if (!isLoading & !isFinished & loadingNewsListener != null) {
                isLoading = true;
                loadingNewsListener.loadMoreNews();
            }

        } else {
            if (newsList.size() > 0) {
                News news = newsList.get(position);

                RvItemVideoBinding bindingVideo = (RvItemVideoBinding) holder.binding;

                bindingVideo.textViewTitle.setText(news.title);
                bindingVideo.date.setText(news.created_at);
                bindingVideo.textViewCategory.setText(news.category.name);
                bindingVideo.textViewCategory.setTextColor(Color.parseColor(news.category.color));
                Picasso.get().load(news.image).into(bindingVideo.imageView);

                bindingVideo.imageViewPlay.setOnClickListener(view -> newsListener.onNewsCLicked(news));
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
        if (newsList.size() > 20) {
            return newsList.size() + 1;
        } else {
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
        if (newsList.size() < 20) {
            setFinished(true);
        }
        notifyDataSetChanged();
    }

    public void setData(ResponseNewsList.ResponseData responseNewsList) {
        this.newsList = responseNewsList.news;
        notifyDataSetChanged();
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder {

        public ViewBinding binding;

        public VideoViewHolder(@NonNull ViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
