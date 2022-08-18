package com.cubes.komentar.pavlovic.ui.details.rvitems;

import android.view.View;
import android.widget.Toast;

import com.cubes.komentar.R;
import com.cubes.komentar.databinding.RvItemCommentBinding;
import com.cubes.komentar.pavlovic.data.source.response.ResponseComment;
import com.cubes.komentar.pavlovic.ui.details.NewsDetailAdapter;
import com.cubes.komentar.pavlovic.ui.tools.NewsDetailListener;

public class RvItemComment implements RecyclerViewItemDetail {

    private final ResponseComment.Comment comment;
    private final NewsDetailListener commentListener;


    public RvItemComment(ResponseComment.Comment comment, NewsDetailListener commentListener) {
        this.comment = comment;
        this.commentListener = commentListener;
    }

    @Override
    public int getType() {
        return R.layout.rv_item_comment;
    }

    @Override
    public void bind(NewsDetailAdapter.ViewHolder holder) {

        RvItemCommentBinding binding = (RvItemCommentBinding) holder.binding;

        String like = comment.positive_votes + "";
        String dislike = comment.negative_votes + "";

        binding.person.setText(comment.name);
        binding.date.setText(comment.created_at);
        binding.content.setText(comment.content);
        binding.like.setText(like);
        binding.dislike.setText(dislike);

        if (comment.vote != null) {
            if (comment.vote.vote) {
                binding.imageViewLike.setImageResource(R.drawable.ic_like_vote);
                binding.likeCircle.setVisibility(View.VISIBLE);
            } else {
                binding.imageViewDislike.setImageResource(R.drawable.ic_dislike_vote);
                binding.dislikeCircle.setVisibility(View.VISIBLE);
            }
        }

        binding.imageViewLike.setOnClickListener(view -> {
            if (comment.vote == null) {
                commentListener.like(comment, binding);

            } else {
                Toast.makeText(view.getContext().getApplicationContext(), "Već ste glasali!", Toast.LENGTH_SHORT).show();
            }
            binding.imageViewLike.setEnabled(false);
            binding.imageViewDislike.setEnabled(false);
        });
        binding.imageViewDislike.setOnClickListener(view -> {
            if (comment.vote == null) {
                commentListener.dislike(comment, binding);

            } else {
                Toast.makeText(view.getContext().getApplicationContext(), "Već ste glasali!", Toast.LENGTH_SHORT).show();
            }
            binding.imageViewLike.setEnabled(false);
            binding.imageViewDislike.setEnabled(false);
        });
        binding.buttonReply.setOnClickListener(view -> commentListener.onCommentClicked(comment));
    }
}
