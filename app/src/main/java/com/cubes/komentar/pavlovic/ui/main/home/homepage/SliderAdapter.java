package com.cubes.komentar.pavlovic.ui.main.home.homepage;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.cubes.komentar.databinding.RvItemForHorizontalRvBinding;
import com.cubes.komentar.pavlovic.data.domain.News;
import com.cubes.komentar.pavlovic.ui.tools.MyMethodsClass;
import com.cubes.komentar.pavlovic.ui.tools.listener.NewsListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.ViewHolder> {

    private final ArrayList<News> newsList;
    private final NewsListener newsListener;
    private final int[] newsIdList;


    public SliderAdapter(ArrayList<News> newsList, NewsListener newsListener) {
        this.newsList = newsList;
        this.newsListener = newsListener;

        this.newsIdList = MyMethodsClass.initNewsIdList(newsList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ViewBinding binding;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        binding = RvItemForHorizontalRvBinding.inflate(inflater, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        News news = newsList.get(position);

        RvItemForHorizontalRvBinding binding = (RvItemForHorizontalRvBinding) holder.binding;

        binding.textViewTitle.setText(news.title);
        binding.textViewCategory.setText(news.category.name);
        binding.date.setText(news.createdAt);

        Picasso.get().load(news.image).into(binding.imageView);

        holder.itemView.setOnClickListener(view -> newsListener.onNewsClickedVP(news.id, newsIdList));
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewBinding binding;

        public ViewHolder(@NonNull ViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
