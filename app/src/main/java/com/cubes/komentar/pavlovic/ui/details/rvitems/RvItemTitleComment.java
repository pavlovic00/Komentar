package com.cubes.komentar.pavlovic.ui.details.rvitems;

import android.view.View;

import com.cubes.komentar.R;
import com.cubes.komentar.databinding.RvItemHorizontalTextViewCommentBinding;
import com.cubes.komentar.pavlovic.data.model.NewsDetailApi;
import com.cubes.komentar.pavlovic.ui.details.NewsDetailAdapter;

public class RvItemTitleComment implements RecyclerViewItemDetail {

    private final String title;
    private final NewsDetailApi data;


    public RvItemTitleComment(String title, NewsDetailApi data) {
        this.title = title;
        this.data = data;
    }

    @Override
    public int getType() {
        return R.layout.rv_item_horizontal_text_view_comment;
    }

    @Override
    public void bind(NewsDetailAdapter.ViewHolder holder) {

        String count = "(" + data.comments_count + ")";

        RvItemHorizontalTextViewCommentBinding binding = (RvItemHorizontalTextViewCommentBinding) holder.binding;
        binding.textViewTitle.setText(title);
        binding.commentCount.setText(count);

        if (data.comments_top_n.size() == 0) {
            binding.obavestenje.setVisibility(View.VISIBLE);
        }
    }
}
