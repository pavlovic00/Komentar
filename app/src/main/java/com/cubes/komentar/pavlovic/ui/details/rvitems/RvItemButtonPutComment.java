package com.cubes.komentar.pavlovic.ui.details.rvitems;

import com.cubes.komentar.databinding.RvItemButtonCommentBinding;
import com.cubes.komentar.pavlovic.data.source.response.ResponseDetail;
import com.cubes.komentar.pavlovic.ui.details.DetailNewsAdapter;
import com.cubes.komentar.pavlovic.ui.tools.NewsDetailListener;

public class RvItemButtonPutComment implements RecyclerViewItemDetail {

    private final ResponseDetail.ResponseDetailData data;
    private final NewsDetailListener putCommentListener;


    public RvItemButtonPutComment(ResponseDetail.ResponseDetailData data, NewsDetailListener putCommentListener) {
        this.data = data;
        this.putCommentListener = putCommentListener;
    }

    @Override
    public int getType() {
        return 4;
    }

    @Override
    public void bind(DetailNewsAdapter.DetailNewsViewHolder holder) {

        RvItemButtonCommentBinding binding = (RvItemButtonCommentBinding) holder.binding;

        binding.buttonComment.setOnClickListener(view -> putCommentListener.onPutCommentClicked(data));
    }
}
