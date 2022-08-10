package com.cubes.komentar.pavlovic.ui.details.rvitems;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.cubes.komentar.R;
import com.cubes.komentar.databinding.RvItemCommentParentBinding;
import com.cubes.komentar.pavlovic.data.source.repository.DataRepository;
import com.cubes.komentar.pavlovic.data.source.response.ResponseComment;
import com.cubes.komentar.pavlovic.ui.comments.ReplyCommentActivity;
import com.cubes.komentar.pavlovic.ui.details.DetailNewsAdapter;

public class RvItemComment implements RecyclerViewItemDetail {

    private final ResponseComment.Comment comment;
    private int like;
    private int dislike;

    public RvItemComment(ResponseComment.Comment comment) {
        this.comment = comment;
    }

    @Override
    public int getType() {
        return 5;
    }

    @Override
    public void bind(DetailNewsAdapter.DetailNewsViewHolder holder) {

        RvItemCommentParentBinding binding = (RvItemCommentParentBinding) holder.binding;

        like = comment.positive_votes;
        dislike = comment.negative_votes;

        binding.person.setText(comment.name);
        binding.date.setText(comment.created_at);
        binding.content.setText(comment.content);
        binding.like.setText(like + "");
        binding.dislike.setText(dislike + "");


        binding.imageViewLike.setOnClickListener(view -> {
            if (!comment.voted) {

                DataRepository.getInstance().voteComment(comment.id, new DataRepository.VoteCommentListener() {
                    @Override
                    public void onResponse(ResponseComment response) {
                        binding.like.setText(String.valueOf(comment.positive_votes + 1));
                        comment.voted = true;
                        binding.imageViewLike.setImageResource(R.drawable.ic_like_vote);
                        binding.likeCircle.setVisibility(View.VISIBLE);
                        Toast.makeText(view.getContext().getApplicationContext(), "Bravo za LAJK!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(view.getContext().getApplicationContext(), "Doslo je do greske!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else {
                Toast.makeText(view.getContext().getApplicationContext(), "Vaš glas je već zabeležen", Toast.LENGTH_SHORT).show();
            }
        });
        binding.imageViewDislike.setOnClickListener(view -> {
            if (!comment.voted) {

                DataRepository.getInstance().unVoteComment(comment.id, new DataRepository.VoteCommentListener() {
                    @Override
                    public void onResponse(ResponseComment response) {
                        binding.dislike.setText(String.valueOf(comment.negative_votes + 1));
                        comment.voted = true;
                        binding.imageViewDislike.setImageResource(R.drawable.ic_dislike_vote);
                        binding.dislikeCircle.setVisibility(View.INVISIBLE);
                        Toast.makeText(view.getContext().getApplicationContext(), "Bravo za DISLAJK!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(view.getContext().getApplicationContext(), "Doslo je do greske!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else {
                Toast.makeText(view.getContext().getApplicationContext(), "Vaš glas je već zabeležen", Toast.LENGTH_SHORT).show();
            }
        });
        binding.buttonReply.setOnClickListener(view -> {
            Intent replyIntent = new Intent(view.getContext(), ReplyCommentActivity.class);
            replyIntent.putExtra("reply_id", comment.id);
            replyIntent.putExtra("news", comment.news);
            replyIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            view.getContext().startActivity(replyIntent);
        });
    }
}
