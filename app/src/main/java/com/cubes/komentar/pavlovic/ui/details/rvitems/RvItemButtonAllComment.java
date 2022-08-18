package com.cubes.komentar.pavlovic.ui.details.rvitems;

import com.cubes.komentar.R;
import com.cubes.komentar.databinding.RvItemButtonAllCommentBinding;
import com.cubes.komentar.pavlovic.data.source.response.ResponseDetail;
import com.cubes.komentar.pavlovic.ui.details.NewsDetailAdapter;
import com.cubes.komentar.pavlovic.ui.tools.NewsDetailListener;

public class RvItemButtonAllComment implements RecyclerViewItemDetail {

    private final ResponseDetail.ResponseDetailData data;
    private final NewsDetailListener allCommentListener;


    public RvItemButtonAllComment(ResponseDetail.ResponseDetailData data, NewsDetailListener allCommentListener) {
        this.data = data;
        this.allCommentListener = allCommentListener;
    }

    @Override
    public int getType() {
        return R.layout.rv_item_button_all_comment;
    }

    @Override
    public void bind(NewsDetailAdapter.ViewHolder holder) {

        String count = data.comments_count + "";

        RvItemButtonAllCommentBinding binding = (RvItemButtonAllCommentBinding) holder.binding;
        binding.commentCount.setText(count);

        binding.buttonAllComment.setOnClickListener(view -> allCommentListener.onAllCommentClicked(data));
    }
}
