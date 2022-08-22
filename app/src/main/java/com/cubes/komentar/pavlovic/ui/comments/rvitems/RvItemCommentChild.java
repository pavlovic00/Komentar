package com.cubes.komentar.pavlovic.ui.comments.rvitems;

import android.widget.Toast;

import com.cubes.komentar.R;
import com.cubes.komentar.databinding.RvItemCommentChildBinding;
import com.cubes.komentar.pavlovic.data.model.CommentApi;
import com.cubes.komentar.pavlovic.ui.comments.CommentAdapter;
import com.cubes.komentar.pavlovic.ui.tools.CommentListener;

public class RvItemCommentChild implements RecyclerViewItemComment {

    private final CommentApi comment;
    private final CommentListener commentListener;
    private RvItemCommentChildBinding binding;


    public RvItemCommentChild(CommentApi comment, CommentListener commentListener) {
        this.comment = comment;
        this.commentListener = commentListener;
    }

    @Override
    public int getType() {
        return R.layout.rv_item_comment_child;
    }

    @Override
    public void bind(CommentAdapter.ViewHolder holder) {

        binding = (RvItemCommentChildBinding) holder.binding;

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
                binding.likeCircle.setBackgroundResource(R.drawable.button_circle_background_like);
            } else {
                binding.imageViewDislike.setImageResource(R.drawable.ic_dislike_vote);
                binding.dislikeCircle.setBackgroundResource(R.drawable.button_circle_background_dislike);
            }
        }

        binding.imageViewLike.setOnClickListener(view -> {
            if (comment.vote == null) {
                commentListener.like(comment);

            } else {
                Toast.makeText(view.getContext().getApplicationContext(), "Već ste glasali!", Toast.LENGTH_SHORT).show();
            }
            binding.imageViewLike.setEnabled(false);
            binding.imageViewDislike.setEnabled(false);
        });
        binding.imageViewDislike.setOnClickListener(view -> {
            if (comment.vote == null) {
                commentListener.dislike(comment);

            } else {
                Toast.makeText(view.getContext().getApplicationContext(), "Već ste glasali!", Toast.LENGTH_SHORT).show();
            }
            binding.imageViewLike.setEnabled(false);
            binding.imageViewDislike.setEnabled(false);
        });
        binding.buttonReply.setOnClickListener(view -> commentListener.onCommentClicked(comment));
    }

    @Override
    public String getCommentsId() {
        return comment.id;
    }

    @Override
    public void updateLike() {
        binding.like.setText(String.valueOf(comment.positive_votes + 1));
        binding.imageViewLike.setImageResource(R.drawable.ic_like_vote);
        binding.likeCircle.setBackgroundResource(R.drawable.button_circle_background_like);
    }

    @Override
    public void updateDislike() {
        binding.dislike.setText(String.valueOf(comment.negative_votes + 1));
        binding.imageViewDislike.setImageResource(R.drawable.ic_dislike_vote);
        binding.dislikeCircle.setBackgroundResource(R.drawable.button_circle_background_dislike);
    }
}
