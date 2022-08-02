package com.cubes.komentar.pavlovic.ui.main.search;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cubes.komentar.databinding.RvItemLoadingBinding;
import com.cubes.komentar.databinding.RvItemSmallBinding;
import com.cubes.komentar.pavlovic.data.model.News;
import com.cubes.komentar.pavlovic.data.tools.LoadingNewsListener;
import com.cubes.komentar.pavlovic.data.tools.NewsListener;
import com.cubes.komentar.pavlovic.ui.details.NewsDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private Context context;
    public ArrayList<News> newsList;
    private boolean isLoading;
    private boolean isFinished;
    private NewsListener newsListener;
    private LoadingNewsListener loadingNewsListener;
    private int page;


    public SearchAdapter(Context context, ArrayList<News> newsList) {
        this.context = context;
        this.newsList = newsList;
        this.page = 2;
        this.isLoading = false;
        this.isFinished = false;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == 0) {
            RvItemSmallBinding binding = RvItemSmallBinding.inflate(LayoutInflater.from(context), parent, false);
            return new SearchAdapter.SearchViewHolder(binding);
        } else {
            RvItemLoadingBinding binding = RvItemLoadingBinding.inflate(LayoutInflater.from(context), parent, false);
            return new SearchAdapter.SearchViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {

        if (position == newsList.size()) {

            if (isFinished) {
                holder.loadingBinding.progressBar.setVisibility(View.GONE);
                holder.loadingBinding.loading.setVisibility(View.GONE);
            }
            if (!isLoading & !isFinished & loadingNewsListener != null) {
                isLoading = true;
                loadingNewsListener.loadMoreNews(page);
            }
        } else {

            if (newsList.size() > 0) {
                News news = newsList.get(position);

                holder.smallBinding.textViewTitle.setText(news.title);
                holder.smallBinding.textViewDate.setText(news.created_at);
                holder.smallBinding.textViewCategory.setText(news.category.name);
                holder.smallBinding.textViewCategory.setTextColor((Color.parseColor(news.category.color)));
                Picasso.get().load(news.image).into(holder.smallBinding.imageView);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent startDetailIntent = new Intent(view.getContext(), NewsDetailActivity.class);
                        startDetailIntent.putExtra("id", news.id);
                        startDetailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        view.getContext().startActivity(startDetailIntent);
                    }
                });
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == newsList.size()) {
            return 1;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        if (newsList == null) {
            return 0;
        } else if (newsList.size() == 20 || newsList.size() > 20) {
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

    public void addNewsList(ArrayList<News> list) {
        this.newsList.addAll(list);
        this.isLoading = false;
        this.page = this.page + 1;
        notifyDataSetChanged();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {

        private RvItemSmallBinding smallBinding;
        private RvItemLoadingBinding loadingBinding;

        public SearchViewHolder(RvItemSmallBinding smallBinding) {
            super(smallBinding.getRoot());
            this.smallBinding = smallBinding;
        }

        public SearchViewHolder(RvItemLoadingBinding loadingBinding) {
            super(loadingBinding.getRoot());
            this.loadingBinding = loadingBinding;
        }
    }
}