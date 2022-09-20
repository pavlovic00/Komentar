package com.cubes.komentar.pavlovic.ui.tag;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.cubes.komentar.R;
import com.cubes.komentar.databinding.RvItemAdsViewBinding;
import com.cubes.komentar.databinding.RvItemLoadingBinding;
import com.cubes.komentar.databinding.RvItemSmallBinding;
import com.cubes.komentar.pavlovic.data.domain.News;
import com.cubes.komentar.pavlovic.ui.tag.rvitems.RecyclerViewItemTag;
import com.cubes.komentar.pavlovic.ui.tag.rvitems.RvItemAdsTag;
import com.cubes.komentar.pavlovic.ui.tag.rvitems.RvItemLoadingTag;
import com.cubes.komentar.pavlovic.ui.tag.rvitems.RvItemSmallTag;
import com.cubes.komentar.pavlovic.ui.tools.listener.LoadingNewsListener;
import com.cubes.komentar.pavlovic.ui.tools.listener.NewsListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;

import java.util.ArrayList;

public class TagsAdapter extends RecyclerView.Adapter<TagsAdapter.ViewHolder> {

    private final ArrayList<RecyclerViewItemTag> items = new ArrayList<>();
    private final NewsListener newsListener;
    private final LoadingNewsListener loadingNewsListener;


    public TagsAdapter(NewsListener newsListener, LoadingNewsListener loadingNewsListener) {
        this.newsListener = newsListener;
        this.loadingNewsListener = loadingNewsListener;
    }

    @NonNull
    @Override
    public TagsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ViewBinding binding;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == R.layout.rv_item_small) {
            binding = RvItemSmallBinding.inflate(inflater, parent, false);
        } else if (viewType == R.layout.rv_item_ads_view) {
            binding = RvItemAdsViewBinding.inflate(inflater, parent, false);
            ((RvItemAdsViewBinding) binding).adsView.setVisibility(View.GONE);
            ((RvItemAdsViewBinding) binding).shimmerLayout.setVisibility(View.VISIBLE);
            ((RvItemAdsViewBinding) binding).shimmerLayout.startShimmerAnimation();

            AdRequest adRequest = new AdRequest.Builder().build();
            ((RvItemAdsViewBinding) binding).adsView.loadAd(adRequest);

            ((RvItemAdsViewBinding) binding).adsView.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    ((RvItemAdsViewBinding) binding).shimmerLayout.stopShimmerAnimation();
                    ((RvItemAdsViewBinding) binding).shimmerLayout.setVisibility(View.GONE);
                    ((RvItemAdsViewBinding) binding).adsView.setVisibility(View.VISIBLE);
                }
            });
        } else {
            binding = RvItemLoadingBinding.inflate(inflater, parent, false);
        }
        return new TagsAdapter.ViewHolder(binding);
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
            items.add(new RvItemSmallTag(newsList.get(i), newsListener, newsList));
        }

        if (newsList.size() == 20) {
            items.add(new RvItemLoadingTag(loadingNewsListener));
        }

        notifyItemRangeInserted(lastIndex, newsList.size());
    }

    public void setTagData(ArrayList<News> list) {

        items.add(new RvItemSmallTag(list.get(0), newsListener, list));

        for (int i = 1; i < list.size(); i++) {

            if ((i - 1) % 5 == 0) {
                items.add(new RvItemAdsTag());
            }
            items.add(new RvItemSmallTag(list.get(i), newsListener, list));
        }

        if (list.size() == 20) {
            items.add(new RvItemLoadingTag(loadingNewsListener));
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