package com.cubes.komentar.pavlovic.ui.details.rvitems;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.cubes.komentar.databinding.RvItemCommentParentBinding;

import com.cubes.komentar.pavlovic.data.repository.DataRepository;
import com.cubes.komentar.pavlovic.data.response.ResponseComment;
import com.cubes.komentar.pavlovic.ui.comments.ReplyCommentActivity;
import com.cubes.komentar.pavlovic.ui.details.DetailNewsAdapter;

public class RvItemComment implements RecyclerViewItemDetail {

    ResponseComment.Comment comment;


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
        binding.disLike.setText(dislike + "");


        binding.imageViewLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataRepository.getInstance().voteComment(String.valueOf(Integer.parseInt(comment.id)), true, new DataRepository.VoteCommentListener() {
                    @Override
                    public void onResponse(ResponseComment response) {
                        like++;
                        binding.like.setText((like) + "");
                        Toast.makeText(view.getContext().getApplicationContext(), "Bravo za LAJK!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });
            }
        });
        binding.imageViewDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataRepository.getInstance().unVoteComment(String.valueOf(Integer.parseInt(comment.id)), true, new DataRepository.VoteCommentListener() {
                    @Override
                    public void onResponse(ResponseComment response) {
                        dislike++;
                        binding.disLike.setText((dislike) + "");
                        Toast.makeText(view.getContext().getApplicationContext(), "Bravo za DISLAJK!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });
            }
        });
        binding.buttonReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyIntent = new Intent(view.getContext(), ReplyCommentActivity.class);
                replyIntent.putExtra("reply_id", comment.id);
                replyIntent.putExtra("news", comment.news);
                replyIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(replyIntent);
            }
        });
    }
}
