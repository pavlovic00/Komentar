package com.cubes.komentar.pavlovic.ui.details.rvitems;

import android.content.Intent;

import com.cubes.komentar.databinding.RvItemButtonCommentBinding;
import com.cubes.komentar.pavlovic.data.source.response.ResponseDetail;
import com.cubes.komentar.pavlovic.ui.comments.PostCommentActivity;
import com.cubes.komentar.pavlovic.ui.details.DetailNewsAdapter;

public class RvItemButtonPutComment implements RecyclerViewItemDetail {

    private final ResponseDetail.ResponseDetailData data;


    public RvItemButtonPutComment(ResponseDetail.ResponseDetailData data) {
        this.data = data;
    }

    @Override
    public int getType() {
        return 4;
    }

    @Override
    public void bind(DetailNewsAdapter.DetailNewsViewHolder holder) {

        RvItemButtonCommentBinding binding = (RvItemButtonCommentBinding) holder.binding;

        binding.buttonComment.setOnClickListener(view -> {
            Intent i = new Intent(view.getContext(), PostCommentActivity.class);
            i.putExtra("id", data.id);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            view.getContext().startActivity(i);
        });
    }
}
