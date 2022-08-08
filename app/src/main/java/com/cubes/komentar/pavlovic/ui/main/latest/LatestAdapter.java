package com.cubes.komentar.pavlovic.ui.main.latest;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.cubes.komentar.databinding.RvItemBigBinding;
import com.cubes.komentar.databinding.RvItemLoadingBinding;
import com.cubes.komentar.databinding.RvItemSmallBinding;
import com.cubes.komentar.pavlovic.data.response.ResponseNewsList;
import com.cubes.komentar.pavlovic.ui.tools.LoadingNewsListener;
import com.cubes.komentar.pavlovic.ui.tools.NewsListener;
import com.cubes.komentar.pavlovic.data.model.News;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LatestAdapter extends RecyclerView.Adapter<LatestAdapter.LatestViewHolder> {

    public ArrayList<News> newsList = new ArrayList<>();
    private boolean isLoading;
    private boolean isFinished;
    private NewsListener newsListener;
    private LoadingNewsListener loadingNewsListener;


    public LatestAdapter() {
    }

    @NonNull
    @Override
    public LatestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ViewBinding binding;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == 0) {
            binding = RvItemBigBinding.inflate(inflater, parent, false);
        } else if (viewType == 1) {
            binding = RvItemSmallBinding.inflate(inflater, parent, false);
        } else {
            binding = RvItemLoadingBinding.inflate(inflater, parent, false);

        }
        return new LatestViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull LatestViewHolder holder, int position) {



        if (position == 0) {

            if (newsList.size() > 0) {
                News news = newsList.get(position);

                RvItemBigBinding bindingBig = (RvItemBigBinding) holder.binding;

                bindingBig.textViewTitle.setText(news.title);
                bindingBig.date.setText(news.created_at);
                bindingBig.textViewCategory.setText(news.category.name);
                bindingBig.textViewCategory.setTextColor(Color.parseColor(news.category.color));

                Picasso.get().load(news.image).into(bindingBig.imageView);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        newsListener.onNewsCLicked(news);
                    }
                });
            }
        } else if (position > 0 & position < newsList.size()) {

            if (newsList.size() > 0) {
                News news = newsList.get(position);

                RvItemSmallBinding bindingSmall = (RvItemSmallBinding) holder.binding;

                bindingSmall.textViewTitle.setText(news.title);
                bindingSmall.date.setText(news.created_at);
                bindingSmall.textViewCategory.setText(news.category.name);
                bindingSmall.textViewCategory.setTextColor(Color.parseColor(news.category.color));

                Picasso.get().load(news.image).into(bindingSmall.imageView);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        newsListener.onNewsCLicked(news);
                    }
                });
            }
        } else {

            RvItemLoadingBinding bindingLoading = (RvItemLoadingBinding) holder.binding;

            if (isFinished) {
                bindingLoading.progressBar.setVisibility(View.GONE);
                bindingLoading.loading.setVisibility(View.GONE);
            }
            if (!isLoading & !isFinished & loadingNewsListener != null) {
                isLoading = true;
                loadingNewsListener.loadMoreNews();
            }
        }
    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0) {
            return 0;
        } else if (position == newsList.size()) {
            return 2;
        } else {
            return 1;
        }
    }

    @Override
    public int getItemCount() {

        if (newsList == null) {
            return 0;
        }
        if (newsList.size() == 20 || newsList.size() > 20) {
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
        if (list.size() < 20) {
            setFinished(true);
        }
        notifyDataSetChanged();
    }

    public void setData(ResponseNewsList.ResponseData responseNewsList) {
        this.newsList = responseNewsList.news;
        notifyDataSetChanged();
    }

    public class LatestViewHolder extends RecyclerView.ViewHolder {

        public ViewBinding binding;

        public LatestViewHolder(@NonNull ViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
