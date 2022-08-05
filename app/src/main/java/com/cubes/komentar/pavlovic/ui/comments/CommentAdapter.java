package com.cubes.komentar.pavlovic.ui.comments;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;


import com.cubes.komentar.databinding.RvItemBigBinding;
import com.cubes.komentar.databinding.RvItemCommentParentBinding;

import com.cubes.komentar.databinding.RvItemCommentChildrenBinding;
import com.cubes.komentar.pavlovic.data.repository.DataRepository;
import com.cubes.komentar.pavlovic.data.response.ResponseComment;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private ArrayList<ResponseComment.Comment> allComments = new ArrayList<>();
    private int like;
    private int dislike;

    public CommentAdapter() {
    }

    @NonNull
    @Override
    public CommentAdapter.CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ViewBinding binding;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == 0) {
            binding = RvItemCommentParentBinding.inflate(inflater, parent, false);
        } else {
            binding = RvItemCommentChildrenBinding.inflate(inflater, parent, false);
        }
        return new CommentViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.CommentViewHolder holder, int position) {

        ResponseComment.Comment comment = allComments.get(position);

        like = comment.positive_votes;
        dislike = comment.negative_votes;

        if (allComments.get(position).parent_comment.equals("0")) {

            RvItemCommentParentBinding bindingParent = (RvItemCommentParentBinding) holder.binding;

            bindingParent.person.setText(comment.name);
            bindingParent.date.setText(comment.created_at);
            bindingParent.content.setText(comment.content);
            bindingParent.like.setText(like + "");
            bindingParent.disLike.setText(dislike + "");

            bindingParent.imageViewLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DataRepository.getInstance().voteComment(String.valueOf(Integer.parseInt(comment.id)), true, new DataRepository.VoteCommentListener() {
                        @Override
                        public void onResponse(ResponseComment response) {
                            like++;
                            bindingParent.like.setText((like) + "");
                            Toast.makeText(view.getContext().getApplicationContext(), "Bravo za LAJK!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Throwable t) {

                        }
                    });
                }
            });
            bindingParent.imageViewDislike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DataRepository.getInstance().unVoteComment(String.valueOf(Integer.parseInt(comment.id)), true, new DataRepository.VoteCommentListener() {
                        @Override
                        public void onResponse(ResponseComment response) {
                            dislike++;
                            bindingParent.disLike.setText((dislike) + "");
                            Toast.makeText(view.getContext().getApplicationContext(), "Bravo za DISLAJK!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Throwable t) {

                        }
                    });
                }
            });
            bindingParent.buttonReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent replyIntent = new Intent(view.getContext(), ReplyCommentActivity.class);
                    replyIntent.putExtra("reply_id", comment.id);
                    replyIntent.putExtra("news", comment.news);
                    replyIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    view.getContext().startActivity(replyIntent);
                }
            });
        } else {

            RvItemCommentChildrenBinding bindingChildren = (RvItemCommentChildrenBinding) holder.binding;

            bindingChildren.person.setText(comment.name);
            bindingChildren.date.setText(comment.created_at);
            bindingChildren.content.setText(comment.content);
            bindingChildren.like.setText(like + "");
            bindingChildren.disLike.setText(dislike + "");

            bindingChildren.imageViewLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DataRepository.getInstance().voteComment(String.valueOf(Integer.parseInt(comment.id)), true, new DataRepository.VoteCommentListener() {
                        @Override
                        public void onResponse(ResponseComment response) {
                            like++;
                            bindingChildren.like.setText((like) + "");
                            Toast.makeText(view.getContext().getApplicationContext(), "Bravo za LAJK!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Throwable t) {

                        }
                    });
                }
            });
            bindingChildren.imageViewDislike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DataRepository.getInstance().unVoteComment(String.valueOf(Integer.parseInt(comment.id)), true, new DataRepository.VoteCommentListener() {
                        @Override
                        public void onResponse(ResponseComment response) {
                            dislike++;
                            bindingChildren.disLike.setText((dislike) + "");
                            Toast.makeText(view.getContext().getApplicationContext(), "Bravo za DISLAJK!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Throwable t) {

                        }
                    });
                }
            });
            bindingChildren.buttonReply.setOnClickListener(new View.OnClickListener() {
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


    @Override
    public int getItemCount() {
        return allComments.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (allComments.get(position).parent_comment.equals("0")) {
            return 0;
        } else {
            return 1;
        }
    }

    public void setDataComment(ArrayList<ResponseComment.Comment> commentData) {
        for (ResponseComment.Comment comment : commentData) {
            allComments.add(comment);
            addChildren(comment.children);
        }
        notifyDataSetChanged();
    }

    private void addChildren(ArrayList<ResponseComment.Comment> comments) {
        if (comments != null && !comments.isEmpty()) {
            for (ResponseComment.Comment comment : comments) {
                allComments.add(comment);
                addChildren(comment.children);
            }
        }
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {

        public ViewBinding binding;

        public CommentViewHolder(ViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
