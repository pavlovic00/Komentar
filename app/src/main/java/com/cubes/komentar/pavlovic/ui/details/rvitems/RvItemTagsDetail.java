package com.cubes.komentar.pavlovic.ui.details.rvitems;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.cubes.komentar.databinding.RvItemGridRvBinding;
import com.cubes.komentar.pavlovic.data.model.Tags;
import com.cubes.komentar.pavlovic.ui.details.ButtonTagsAdapter;
import com.cubes.komentar.pavlovic.ui.details.DetailNewsAdapter;
import com.cubes.komentar.pavlovic.ui.tools.NewsDetailListener;

import java.util.ArrayList;

public class RvItemTagsDetail implements RecyclerViewItemDetail {

    private final ArrayList<Tags> tagsList;
    private final NewsDetailListener tagListener;


    public RvItemTagsDetail(ArrayList<Tags> tagsList, NewsDetailListener tagListener) {
        this.tagsList = tagsList;
        this.tagListener = tagListener;
    }


    @Override
    public int getType() {
        return 2;
    }

    @Override
    public void bind(DetailNewsAdapter.DetailNewsViewHolder holder) {

        RvItemGridRvBinding binding = (RvItemGridRvBinding) holder.binding;

        StaggeredGridLayoutManager grid = new StaggeredGridLayoutManager(3, LinearLayoutManager.HORIZONTAL);
        binding.recyclerViewGrid.setLayoutManager(grid);
        binding.recyclerViewGrid.setAdapter(new ButtonTagsAdapter(tagsList, tagListener));
    }
}
