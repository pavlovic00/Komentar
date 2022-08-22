package com.cubes.komentar.pavlovic.ui.main.home.homepage;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.cubes.komentar.R;
import com.cubes.komentar.databinding.RvItemBigBinding;
import com.cubes.komentar.databinding.RvItemButtonsHomepageBinding;
import com.cubes.komentar.databinding.RvItemHorizontalRv2Binding;
import com.cubes.komentar.databinding.RvItemHorizontalRvBinding;
import com.cubes.komentar.databinding.RvItemHorizontalTextViewBinding;
import com.cubes.komentar.databinding.RvItemSmallBinding;
import com.cubes.komentar.databinding.RvItemVideoBinding;
import com.cubes.komentar.pavlovic.data.domain.CategoryBox;
import com.cubes.komentar.pavlovic.data.domain.News;
import com.cubes.komentar.pavlovic.data.domain.NewsList;
import com.cubes.komentar.pavlovic.ui.main.home.homepage.rvitems.RecyclerViewItemHomepage;
import com.cubes.komentar.pavlovic.ui.main.home.homepage.rvitems.RvItemBig;
import com.cubes.komentar.pavlovic.ui.main.home.homepage.rvitems.RvItemButtonsNews;
import com.cubes.komentar.pavlovic.ui.main.home.homepage.rvitems.RvItemEditorChoice;
import com.cubes.komentar.pavlovic.ui.main.home.homepage.rvitems.RvItemSlider;
import com.cubes.komentar.pavlovic.ui.main.home.homepage.rvitems.RvItemSmall;
import com.cubes.komentar.pavlovic.ui.main.home.homepage.rvitems.RvItemTitle;
import com.cubes.komentar.pavlovic.ui.main.home.homepage.rvitems.RvItemVideo;
import com.cubes.komentar.pavlovic.ui.tools.NewsListener;
import com.cubes.komentar.pavlovic.ui.tools.VideoListener;

import java.util.ArrayList;

public class HomepageAdapter extends RecyclerView.Adapter<HomepageAdapter.ViewHolder> {

    private final ArrayList<RecyclerViewItemHomepage> items = new ArrayList<>();
    private final NewsListener newsListener;
    private final VideoListener videoListener;


    public HomepageAdapter(NewsListener newsListener, VideoListener videoListener) {
        this.newsListener = newsListener;
        this.videoListener = videoListener;
    }

    @SuppressLint("NonConstantResourceId")
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ViewBinding binding;

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case R.layout.rv_item_horizontal_rv:
                binding = RvItemHorizontalRvBinding.inflate(inflater, parent, false);
                break;
            case R.layout.rv_item_small:
                binding = RvItemSmallBinding.inflate(inflater, parent, false);
                break;
            case R.layout.rv_item_buttons_homepage:
                binding = RvItemButtonsHomepageBinding.inflate(inflater, parent, false);
                break;
            case R.layout.rv_item_horizontal_rv2:
                binding = RvItemHorizontalRv2Binding.inflate(inflater, parent, false);
                break;
            case R.layout.rv_item_video:
                binding = RvItemVideoBinding.inflate(inflater, parent, false);
                break;
            case R.layout.rv_item_big:
                binding = RvItemBigBinding.inflate(inflater, parent, false);
                break;
            default:
                binding = RvItemHorizontalTextViewBinding.inflate(inflater, parent, false);
        }

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        this.items.get(position).bind(holder);
    }

    @Override
    public int getItemViewType(int position) {

        return this.items.get(position).getType();
    }

    @Override
    public int getItemCount() {

        return this.items.size();
    }

    public void setDataItems(NewsList response) {
        //0
        items.add(new RvItemSlider(response.slider, newsListener));
        //1
        for (int i = 0; i < response.top.size(); i++) {
            News news = response.top.get(i);
            items.add(new RvItemSmall(news, newsListener));
        }
        //2
        items.add(new RvItemButtonsNews(response.latest, response.most_commented, response.most_read, newsListener));
        //3-4
        if (response.editors_choice.size() > 0) {
            items.add(new RvItemTitle("Izbor urednika", "#FF0000"));
            items.add(new RvItemEditorChoice(response.editors_choice, newsListener));
        }
        //5-6
        if (response.videos.size() > 0) {
            items.add(new RvItemTitle("Video", "#FF0000"));

            for (News video : response.videos) {
                items.add(new RvItemVideo(video, videoListener));
            }
        }
        //7-8-9
        for (CategoryBox categoryBox : response.category) {
            items.add(new RvItemTitle(categoryBox.title, categoryBox.color));
            items.add(new RvItemBig(categoryBox.news.get(0), newsListener));

            for (int i = 1; i < 5; i++) {
                items.add(new RvItemSmall(categoryBox.news.get(i), newsListener));
            }
        }
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewBinding binding;

        public ViewHolder(ViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
