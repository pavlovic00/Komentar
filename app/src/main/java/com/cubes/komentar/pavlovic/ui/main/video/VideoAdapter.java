package com.cubes.komentar.pavlovic.ui.main.video;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.cubes.komentar.R;
import com.cubes.komentar.databinding.RvItemAdsViewBinding;
import com.cubes.komentar.databinding.RvItemLoadingBinding;
import com.cubes.komentar.databinding.RvItemVideoBinding;
import com.cubes.komentar.pavlovic.data.domain.News;
import com.cubes.komentar.pavlovic.ui.main.video.rvitems.RecyclerViewItemVideo;
import com.cubes.komentar.pavlovic.ui.main.video.rvitems.RvItemAdsVideo;
import com.cubes.komentar.pavlovic.ui.main.video.rvitems.RvItemLoadingVideo;
import com.cubes.komentar.pavlovic.ui.main.video.rvitems.RvItemVideo;
import com.cubes.komentar.pavlovic.ui.tools.listener.LoadingNewsListener;
import com.cubes.komentar.pavlovic.ui.tools.listener.NewsListener;
import com.cubes.komentar.pavlovic.ui.tools.listener.VideoListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;

import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    private final ArrayList<RecyclerViewItemVideo> items = new ArrayList<>();
    private final NewsListener newsListener;
    private final VideoListener videoListener;
    private final LoadingNewsListener loadingNewsListener;


    public VideoAdapter(NewsListener newsListener, LoadingNewsListener loadingNewsListener, VideoListener videoListener) {
        this.newsListener = newsListener;
        this.videoListener = videoListener;
        this.loadingNewsListener = loadingNewsListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ViewBinding binding;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == R.layout.rv_item_video) {
            binding = RvItemVideoBinding.inflate(inflater, parent, false);
        } else if (viewType == R.layout.rv_item_ads_view) {
            binding = RvItemAdsViewBinding.inflate(inflater, parent, false);
            ((RvItemAdsViewBinding) binding).view.setBackgroundColor(Color.parseColor("#8088909D"));
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
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        this.items.get(position).bind(holder);
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public void addNewsList(ArrayList<News> newsList) {

        int lastIndex = items.size();

        items.remove(items.size() - 1);

        for (int i = 0; i < newsList.size(); i++) {
            items.add(new RvItemVideo(newsList.get(i), newsListener, newsList, videoListener));
        }

        if (newsList.size() == 20) {
            items.add(new RvItemLoadingVideo(loadingNewsListener));
        }

        notifyItemRangeInserted(lastIndex, newsList.size());
    }

    public void setData(ArrayList<News> list) {

        items.add(new RvItemVideo(list.get(0), newsListener, list, videoListener));

        for (int i = 1; i < list.size(); i++) {

            if ((i - 1) % 5 == 0) {
                items.add(new RvItemAdsVideo());
            }
            items.add(new RvItemVideo(list.get(i), newsListener, list, videoListener));
        }

        if (list.size() == 20) {
            items.add(new RvItemLoadingVideo(loadingNewsListener));
        }

        notifyDataSetChanged();
    }

    public void removeItem() {
        items.remove(items.size() - 1);
        notifyItemRemoved(items.size());
    }

    @Override
    public int getItemViewType(int position) {
        return this.items.get(position).getType();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewBinding binding;

        public ViewHolder(@NonNull ViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
