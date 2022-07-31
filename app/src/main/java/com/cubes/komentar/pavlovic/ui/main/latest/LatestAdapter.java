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
import com.cubes.komentar.databinding.RvItemSmallBinding;
import com.cubes.komentar.pavlovic.data.model.News;
import com.cubes.komentar.pavlovic.ui.details.NewsDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LatestAdapter extends RecyclerView.Adapter<LatestAdapter.LatestViewHolder> {

    private Context context;
    public ArrayList<News> newsList;


    public LatestAdapter(Context context, ArrayList<News> newsList) {
        this.context = context;
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public LatestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == 0) {
            RvItemBigBinding binding = RvItemBigBinding.inflate(LayoutInflater.from(context),parent,false);
            return  new LatestViewHolder(binding);
        }
        else{
            RvItemSmallBinding binding = RvItemSmallBinding.inflate(LayoutInflater.from(context),parent,false);
            return  new LatestViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull LatestViewHolder holder, int position) {

        News news = newsList.get(position);

        if(position == 0){
            holder.bindingBig.textViewTitle.setText(news.title);
            holder.bindingBig.textViewDate.setText(news.created_at);
            holder.bindingBig.textViewCategory.setText(news.category.name);
            holder.bindingBig.textViewCategory.setTextColor(Color.parseColor(news.category.color));

            Picasso.get().load(news.image).into(holder.bindingBig.imageView);
        }
        else{
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
                startDetailIntent.putExtra("id",news.id);
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

        if (newsList == null){
            return 0;
        }

        return newsList.size();
    }

    public class LatestViewHolder extends RecyclerView.ViewHolder{

        private RvItemSmallBinding bindingSmall;
        private RvItemBigBinding bindingBig;

        public LatestViewHolder(RvItemSmallBinding bindingSmall) {
            super(bindingSmall.getRoot());
            this.bindingSmall = bindingSmall;
        }

        public LatestViewHolder(RvItemBigBinding bindingBig) {
            super(bindingBig.getRoot());
            this.bindingBig = bindingBig;
        }
    }
}
