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
import com.cubes.komentar.pavlovic.data.domain.News;
import com.cubes.komentar.pavlovic.ui.tools.LoadingNewsListener;
import com.cubes.komentar.pavlovic.ui.tools.NewsListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LatestAdapter extends RecyclerView.Adapter<LatestAdapter.ViewHolder> {

    private ArrayList<News> newsList = new ArrayList<>();
    private boolean isLoading;
    private boolean isFinished;
    private NewsListener newsListener;
    private LoadingNewsListener loadingNewsListener;


    public LatestAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ViewBinding binding;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == 0) {
            binding = RvItemBigBinding.inflate(inflater, parent, false);
        } else if (viewType == 1) {
            binding = RvItemSmallBinding.inflate(inflater, parent, false);
        } else {
            binding = RvItemLoadingBinding.inflate(inflater, parent, false);

        }
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (position == 0) {

            if (newsList.size() > 0) {
                News news = newsList.get(position);

                RvItemBigBinding bindingBig = (RvItemBigBinding) holder.binding;

                bindingBig.textViewTitle.setText(news.title);
                bindingBig.date.setText(news.createdAt);
                bindingBig.textViewCategory.setText(news.category.name);
                bindingBig.textViewCategory.setTextColor(Color.parseColor(news.category.color));

                Picasso.get().load(news.image).into(bindingBig.imageView);
                holder.itemView.setOnClickListener(view -> newsListener.onNewsClicked(news));
            }
        } else if (position > 0 & position < newsList.size()) {
            News news = newsList.get(position);

            RvItemSmallBinding bindingSmall = (RvItemSmallBinding) holder.binding;

            bindingSmall.textViewTitle.setText(news.title);
            bindingSmall.date.setText(news.createdAt);
            bindingSmall.textViewCategory.setText(news.category.name);
            bindingSmall.textViewCategory.setTextColor(Color.parseColor(news.category.color));

            Picasso.get().load(news.image).into(bindingSmall.imageView);
            holder.itemView.setOnClickListener(view -> newsListener.onNewsClicked(news));
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

    public void setData(ArrayList<News> responseNewsList) {
        this.newsList = responseNewsList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewBinding binding;

        public ViewHolder(@NonNull ViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
