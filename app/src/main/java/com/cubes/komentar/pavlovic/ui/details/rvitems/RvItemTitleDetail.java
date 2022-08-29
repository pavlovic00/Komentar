package com.cubes.komentar.pavlovic.ui.details.rvitems;

import com.cubes.komentar.R;
import com.cubes.komentar.databinding.RvItemHorizontalTextViewLongBinding;
import com.cubes.komentar.pavlovic.ui.details.DetailAdapter;

public class RvItemTitleDetail implements RecyclerViewItemDetail {

    private final String title;


    public RvItemTitleDetail(String title) {
        this.title = title;
    }

    @Override
    public int getType() {
        return R.layout.rv_item_horizontal_text_view_long;
    }

    @Override
    public void bind(DetailAdapter.ViewHolder holder) {

        RvItemHorizontalTextViewLongBinding binding = (RvItemHorizontalTextViewLongBinding) holder.binding;
        binding.textViewTitle.setText(title);
    }
}
