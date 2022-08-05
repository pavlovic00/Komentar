package com.cubes.komentar.pavlovic.ui.main.home.homepage;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.cubes.komentar.databinding.RvItemForHorizontalRvBinding;
import com.cubes.komentar.databinding.RvItemTextForNewsHomepageBinding;
import com.cubes.komentar.pavlovic.data.model.News;
import com.cubes.komentar.pavlovic.ui.details.NewsDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderHolder> {

    private ArrayList<News> list;


    public SliderAdapter(ArrayList<News> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public SliderAdapter.SliderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ViewBinding binding;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        binding = RvItemForHorizontalRvBinding.inflate(inflater,parent,false);

        return new SliderHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderAdapter.SliderHolder holder, int position) {
        News news = list.get(position);

        RvItemForHorizontalRvBinding binding = (RvItemForHorizontalRvBinding) holder.binding;

        binding.textViewTitle.setText(news.title);
        binding.textViewCategory.setText(news.category.name);
        binding.date.setText(news.created_at);

        Picasso.get().load(news.image).into(binding.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startDetailIntent = new Intent(view.getContext(), NewsDetailActivity.class);
                startDetailIntent.putExtra("id", news.id);
                view.getContext().startActivity(startDetailIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SliderHolder extends RecyclerView.ViewHolder {

        public ViewBinding binding;

        public SliderHolder(@NonNull ViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
