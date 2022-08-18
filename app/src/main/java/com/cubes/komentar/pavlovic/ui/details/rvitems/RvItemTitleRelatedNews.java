package com.cubes.komentar.pavlovic.ui.details.rvitems;

import com.cubes.komentar.R;
import com.cubes.komentar.databinding.RvItemHorizontalTextViewBinding;
import com.cubes.komentar.pavlovic.ui.details.NewsDetailAdapter;

public class RvItemTitleRelatedNews implements RecyclerViewItemDetail {

    private final String title;


    public RvItemTitleRelatedNews(String title) {
        this.title = title;
    }

    @Override
    public int getType() {
        return R.layout.rv_item_horizontal_text_view;
    }

    @Override
    public void bind(NewsDetailAdapter.ViewHolder holder) {

        RvItemHorizontalTextViewBinding binding = (RvItemHorizontalTextViewBinding) holder.binding;
        binding.textViewTitle.setText(title);
    }
}
