package com.cubes.komentar.pavlovic.ui.main.latest;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.cubes.komentar.R;
import com.cubes.komentar.databinding.RvItemAdsViewBinding;
import com.cubes.komentar.databinding.RvItemBigBinding;
import com.cubes.komentar.databinding.RvItemLoadingBinding;
import com.cubes.komentar.databinding.RvItemSmallBinding;
import com.cubes.komentar.pavlovic.data.domain.News;
import com.cubes.komentar.pavlovic.ui.main.latest.rvitems.RecyclerViewItemLatest;
import com.cubes.komentar.pavlovic.ui.main.latest.rvitems.RvItemAdsLatest;
import com.cubes.komentar.pavlovic.ui.main.latest.rvitems.RvItemBigLatest;
import com.cubes.komentar.pavlovic.ui.main.latest.rvitems.RvItemLoadingLatest;
import com.cubes.komentar.pavlovic.ui.main.latest.rvitems.RvItemSmallLatest;
import com.cubes.komentar.pavlovic.ui.tools.listener.LoadingNewsListener;
import com.cubes.komentar.pavlovic.ui.tools.listener.NewsListener;

import java.util.ArrayList;

public class LatestAdapter extends RecyclerView.Adapter<LatestAdapter.ViewHolder> {

    private final ArrayList<RecyclerViewItemLatest> items = new ArrayList<>();
    private final NewsListener newsListener;
    private final LoadingNewsListener loadingNewsListener;


    public LatestAdapter(NewsListener newsListener, LoadingNewsListener loadingNewsListener) {
        this.newsListener = newsListener;
        this.loadingNewsListener = loadingNewsListener;
    }

    @NonNull
    @Override
    public LatestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ViewBinding binding;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == R.layout.rv_item_big) {
            binding = RvItemBigBinding.inflate(inflater, parent, false);
        } else if (viewType == R.layout.rv_item_small) {
            binding = RvItemSmallBinding.inflate(inflater, parent, false);
        } else if (viewType == R.layout.rv_item_ads_view) {
            binding = RvItemAdsViewBinding.inflate(inflater, parent, false);
        } else {
            binding = RvItemLoadingBinding.inflate(inflater, parent, false);
        }
        return new LatestAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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

    public void addNewsList(ArrayList<News> newsList) {

        items.remove(items.size() - 1);

        for (int i = 0; i < newsList.size(); i++) {
            items.add(new RvItemSmallLatest(newsList.get(i), newsListener, newsList));
        }

        if (newsList.size() == 20) {
            items.add(new RvItemLoadingLatest(loadingNewsListener));
        }

        notifyDataSetChanged();
    }

    public void setData(ArrayList<News> list) {

        items.add(new RvItemBigLatest(list.get(0), newsListener, list));

        for (int i = 1; i < list.size(); i++) {

            if ((i - 1) % 5 == 0) {
                items.add(new RvItemAdsLatest());
            }
            items.add(new RvItemSmallLatest(list.get(i), newsListener, list));
        }

        if (list.size() == 20) {
            items.add(new RvItemLoadingLatest(loadingNewsListener));
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