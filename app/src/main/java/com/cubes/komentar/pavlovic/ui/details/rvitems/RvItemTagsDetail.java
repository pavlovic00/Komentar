package com.cubes.komentar.pavlovic.ui.details.rvitems;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.cubes.komentar.databinding.RvItemGridRvBinding;
import com.cubes.komentar.pavlovic.data.model.Tags;
import com.cubes.komentar.pavlovic.ui.details.DetailNewsAdapter;
import com.cubes.komentar.pavlovic.ui.tag.TagsAdapter;

import java.util.ArrayList;

public class RvItemTagsDetail implements RecyclerViewItemDetail{

    private ArrayList<Tags> tagsList;
    private Context context;

    public RvItemTagsDetail(ArrayList<Tags> tagsList,Context context) {
        this.tagsList = tagsList;
        this.context = context;
    }


    @Override
    public int getType() {
        return 3;
    }

    @Override
    public void bind(DetailNewsAdapter.DetailNewsViewHolder holder) {

        RvItemGridRvBinding binding = (RvItemGridRvBinding) holder.binding;

        StaggeredGridLayoutManager grid = new StaggeredGridLayoutManager(3, LinearLayoutManager.HORIZONTAL);
        binding.recyclerViewGrid.setLayoutManager(grid);
        binding.recyclerViewGrid.setAdapter(new TagsAdapter(tagsList,context));
    }
}
