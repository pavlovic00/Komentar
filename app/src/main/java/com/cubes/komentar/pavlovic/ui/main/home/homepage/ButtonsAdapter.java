package com.cubes.komentar.pavlovic.ui.main.home.homepage;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cubes.komentar.databinding.RvItemTextForNewsHomepageBinding;
import com.cubes.komentar.pavlovic.data.model.News;
import com.cubes.komentar.pavlovic.ui.details.NewsDetailActivity;

import java.util.ArrayList;

public class ButtonsAdapter extends RecyclerView.Adapter<ButtonsAdapter.ButtonsHolder> {

    private ArrayList<News> list;


    public ButtonsAdapter(ArrayList<News> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ButtonsAdapter.ButtonsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RvItemTextForNewsHomepageBinding binding = RvItemTextForNewsHomepageBinding.
                inflate(LayoutInflater.from(parent.getContext()),parent,false);

        return new ButtonsAdapter.ButtonsHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ButtonsAdapter.ButtonsHolder holder, int position) {
        News news = list.get(position);

        holder.binding.textViewTitle.setText(news.title);
        holder.binding.textViewDate.setText(news.created_at);

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
        return list.size();
    }

    public class ButtonsHolder extends RecyclerView.ViewHolder {

        RvItemTextForNewsHomepageBinding binding;

        public ButtonsHolder(@NonNull RvItemTextForNewsHomepageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
