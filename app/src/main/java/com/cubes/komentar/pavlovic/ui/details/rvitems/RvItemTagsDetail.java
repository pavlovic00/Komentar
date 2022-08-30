package com.cubes.komentar.pavlovic.ui.details.rvitems;

import com.cubes.komentar.R;
import com.cubes.komentar.databinding.RvItemGridRvBinding;
import com.cubes.komentar.pavlovic.data.domain.Tags;
import com.cubes.komentar.pavlovic.ui.details.ButtonTagsAdapter;
import com.cubes.komentar.pavlovic.ui.details.DetailsAdapter;
import com.cubes.komentar.pavlovic.ui.tools.listener.DetailsListener;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.ArrayList;

public class RvItemTagsDetail implements RecyclerViewItemDetail {

    private final ArrayList<Tags> tagsList;
    private final DetailsListener tagListener;


    public RvItemTagsDetail(ArrayList<Tags> tagsList, DetailsListener tagListener) {
        this.tagsList = tagsList;
        this.tagListener = tagListener;
    }

    @Override
    public int getType() {
        return R.layout.rv_item_grid_rv;
    }

    @Override
    public void bind(DetailsAdapter.ViewHolder holder) {

        RvItemGridRvBinding binding = (RvItemGridRvBinding) holder.binding;

        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(binding.getRoot().getContext());
        binding.recyclerViewGrid.setLayoutManager(flexboxLayoutManager);
        binding.recyclerViewGrid.setAdapter(new ButtonTagsAdapter(tagsList, tagListener));
    }
}
