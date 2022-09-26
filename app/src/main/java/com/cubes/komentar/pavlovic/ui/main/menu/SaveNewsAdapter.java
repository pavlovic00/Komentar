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
import com.cubes.komentar.pavlovic.ui.tools.listener.ItemTouchHelperContract;
import com.cubes.komentar.pavlovic.ui.tools.listener.NewsListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SaveNewsAdapter extends RecyclerView.Adapter<SaveNewsAdapter.ViewHolder> implements ItemTouchHelperContract {

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

    @SuppressLint("NotifyDataSetChanged")
    public void setSaveNewsData(ArrayList<SaveNews> list) {

        for (SaveNews news : list) {
            items.add(new RvItemSaveNews(newsListener, news, list));
        }

        notifyDataSetChanged();
    }

    @Override
    public void onRowMoved(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(items, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(items, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    public List<SaveNews> getNewList() {

        List<SaveNews> list = new ArrayList<>();

        for (int i = 0; i < items.size(); i++) {
            list.add(items.get(i).getNews());
        }
        return list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewBinding binding;

        public ViewHolder(@NonNull ViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
