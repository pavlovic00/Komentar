package com.cubes.komentar.pavlovic.ui.main.menu;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.cubes.komentar.databinding.RvItemSaveNewsBinding;
import com.cubes.komentar.pavlovic.data.domain.SaveNews;
import com.cubes.komentar.pavlovic.ui.main.menu.rvitems.RecyclerViewItemSaveNews;
import com.cubes.komentar.pavlovic.ui.main.menu.rvitems.RvItemSaveNews;
import com.cubes.komentar.pavlovic.ui.tools.listener.NewsListener;

import java.util.ArrayList;

public class SaveNewsAdapter extends RecyclerView.Adapter<SaveNewsAdapter.ViewHolder> {

    private final ArrayList<RecyclerViewItemSaveNews> items = new ArrayList<>();
    private final NewsListener newsListener;


    public SaveNewsAdapter(NewsListener newsListener) {
        this.newsListener = newsListener;
    }

    @NonNull
    @Override
    public SaveNewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ViewBinding binding;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        binding = RvItemSaveNewsBinding.inflate(inflater, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SaveNewsAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        this.items.get(position).bind(holder);
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return this.items.get(position).getType();
    }

    public void setSaveNewsData(ArrayList<SaveNews> list) {

        for (SaveNews news : list) {
            items.add(new RvItemSaveNews(newsListener, news, list));
        }

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
