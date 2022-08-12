package com.cubes.komentar.pavlovic.ui.comments;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.cubes.komentar.R;
import com.cubes.komentar.databinding.RvItemCommentBinding;
import com.cubes.komentar.pavlovic.data.model.Vote;
import com.cubes.komentar.pavlovic.data.source.response.ResponseComment;
import com.cubes.komentar.pavlovic.ui.tools.CommentListener;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private final ArrayList<ResponseComment.Comment> allComments = new ArrayList<>();
    private ArrayList<Vote> votes = new ArrayList<>();
    private CommentListener commentListener;
    private final Activity activity;


    public CommentAdapter(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public CommentAdapter.CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ViewBinding binding;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());


        binding = RvItemCommentBinding.inflate(inflater, parent, false);

        return new CommentViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.CommentViewHolder holder, int position) {

        ResponseComment.Comment comment = allComments.get(position);

        RvItemCommentBinding binding = (RvItemCommentBinding) holder.binding;

        binding.person.setText(comment.name);
        binding.date.setText(comment.created_at);
        binding.content.setText(comment.content);
        binding.like.setText(comment.positive_votes + "");
        binding.dislike.setText(comment.negative_votes + "");

        if (!allComments.get(position).parent_comment.equals("0")) {
            setMargins(binding.rootLayout);
        }

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
                Vote vote = new Vote(comment.id, true);
                commentListener.like(comment.id);
                like(comment, binding);
                votes.add(vote);

                PrefConfig.writeListInPref(activity, votes);

            } else {
                Toast.makeText(view.getContext().getApplicationContext(), "Već ste glasali!", Toast.LENGTH_SHORT).show();
            }
            binding.imageViewLike.setEnabled(false);
            binding.imageViewDislike.setEnabled(false);
        });
        binding.imageViewDislike.setOnClickListener(view -> {
            if (comment.vote == null) {
                Vote vote = new Vote(comment.id, false);
                commentListener.dislike(comment.id);
                dislike(comment, binding);
                votes.add(vote);

                PrefConfig.writeListInPref(activity, votes);

            } else {
                Toast.makeText(view.getContext().getApplicationContext(), "Već ste glasali!", Toast.LENGTH_SHORT).show();
            }
            binding.imageViewLike.setEnabled(false);
            binding.imageViewDislike.setEnabled(false);
        });
        binding.buttonReply.setOnClickListener(view -> commentListener.onNewsCLicked(comment));
    }

    @Override
    public int getItemCount() {
        return allComments.size();
    }

    public void setDataComment(ArrayList<ResponseComment.Comment> commentData) {

        if (PrefConfig.readListFromPref(activity) != null) {
            votes = (ArrayList<Vote>) PrefConfig.readListFromPref(activity);
        }

        for (ResponseComment.Comment comment : commentData) {
            allComments.add(comment);
            addChildren(comment.children);
        }

        if (votes != null) {
            setVoteData(allComments, votes);
        }
        notifyDataSetChanged();
    }

    private void setVoteData(ArrayList<ResponseComment.Comment> allComments, ArrayList<Vote> votes) {

        for (ResponseComment.Comment comment : allComments) {
            for (Vote vote : votes) {
                if (comment.id.equals(vote.commentId)) {
                    comment.vote = vote;
                }
                if (comment.children != null) {
                    setVoteData(comment.children, votes);
                }
            }
        }
    }

    public void like(ResponseComment.Comment comment, RvItemCommentBinding binding) {
        binding.like.setText(String.valueOf(comment.positive_votes + 1));
        binding.imageViewLike.setImageResource(R.drawable.ic_like_vote);
        binding.likeCircle.setVisibility(View.VISIBLE);
    }

    public void dislike(ResponseComment.Comment comment, RvItemCommentBinding binding) {
        binding.dislike.setText(String.valueOf(comment.negative_votes + 1));
        binding.imageViewDislike.setImageResource(R.drawable.ic_dislike_vote);
        binding.dislikeCircle.setVisibility(View.VISIBLE);
    }

    public void setCommentListener(CommentListener commentListener) {
        this.commentListener = commentListener;
    }

    private void addChildren(ArrayList<ResponseComment.Comment> comments) {
        if (comments != null && !comments.isEmpty()) {
            for (ResponseComment.Comment comment : comments) {
                allComments.add(comment);
                addChildren(comment.children);
            }
        }
    }

    private void setMargins(View view) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(80, 0, 0, 0);
            view.requestLayout();
        }
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {

        public ViewBinding binding;

        public CommentViewHolder(ViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
