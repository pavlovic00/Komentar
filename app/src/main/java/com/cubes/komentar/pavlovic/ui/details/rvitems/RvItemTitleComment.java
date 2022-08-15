package com.cubes.komentar.pavlovic.ui.details.rvitems;

import android.view.View;

import com.cubes.komentar.databinding.RvItemHorizontalTextViewCommentBinding;
import com.cubes.komentar.pavlovic.data.source.response.ResponseDetail;
import com.cubes.komentar.pavlovic.ui.details.DetailNewsAdapter;

public class RvItemTitleComment implements RecyclerViewItemDetail {

    private final String title;
    private final ResponseDetail.ResponseDetailData data;


    public RvItemTitleComment(String title, ResponseDetail.ResponseDetailData data) {
        this.title = title;
        this.data = data;
    }

    @Override
    public int getType() {
        return 3;
    }

    @Override
    public void bind(DetailNewsAdapter.DetailNewsViewHolder holder) {

        RvItemHorizontalTextViewCommentBinding binding = (RvItemHorizontalTextViewCommentBinding) holder.binding;
        binding.textViewTitle.setText(title);
        binding.commentCount.setText("(" + data.comments_count + ")");

        if (data.comments_top_n.size() == 0) {
            binding.upozorenje.setVisibility(View.VISIBLE);
        }
    }
}
