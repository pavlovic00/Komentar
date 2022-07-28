package com.cubes.komentar.pavlovic.ui.details.rvitems;

import com.cubes.komentar.databinding.RvItemHorizontalTextViewBinding;
import com.cubes.komentar.pavlovic.ui.details.DetailNewsAdapter;

public class RvItemTitleRelatedNews implements RecyclerViewItemDetail{

    private String title;

    public RvItemTitleRelatedNews(String title) {
        this.title = title;
    }

    @Override
    public int getType() {
        return 8;
    }

    @Override
    public void bind(DetailNewsAdapter.DetailNewsViewHolder holder) {

        RvItemHorizontalTextViewBinding binding = (RvItemHorizontalTextViewBinding) holder.binding;
        binding.textViewTitle.setText(title);
    }
}
