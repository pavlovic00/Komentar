package com.cubes.komentar.pavlovic.ui.main.home.homepage.rvitems;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.cubes.komentar.databinding.RvItemHorizontalRv2Binding;
import com.cubes.komentar.pavlovic.data.model.News;
import com.cubes.komentar.pavlovic.ui.main.home.homepage.HomepageAdapter;
import com.cubes.komentar.pavlovic.ui.main.home.homepage.SliderAdapter;

import java.util.ArrayList;

public class RvItemEditorChoise implements RecyclerViewItemHomepage {

    public ArrayList<News> editorsChoiceList;


    public RvItemEditorChoise(ArrayList<News> editorsChoiceList) {
        this.editorsChoiceList = editorsChoiceList;
    }

    @Override
    public int getType() {
        return 4;
    }

    @Override
    public void bind(HomepageAdapter.HomepageViewHolder holder) {

        RvItemHorizontalRv2Binding binding = (RvItemHorizontalRv2Binding) holder.binding;

        binding.recyclerViewHorizontal2.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext(),
                RecyclerView.HORIZONTAL, false));
        binding.recyclerViewHorizontal2.setAdapter(new SliderAdapter(editorsChoiceList));

        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(binding.recyclerViewHorizontal2);


        binding.indicator.attachToRecyclerView(binding.recyclerViewHorizontal2, pagerSnapHelper);
    }
}
