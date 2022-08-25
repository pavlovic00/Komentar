package com.cubes.komentar.pavlovic.ui.main.home.homepage.rvitems;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.cubes.komentar.R;
import com.cubes.komentar.databinding.RvItemHorizontalRv2Binding;
import com.cubes.komentar.pavlovic.data.domain.News;
import com.cubes.komentar.pavlovic.ui.main.home.homepage.HomepageAdapter;
import com.cubes.komentar.pavlovic.ui.main.home.homepage.SliderAdapter;
import com.cubes.komentar.pavlovic.ui.tools.listener.NewsListener;

import java.util.ArrayList;

public class RvItemEditorChoice implements RecyclerViewItemHomepage {

    private final ArrayList<News> editorsChoiceList;
    private final PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
    private final NewsListener newsListener;


    public RvItemEditorChoice(ArrayList<News> editorsChoiceList, NewsListener newsListener) {
        this.editorsChoiceList = editorsChoiceList;
        this.newsListener = newsListener;
    }

    @Override
    public int getType() {
        return R.layout.rv_item_horizontal_rv2;
    }

    @Override
    public void bind(HomepageAdapter.ViewHolder holder) {

        RvItemHorizontalRv2Binding binding = (RvItemHorizontalRv2Binding) holder.binding;

        binding.recyclerViewHorizontal2.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext(),
                RecyclerView.HORIZONTAL, false));
        binding.recyclerViewHorizontal2.setAdapter(new SliderAdapter(editorsChoiceList, newsListener));


        if (binding.recyclerViewHorizontal2.getOnFlingListener() == null) {
            pagerSnapHelper.attachToRecyclerView(binding.recyclerViewHorizontal2);
            binding.indicator.attachToRecyclerView(binding.recyclerViewHorizontal2, pagerSnapHelper);
        }
    }
}
