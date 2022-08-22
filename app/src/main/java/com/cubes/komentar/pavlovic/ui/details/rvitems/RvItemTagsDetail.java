package com.cubes.komentar.pavlovic.ui.details.rvitems;

import com.cubes.komentar.R;
import com.cubes.komentar.databinding.RvItemGridRvBinding;
import com.cubes.komentar.pavlovic.data.model.TagsApi;
import com.cubes.komentar.pavlovic.ui.details.ButtonTagsAdapter;
import com.cubes.komentar.pavlovic.ui.details.NewsDetailAdapter;
import com.cubes.komentar.pavlovic.ui.tools.NewsDetailListener;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.ArrayList;

public class RvItemTagsDetail implements RecyclerViewItemDetail {

    private final ArrayList<TagsApi> tagsList;
    private final NewsDetailListener tagListener;


    public RvItemTagsDetail(ArrayList<TagsApi> tagsList, NewsDetailListener tagListener) {
        this.tagsList = tagsList;
        this.tagListener = tagListener;
    }

    @Override
    public int getType() {
        return R.layout.rv_item_grid_rv;
    }

    @Override
    public void bind(NewsDetailAdapter.ViewHolder holder) {

        RvItemGridRvBinding binding = (RvItemGridRvBinding) holder.binding;

        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(binding.getRoot().getContext());
        binding.recyclerViewGrid.setLayoutManager(flexboxLayoutManager);
        binding.recyclerViewGrid.setAdapter(new ButtonTagsAdapter(tagsList, tagListener));
    }
}
