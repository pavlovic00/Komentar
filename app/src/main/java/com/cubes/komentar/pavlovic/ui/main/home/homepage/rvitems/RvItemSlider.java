package com.cubes.komentar.pavlovic.ui.main.home.homepage.rvitems;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.cubes.komentar.R;
import com.cubes.komentar.databinding.RvItemHorizontalRvBinding;
import com.cubes.komentar.pavlovic.data.domain.News;
import com.cubes.komentar.pavlovic.ui.main.home.homepage.HomepageAdapter;
import com.cubes.komentar.pavlovic.ui.main.home.homepage.SliderAdapter;
import com.cubes.komentar.pavlovic.ui.tools.listener.NewsListener;

import java.util.ArrayList;

public class RvItemSlider implements RecyclerViewItemHomepage {

    private final ArrayList<News> sliderList;
    private final PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
    private final NewsListener newsListener;


    public RvItemSlider(ArrayList<News> sliderList, NewsListener newsListener) {
        this.sliderList = sliderList;
        this.newsListener = newsListener;
    }

    @Override
    public int getType() {
        return R.layout.rv_item_horizontal_rv;
    }

    @Override
    public void bind(HomepageAdapter.ViewHolder holder) {

        RvItemHorizontalRvBinding binding = (RvItemHorizontalRvBinding) holder.binding;

        binding.recyclerViewHorizontal.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext(),
                RecyclerView.HORIZONTAL, false));
        binding.recyclerViewHorizontal.setAdapter(new SliderAdapter(sliderList, newsListener, true));

        if (binding.recyclerViewHorizontal.getOnFlingListener() == null) {
            pagerSnapHelper.attachToRecyclerView(binding.recyclerViewHorizontal);
            binding.indicator.attachToRecyclerView(binding.recyclerViewHorizontal, pagerSnapHelper);
        }
    }
}
