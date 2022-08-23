package com.cubes.komentar.pavlovic.ui.details.rvitems;

import com.cubes.komentar.R;
import com.cubes.komentar.databinding.RvItemButtonCommentBinding;
import com.cubes.komentar.pavlovic.data.domain.NewsDetail;
import com.cubes.komentar.pavlovic.ui.details.NewsDetailAdapter;
import com.cubes.komentar.pavlovic.ui.tools.NewsDetailListener;

public class RvItemButtonPutComment implements RecyclerViewItemDetail {

    private final NewsDetail data;
    private final NewsDetailListener putCommentListener;


    public RvItemButtonPutComment(NewsDetail data, NewsDetailListener putCommentListener) {
        this.data = data;
        this.putCommentListener = putCommentListener;
    }

    @Override
    public int getType() {
        return R.layout.rv_item_button_comment;
    }

    @Override
    public void bind(NewsDetailAdapter.ViewHolder holder) {

        RvItemButtonCommentBinding binding = (RvItemButtonCommentBinding) holder.binding;

        binding.buttonComment.setOnClickListener(view -> putCommentListener.onPutCommentClicked(data));
    }
}
