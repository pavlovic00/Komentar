package com.cubes.komentar.pavlovic.ui.details.rvitems;

import com.cubes.komentar.databinding.RvItemHorizontalTextViewBinding;
import com.cubes.komentar.pavlovic.ui.details.DetailNewsAdapter;

public class RvItemTitleRelatedNews implements RecyclerViewItemDetail {

    private final String title;


    public RvItemTitleRelatedNews(String title) {
        this.title = title;
    }

    @Override
    public int getType() {
        return 7;
    }

    @Override
    public void bind(DetailNewsAdapter.DetailNewsViewHolder holder) {

        RvItemHorizontalTextViewBinding binding = (RvItemHorizontalTextViewBinding) holder.binding;
        binding.textViewTitle.setText(title);
    }
}
