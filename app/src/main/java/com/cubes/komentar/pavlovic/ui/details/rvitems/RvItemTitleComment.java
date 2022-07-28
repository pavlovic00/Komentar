package com.cubes.komentar.pavlovic.ui.details.rvitems;

import com.cubes.komentar.databinding.RvItemHorizontalTextViewCommentBinding;
import com.cubes.komentar.pavlovic.data.response.responsedetail.ResponseDetailData;
import com.cubes.komentar.pavlovic.ui.details.DetailNewsAdapter;

public class RvItemTitleComment implements RecyclerViewItemDetail{

    private String title;
    private ResponseDetailData data;

    public RvItemTitleComment(String title,ResponseDetailData data) {
        this.title = title;
        this.data = data;
    }

    @Override
    public int getType() {
        return 4;
    }

    @Override
    public void bind(DetailNewsAdapter.DetailNewsViewHolder holder) {

        RvItemHorizontalTextViewCommentBinding binding = (RvItemHorizontalTextViewCommentBinding) holder.binding;
        binding.textViewTitle.setText(title);
        binding.commentCount.setText("("+data.comments_count+")");

    }
}
