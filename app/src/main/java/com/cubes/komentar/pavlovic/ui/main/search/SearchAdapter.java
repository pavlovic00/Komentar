package com.cubes.komentar.pavlovic.ui.main.search;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.cubes.komentar.R;
import com.cubes.komentar.databinding.RvItemAdsViewBinding;
import com.cubes.komentar.databinding.RvItemLoadingBinding;
import com.cubes.komentar.databinding.RvItemSmallBinding;
import com.cubes.komentar.pavlovic.data.domain.News;
import com.cubes.komentar.pavlovic.ui.main.search.rvitems.RecyclerViewItemSearch;
import com.cubes.komentar.pavlovic.ui.main.search.rvitems.RvItemAdsSearch;
import com.cubes.komentar.pavlovic.ui.main.search.rvitems.RvItemLoadingSearch;
import com.cubes.komentar.pavlovic.ui.main.search.rvitems.RvItemSmallSearch;
import com.cubes.komentar.pavlovic.ui.tools.listener.LoadingNewsListener;
import com.cubes.komentar.pavlovic.ui.tools.listener.NewsListener;
import com.google.android.gms.ads.AdRequest;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private final ArrayList<RecyclerViewItemSearch> items = new ArrayList<>();
    private final NewsListener newsListener;
    private final LoadingNewsListener loadingNewsListener;


    public SearchAdapter(NewsListener newsListener, LoadingNewsListener loadingNewsListener) {
        this.newsListener = newsListener;
        this.loadingNewsListener = loadingNewsListener;
    }

    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ViewBinding binding;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == R.layout.rv_item_small) {
            binding = RvItemSmallBinding.inflate(inflater, parent, false);
        } else if (viewType == R.layout.rv_item_ads_view) {
            binding = RvItemAdsViewBinding.inflate(inflater, parent, false);
            AdRequest adRequest = new AdRequest.Builder().build();
            ((RvItemAdsViewBinding) binding).adsView.loadAd(adRequest);
        } else {
            binding = RvItemLoadingBinding.inflate(inflater, parent, false);
        }
        return new SearchAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
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

        int lastIndex = items.size();

        items.remove(items.size() - 1);

        for (int i = 0; i < newsList.size(); i++) {
            items.add(new RvItemSmallSearch(newsList.get(i), newsListener, newsList));
        }

        if (newsList.size() == 20) {
            items.add(new RvItemLoadingSearch(loadingNewsListener));
        }

        notifyItemRangeInserted(lastIndex, newsList.size());
    }

    public void setSearchData(ArrayList<News> list) {

        items.add(new RvItemSmallSearch(list.get(0), newsListener, list));

        for (int i = 1; i < list.size(); i++) {

            if ((i - 1) % 5 == 0) {
                items.add(new RvItemAdsSearch());
            }
            items.add(new RvItemSmallSearch(list.get(i), newsListener, list));
        }

        if (list.size() == 20) {
            items.add(new RvItemLoadingSearch(loadingNewsListener));
        }

        notifyDataSetChanged();
    }

    public void removeItem() {
        items.remove(items.size() - 1);
        notifyItemRemoved(items.size());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewBinding binding;

        public ViewHolder(@NonNull ViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}