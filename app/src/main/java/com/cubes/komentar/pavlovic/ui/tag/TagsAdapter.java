package com.cubes.komentar.pavlovic.ui.tag;

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
import com.cubes.komentar.pavlovic.ui.main.latest.rvitems.RvItemAdsLatest;
import com.cubes.komentar.pavlovic.ui.main.latest.rvitems.RvItemSmallLatest;
import com.cubes.komentar.pavlovic.ui.tag.rvitems.RecyclerViewItemTag;
import com.cubes.komentar.pavlovic.ui.tag.rvitems.RvItemAdsTag;
import com.cubes.komentar.pavlovic.ui.tag.rvitems.RvItemLoadingTag;
import com.cubes.komentar.pavlovic.ui.tag.rvitems.RvItemSmallTag;
import com.cubes.komentar.pavlovic.ui.tools.listener.LoadingNewsListener;
import com.cubes.komentar.pavlovic.ui.tools.listener.NewsListener;

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

        items.remove(items.size() - 1);

        for (int i = 0; i < newsList.size(); i++) {
            items.add(new RvItemSmallTag(newsList.get(i), newsListener));
        }

        if (newsList.size() == 20) {
            items.add(new RvItemLoadingTag(loadingNewsListener));
        }

        notifyDataSetChanged();
    }

    public void setTagData(ArrayList<News> list) {

        items.add(new RvItemSmallTag(list.get(0), newsListener));

        items.add(new RvItemAdsTag());
        for (int i = 1; i < list.size(); i++) {
            if (i < 6) {
                items.add(new RvItemSmallTag(list.get(i), newsListener));
            }
        }

        if (list.size() > 6) {
            items.add(new RvItemAdsTag());
        }
        for (int i = 6; i < list.size(); i++) {
            if (i < 11) {
                items.add(new RvItemSmallTag(list.get(i), newsListener));
            }
        }

        if (list.size() > 11) {
            items.add(new RvItemAdsTag());
        }
        for (int i = 11; i < list.size(); i++) {
            if (i < 16) {
                items.add(new RvItemSmallTag(list.get(i), newsListener));
            }
        }

        if (list.size() > 16) {
            items.add(new RvItemAdsTag());
        }
        for (int i = 16; i < list.size(); i++) {
            items.add(new RvItemSmallTag(list.get(i), newsListener));

        }

        if (list.size() == 20) {
            items.add(new RvItemLoadingTag(loadingNewsListener));
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