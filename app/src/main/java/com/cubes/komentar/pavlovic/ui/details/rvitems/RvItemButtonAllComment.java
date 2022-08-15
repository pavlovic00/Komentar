package com.cubes.komentar.pavlovic.ui.details.rvitems;

import com.cubes.komentar.databinding.RvItemButtonAllCommentBinding;
import com.cubes.komentar.pavlovic.data.source.response.ResponseDetail;
import com.cubes.komentar.pavlovic.ui.details.DetailNewsAdapter;
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
        return 6;
    }

    @Override
    public void bind(DetailNewsAdapter.DetailNewsViewHolder holder) {


        RvItemButtonAllCommentBinding binding = (RvItemButtonAllCommentBinding) holder.binding;
        binding.commentCount.setText("" + data.comments_count);

        binding.buttonAllComment.setOnClickListener(view -> allCommentListener.onAllCommentClicked(data));
    }
}
