package com.cubes.komentar.pavlovic.ui.details.rvitems;

import android.view.View;

import com.cubes.komentar.R;
import com.cubes.komentar.databinding.RvItemHorizontalTextViewCommentBinding;
import com.cubes.komentar.pavlovic.data.domain.NewsDetail;
import com.cubes.komentar.pavlovic.ui.details.DetailAdapter;

public class RvItemTitleComment implements RecyclerViewItemDetail {

    private final String title;
    private final NewsDetail data;


    public RvItemTitleComment(String title, NewsDetail data) {
        this.title = title;
        this.data = data;
    }

    @Override
    public int getType() {
        return R.layout.rv_item_horizontal_text_view_comment;
    }

    @Override
    public void bind(DetailAdapter.ViewHolder holder) {

        String count = "(" + data.commentsCount + ")";

        RvItemHorizontalTextViewCommentBinding binding = (RvItemHorizontalTextViewCommentBinding) holder.binding;
        binding.textViewTitle.setText(title);
        binding.commentCount.setText(count);

        if (data.topComments.size() == 0) {
            binding.obavestenje.setVisibility(View.VISIBLE);
        }
    }
}
