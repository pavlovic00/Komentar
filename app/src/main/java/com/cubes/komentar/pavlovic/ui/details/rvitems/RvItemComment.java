package com.cubes.komentar.pavlovic.ui.details.rvitems;

import android.view.View;
import android.widget.Toast;

import com.cubes.komentar.R;
import com.cubes.komentar.databinding.RvItemCommentBinding;
import com.cubes.komentar.pavlovic.data.source.response.ResponseComment;
import com.cubes.komentar.pavlovic.ui.details.DetailNewsAdapter;
import com.cubes.komentar.pavlovic.ui.tools.CommentListener;

public class RvItemComment implements RecyclerViewItemDetail {

    private final ResponseComment.Comment comment;
    private final CommentListener commentListener;


    public RvItemComment(ResponseComment.Comment comment, CommentListener commentListener) {
        this.comment = comment;
        this.commentListener = commentListener;
    }

    @Override
    public int getType() {
        return 5;
    }

    @Override
    public void bind(DetailNewsAdapter.DetailNewsViewHolder holder) {

        RvItemCommentBinding binding = (RvItemCommentBinding) holder.binding;

        binding.person.setText(comment.name);
        binding.date.setText(comment.created_at);
        binding.content.setText(comment.content);
        binding.like.setText(comment.positive_votes + "");
        binding.dislike.setText(comment.negative_votes + "");

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
