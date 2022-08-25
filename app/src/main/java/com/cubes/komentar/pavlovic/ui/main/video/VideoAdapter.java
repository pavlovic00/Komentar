package com.cubes.komentar.pavlovic.ui.main.video;

import android.view.LayoutInflater;
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
import com.cubes.komentar.pavlovic.ui.tools.listener.VideoListener;

import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    private final ArrayList<RecyclerViewItemVideo> items = new ArrayList<>();
    private final VideoListener videoListener;
    private final LoadingNewsListener loadingNewsListener;


    public VideoAdapter(VideoListener videoListener, LoadingNewsListener loadingNewsListener) {
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

        items.remove(items.size() - 1);

        for (int i = 0; i < newsList.size(); i++) {
            items.add(new RvItemVideo(newsList.get(i), videoListener));
        }

        if (newsList.size() == 20) {
            items.add(new RvItemLoadingVideo(loadingNewsListener));
        }

        notifyDataSetChanged();
    }

    public void setData(ArrayList<News> list) {

        items.add(new RvItemVideo(list.get(0), videoListener));

        items.add(new RvItemAdsVideo());
        for (int i = 1; i < list.size(); i++) {
            if (i < 6) {
                items.add(new RvItemVideo(list.get(i), videoListener));
            }
        }

        if (list.size() > 6) {
            items.add(new RvItemAdsVideo());
        }
        for (int i = 6; i < list.size(); i++) {
            if (i < 11) {
                items.add(new RvItemVideo(list.get(i), videoListener));
            }
        }

        if (list.size() > 11) {
            items.add(new RvItemAdsVideo());
        }
        for (int i = 11; i < list.size(); i++) {
            if (i < 16) {
                items.add(new RvItemVideo(list.get(i), videoListener));
            }
        }

        if (list.size() > 16) {
            items.add(new RvItemAdsVideo());
        }
        for (int i = 16; i < list.size(); i++) {
            items.add(new RvItemVideo(list.get(i), videoListener));

        }

        if (list.size() == 20) {
            items.add(new RvItemLoadingVideo(loadingNewsListener));
        }

        notifyDataSetChanged();
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
