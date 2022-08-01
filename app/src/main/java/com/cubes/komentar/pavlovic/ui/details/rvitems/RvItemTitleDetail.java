package com.cubes.komentar.pavlovic.ui.details.rvitems;

import com.cubes.komentar.databinding.RvItemHorizontalTextViewLongBinding;
import com.cubes.komentar.pavlovic.ui.details.DetailNewsAdapter;

public class RvItemTitleDetail implements RecyclerViewItemDetail {

    private String title;

    public RvItemTitleDetail(String title) {
        this.title = title;
    }

    @Override
    public int getType() {
        return 1;
    }

    @Override
    public void bind(DetailNewsAdapter.DetailNewsViewHolder holder) {

        RvItemHorizontalTextViewLongBinding binding = (RvItemHorizontalTextViewLongBinding) holder.binding;
        binding.textViewTitle.setText(title);
    }
}
