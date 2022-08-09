package com.cubes.komentar.pavlovic.ui.details.rvitems;

import android.content.Intent;

import com.cubes.komentar.databinding.RvItemButtonAllCommentBinding;
import com.cubes.komentar.pavlovic.data.source.response.ResponseDetail;
import com.cubes.komentar.pavlovic.ui.comments.AllCommentActivity;
import com.cubes.komentar.pavlovic.ui.details.DetailNewsAdapter;

public class RvItemButtonAllComment implements RecyclerViewItemDetail {

    private final ResponseDetail.ResponseDetailData data;


    public RvItemButtonAllComment(ResponseDetail.ResponseDetailData data) {
        this.data = data;
    }

    @Override
    public int getType() {
        return 6;
    }

    @Override
    public void bind(DetailNewsAdapter.DetailNewsViewHolder holder) {


        RvItemButtonAllCommentBinding binding = (RvItemButtonAllCommentBinding) holder.binding;
        binding.commentCount.setText("" + data.comments_count);

        binding.buttonAllComment.setOnClickListener(view -> {
            Intent commentIntent = new Intent(view.getContext(), AllCommentActivity.class);
            commentIntent.putExtra("id", data.id);
            view.getContext().startActivity(commentIntent);
        });

    }
}
