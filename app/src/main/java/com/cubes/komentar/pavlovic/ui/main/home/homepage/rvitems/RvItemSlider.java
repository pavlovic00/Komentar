package com.cubes.komentar.pavlovic.ui.main.home.homepage.rvitems;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.cubes.komentar.databinding.RvItemHorizontalRvBinding;
import com.cubes.komentar.pavlovic.data.model.News;
import com.cubes.komentar.pavlovic.ui.main.home.homepage.HomepageAdapter;
import com.cubes.komentar.pavlovic.ui.main.home.homepage.SliderAdapter;

import java.util.ArrayList;

public class RvItemSlider implements RecyclerViewItemHomepage {

    public ArrayList<News> sliderList;
    PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();


    public RvItemSlider(ArrayList<News> sliderList) {
        this.sliderList = sliderList;
    }

    @Override
    public int getType() {
        return 0;
    }

    @Override
    public void bind(HomepageAdapter.HomepageViewHolder holder) {

        RvItemHorizontalRvBinding binding = (RvItemHorizontalRvBinding) holder.binding;

        binding.recyclerViewHorizontal.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext(),
                RecyclerView.HORIZONTAL, false));
        binding.recyclerViewHorizontal.setAdapter(new SliderAdapter(sliderList));

        if (binding.recyclerViewHorizontal.getOnFlingListener() == null) {
            pagerSnapHelper.attachToRecyclerView(binding.recyclerViewHorizontal);
            binding.indicator.attachToRecyclerView(binding.recyclerViewHorizontal, pagerSnapHelper);
        }
    }
}
