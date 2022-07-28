package com.cubes.komentar.pavlovic.ui.main.search;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cubes.komentar.databinding.RvItemSmallBinding;
import com.cubes.komentar.pavlovic.data.model.News;
import com.cubes.komentar.pavlovic.ui.details.NewsDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private Context context;
    public ArrayList<News> list;


    public SearchAdapter(Context context, ArrayList<News> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RvItemSmallBinding binding = RvItemSmallBinding.inflate(LayoutInflater.from(context),parent,false);

        return  new SearchViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {

        News news = list.get(position);

        holder.smallBinding.textViewTitle.setText(news.title);
        holder.smallBinding.textViewDate.setText(news.created_at);
        holder.smallBinding.textViewCategory.setText(news.category.name);
        holder.smallBinding.textViewCategory.setTextColor((Color.parseColor(news.category.color)));
        Picasso.get().load(news.image).into(holder.smallBinding.imageView);

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
    public int getItemCount() {
        if(list == null){
            return 0;
        }
        return list.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder{

        private RvItemSmallBinding smallBinding;

        public SearchViewHolder(RvItemSmallBinding smallBinding) {
            super(smallBinding.getRoot());
            this.smallBinding = smallBinding;
        }
    }
}