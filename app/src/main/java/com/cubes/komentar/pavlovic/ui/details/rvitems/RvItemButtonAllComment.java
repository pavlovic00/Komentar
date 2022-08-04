package com.cubes.komentar.pavlovic.ui.details.rvitems;

import android.content.Intent;
import android.view.View;

import com.cubes.komentar.databinding.RvItemButtonAllCommentBinding;
import com.cubes.komentar.pavlovic.data.response.ResponseDetail;
import com.cubes.komentar.pavlovic.ui.details.DetailNewsAdapter;
import com.cubes.komentar.pavlovic.ui.comments.AllCommentActivity;

public class RvItemButtonAllComment implements RecyclerViewItemDetail {

    private ResponseDetail.ResponseDetailData data;


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

        binding.buttonAllComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent commentIntent = new Intent(view.getContext(), AllCommentActivity.class);
                commentIntent.putExtra("id",data.id);
                view.getContext().startActivity(commentIntent);
            }
        });

    }
}
