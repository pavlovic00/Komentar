package com.cubes.komentar.pavlovic.ui.comments;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.cubes.komentar.databinding.RvItemCommentBinding;

import com.cubes.komentar.pavlovic.data.repository.DataRepository;
import com.cubes.komentar.pavlovic.data.response.ResponseComment;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private ArrayList<ResponseComment.ResponseCommentData> dataList;
    private int like;
    private int dislike;


    public CommentAdapter(ArrayList<ResponseComment.ResponseCommentData> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public CommentAdapter.CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RvItemCommentBinding binding = RvItemCommentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new CommentAdapter.CommentViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.CommentViewHolder holder, int position) {

        ResponseComment.ResponseCommentData comment = dataList.get(position);

        like = comment.positive_votes;
        dislike = comment.negative_votes;

        holder.binding.textViewPerson.setText(comment.name);
        holder.binding.textViewDate.setText(comment.created_at);
        holder.binding.textViewContent.setText(comment.content);
        holder.binding.textViewLike.setText(like + "");
        holder.binding.textViewDissLike.setText(dislike + "");

        holder.binding.imageViewLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataRepository.getInstance().voteComment(String.valueOf(Integer.parseInt(comment.id)), true, new DataRepository.VoteCommentListener() {
                    @Override
                    public void onResponse(ResponseComment response) {
                        like++;
                        holder.binding.textViewLike.setText((like) + "");
                        Toast.makeText(view.getContext().getApplicationContext(), "Bravo za LAJK!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });
            }
        });
        holder.binding.imageViewDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataRepository.getInstance().unVoteComment(String.valueOf(Integer.parseInt(comment.id)), true, new DataRepository.VoteCommentListener() {
                    @Override
                    public void onResponse(ResponseComment response) {
                        dislike++;
                        holder.binding.textViewDissLike.setText((dislike) + "");
                        Toast.makeText(view.getContext().getApplicationContext(), "Bravo za DISLAJK!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });
            }
        });
        holder.binding.buttonReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyIntent = new Intent(view.getContext(), ReplyCommentActivity.class);
                replyIntent.putExtra("reply_id", comment.id);
                replyIntent.putExtra("news", comment.news);
                replyIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(replyIntent);
            }
        });

        if (comment.children != null && comment.children.size() > 0) {

            holder.binding.recyclerViewChildren.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
            holder.binding.recyclerViewChildren.setAdapter(new CommentAdapter(comment.children));

            if (comment.parent_comment != "0") {
                setMargins(holder.binding.recyclerViewChildren, 80, 0, 0, 0);
            }

            holder.binding.imageViewLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DataRepository.getInstance().voteComment(String.valueOf(Integer.parseInt(comment.id)), true, new DataRepository.VoteCommentListener() {
                        @Override
                        public void onResponse(ResponseComment response) {
                            like++;
                            holder.binding.textViewLike.setText((like) + "");
                            Toast.makeText(view.getContext().getApplicationContext(), "Bravo za LAJK!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Throwable t) {

                        }
                    });
                }
            });
            holder.binding.imageViewDislike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DataRepository.getInstance().unVoteComment(String.valueOf(Integer.parseInt(comment.id)), true, new DataRepository.VoteCommentListener() {
                        @Override
                        public void onResponse(ResponseComment response) {
                            dislike++;
                            holder.binding.textViewDissLike.setText((dislike) + "");
                            Toast.makeText(view.getContext().getApplicationContext(), "Bravo za DISLAJK!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Throwable t) {

                        }
                    });
                }
            });
            holder.binding.buttonReply.setOnClickListener(new View.OnClickListener() {
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

    private void setMargins(RecyclerView view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {

        private RvItemCommentBinding binding;

        public CommentViewHolder(RvItemCommentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
