package com.cubes.komentar.pavlovic.ui.details.rvitems;

import com.cubes.komentar.R;
import com.cubes.komentar.databinding.RvItemButtonAllCommentBinding;
import com.cubes.komentar.pavlovic.data.domain.NewsDetail;
import com.cubes.komentar.pavlovic.ui.details.DetailsAdapter;
import com.cubes.komentar.pavlovic.ui.tools.listener.NewsDetailListener;

public class RvItemButtonAllComment implements RecyclerViewItemDetail {

    private final NewsDetail data;
    private final NewsDetailListener allCommentListener;


    public RvItemButtonAllComment(NewsDetail data, NewsDetailListener allCommentListener) {
        this.data = data;
        this.allCommentListener = allCommentListener;
    }

    @Override
    public int getType() {
        return R.layout.rv_item_button_all_comment;
    }

    @Override
    public void bind(DetailsAdapter.ViewHolder holder) {

        String count = data.commentsCount + "";

        RvItemButtonAllCommentBinding binding = (RvItemButtonAllCommentBinding) holder.binding;
        binding.commentCount.setText(count);

        binding.buttonAllComment.setOnClickListener(view -> allCommentListener.onAllCommentClicked(data));
    }
}
