package com.cubes.komentar.pavlovic.ui.tag;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.cubes.komentar.databinding.RvItemLoadingBinding;
import com.cubes.komentar.databinding.RvItemSmallBinding;
import com.cubes.komentar.pavlovic.data.model.News;
import com.cubes.komentar.pavlovic.data.response.ResponseNewsList;
import com.cubes.komentar.pavlovic.data.tools.LoadingNewsListener;
import com.cubes.komentar.pavlovic.data.tools.NewsListener;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TagsAdapter extends RecyclerView.Adapter<TagsAdapter.TagsViewHolder> {

    public ArrayList<News> newsList = new ArrayList<>();
    private boolean isLoading;
    private boolean isFinished;
    private NewsListener newsListener;
    private LoadingNewsListener loadingNewsListener;
    private int page = 2;


    public TagsAdapter() {
    }

    @NonNull
    @Override
    public TagsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ViewBinding binding;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == 0) {
            binding = RvItemSmallBinding.inflate(inflater, parent, false);
        } else {
            binding = RvItemLoadingBinding.inflate(inflater, parent, false);
        }
        return new TagsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TagsViewHolder holder, int position) {

        if (position == newsList.size()) {

            RvItemLoadingBinding bindingLoading = (RvItemLoadingBinding) holder.binding;


            if (isFinished) {
                bindingLoading.progressBar.setVisibility(View.GONE);
                bindingLoading.loading.setVisibility(View.GONE);
            }
            if (!isLoading & !isFinished & loadingNewsListener != null) {
                isLoading = true;
                loadingNewsListener.loadMoreNews(page);
            }
        } else {

            if (newsList.size() > 0) {
                News news = newsList.get(position);

                RvItemSmallBinding bindingSmall = (RvItemSmallBinding) holder.binding;


                bindingSmall.textViewTitle.setText(news.title);
                bindingSmall.date.setText(news.created_at);
                bindingSmall.textViewCategory.setText(news.category.name);
                bindingSmall.textViewCategory.setTextColor((Color.parseColor(news.category.color)));
                Picasso.get().load(news.image).into(bindingSmall.imageView);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        newsListener.onNewsCLicked(news);
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
        if (list.size() < 20) {
            setFinished(true);
        }
        notifyDataSetChanged();
    }

    public void setDataTags(ResponseNewsList responseNewsList) {
        this.newsList = responseNewsList.data.news;
        notifyDataSetChanged();
    }

    public class TagsViewHolder extends RecyclerView.ViewHolder {

        public ViewBinding binding;

        public TagsViewHolder(@NonNull ViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}