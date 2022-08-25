package com.cubes.komentar.pavlovic.ui.details.rvitems;

import android.widget.Toast;

import com.cubes.komentar.R;
import com.cubes.komentar.databinding.RvItemCommentParentBinding;
import com.cubes.komentar.pavlovic.data.domain.Comment;
import com.cubes.komentar.pavlovic.ui.details.NewsDetailAdapter;
import com.cubes.komentar.pavlovic.ui.tools.listener.NewsDetailListener;

public class RvItemComment implements RecyclerViewItemDetail {

    private final Comment comment;
    private final NewsDetailListener commentListener;
    private RvItemCommentParentBinding binding;


    public RvItemComment(Comment comment, NewsDetailListener commentListener) {
        this.comment = comment;
        this.commentListener = commentListener;
    }

    @Override
    public int getType() {
        return R.layout.rv_item_comment_parent;
    }

    @Override
    public void bind(NewsDetailAdapter.ViewHolder holder) {

        binding = (RvItemCommentParentBinding) holder.binding;

        String like = comment.dislikes + "";
        String dislike = comment.likes + "";

        binding.person.setText(comment.name);
        binding.date.setText(comment.createdAt);
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
        binding.like.setText(String.valueOf(comment.dislikes + 1));
        binding.imageViewLike.setImageResource(R.drawable.ic_like_vote);
        binding.likeCircle.setBackgroundResource(R.drawable.button_circle_background_like);
    }

    @Override
    public void updateDislike() {
        binding.dislike.setText(String.valueOf(comment.likes + 1));
        binding.imageViewDislike.setImageResource(R.drawable.ic_dislike_vote);
        binding.dislikeCircle.setBackgroundResource(R.drawable.button_circle_background_dislike);
    }
}
