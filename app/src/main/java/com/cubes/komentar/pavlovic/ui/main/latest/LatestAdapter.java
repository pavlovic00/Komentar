package com.cubes.komentar.pavlovic.ui.main.latest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cubes.komentar.databinding.RvItemBigBinding;
import com.cubes.komentar.databinding.RvItemLoadingBinding;
import com.cubes.komentar.databinding.RvItemSmallBinding;
import com.cubes.komentar.pavlovic.data.tools.LoadingNewsListener;
import com.cubes.komentar.pavlovic.data.tools.NewsListener;
import com.cubes.komentar.pavlovic.data.model.News;
import com.cubes.komentar.pavlovic.ui.details.NewsDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LatestAdapter extends RecyclerView.Adapter<LatestAdapter.LatestViewHolder> {

    private Context context;
    public ArrayList<News> newsList;
    private boolean isLoading;
    private boolean isFinished;
    private NewsListener newsListener;
    private LoadingNewsListener loadingNewsListener;
    private int page;


    public LatestAdapter(Context context, ArrayList<News> newsList) {
        this.context = context;
        this.newsList = newsList;
        this.page = 2;
        this.isLoading = false;
        this.isFinished = false;
    }

    @NonNull
    @Override
    public LatestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == 0) {
            RvItemBigBinding binding = RvItemBigBinding.inflate(LayoutInflater.from(context),parent,false);
            return  new LatestViewHolder(binding);
        }
        else if (viewType == 1){
            RvItemSmallBinding binding = RvItemSmallBinding.inflate(LayoutInflater.from(context),parent,false);
            return  new LatestViewHolder(binding);
        }
        else {
            RvItemLoadingBinding binding = RvItemLoadingBinding.inflate(LayoutInflater.from(context),parent,false);
            return  new LatestViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull LatestViewHolder holder, int position) {

        if(position == 0){

            if (newsList.size() > 0) {
                News news = newsList.get(position);


                holder.bindingBig.textViewTitle.setText(news.title);
                holder.bindingBig.textViewDate.setText(news.created_at);
                holder.bindingBig.textViewCategory.setText(news.category.name);
                holder.bindingBig.textViewCategory.setTextColor(Color.parseColor(news.category.color));

                Picasso.get().load(news.image).into(holder.bindingBig.imageView);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent startDetailIntent = new Intent(view.getContext(), NewsDetailActivity.class);
                        startDetailIntent.putExtra("id", news.id);
                        view.getContext().startActivity(startDetailIntent);
                    }
                });
            }
        }
        else if (position>0 & position<newsList.size()){

            if (newsList.size() >0) {
                News news = newsList.get(position);

                holder.bindingSmall.textViewTitle.setText(news.title);
                holder.bindingSmall.textViewDate.setText(news.created_at);
                holder.bindingSmall.textViewCategory.setText(news.category.name);
                holder.bindingSmall.textViewCategory.setTextColor(Color.parseColor(news.category.color));

                Picasso.get().load(news.image).into(holder.bindingSmall.imageView);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent startDetailIntent = new Intent(view.getContext(), NewsDetailActivity.class);
                        startDetailIntent.putExtra("id", news.id);
                        view.getContext().startActivity(startDetailIntent);
                    }
                });
            }
        }
        else {
            if (isFinished){
                holder.bindingLoading.progressBar.setVisibility(View.GONE);
                holder.bindingLoading.loading.setVisibility(View.GONE);
            }
            if (!isLoading & !isFinished & loadingNewsListener != null){
                isLoading = true;
                loadingNewsListener.loadMoreNews(page);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0){
            return 0;
        }
        else if (position == newsList.size()){
            return 2;
        }
        else {
            return 1;
        }
    }

    @Override
    public int getItemCount() {

        if (newsList == null){
            return 0;
        }

        return newsList.size()+1;
    }

    public void setLoadingNewsListener(LoadingNewsListener loadingNewsListener) {
        this.loadingNewsListener = loadingNewsListener;
    }

    public void setNewsListener(NewsListener newsListener){
        this.newsListener = newsListener;
    }

    public void setFinished(boolean finished){
        isFinished = finished;
    }

    public void addNewsList(ArrayList<News> list){
        this.newsList.addAll(list);
        this.isLoading = false;
        this.page = this.page+1;
        notifyDataSetChanged();
    }

    public void refreshNewsList(ArrayList<News> newsList){
        this.newsList = newsList;
        notifyDataSetChanged();
    }

    public class LatestViewHolder extends RecyclerView.ViewHolder{

        private RvItemSmallBinding bindingSmall;
        private RvItemBigBinding bindingBig;
        private RvItemLoadingBinding bindingLoading;

        public LatestViewHolder(RvItemSmallBinding bindingSmall) {
            super(bindingSmall.getRoot());
            this.bindingSmall = bindingSmall;
        }

        public LatestViewHolder(RvItemBigBinding bindingBig) {
            super(bindingBig.getRoot());
            this.bindingBig = bindingBig;
        }

        public LatestViewHolder(RvItemLoadingBinding bindingLoading){
            super(bindingLoading.getRoot());
            this.bindingLoading = bindingLoading;
        }
    }
}
