package com.cubes.komentar.pavlovic.ui.comments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.cubes.komentar.R;
import com.cubes.komentar.databinding.RvItemCommentBinding;
import com.cubes.komentar.pavlovic.data.source.response.ResponseComment;
import com.cubes.komentar.pavlovic.ui.tools.CommentListener;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private final ArrayList<ResponseComment.Comment> allComments = new ArrayList<>();
    private CommentListener commentListener;


    public CommentAdapter() {

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


        binding.imageViewLike.setOnClickListener(view -> {
            if (comment.voted == 0) {
                commentListener.like(comment.id);
                like(comment, binding);
            } else {
                Toast.makeText(view.getContext().getApplicationContext(), "Već ste glasali!", Toast.LENGTH_SHORT).show();
            }
        });
        binding.imageViewDislike.setOnClickListener(view -> {
            if (comment.voted == 0) {
                commentListener.dislike(comment.id);
                dislike(comment, binding);
            } else {
                Toast.makeText(view.getContext().getApplicationContext(), "Već ste glasali!", Toast.LENGTH_SHORT).show();
            }
        });
        binding.buttonReply.setOnClickListener(view -> commentListener.onNewsCLicked(comment));

        if (!allComments.get(position).parent_comment.equals("0")) {
            setMargins(binding.rootLayout);
        }
    }

    @Override
    public int getItemCount() {
        return allComments.size();
    }

    public void like(ResponseComment.Comment comment, RvItemCommentBinding binding) {
        binding.like.setText(String.valueOf(comment.positive_votes + 1));
        comment.voted = 1;
        binding.imageViewLike.setImageResource(R.drawable.ic_like_vote);
        binding.likeCircle.setVisibility(View.VISIBLE);
    }

    public void dislike(ResponseComment.Comment comment, RvItemCommentBinding binding) {
        binding.dislike.setText(String.valueOf(comment.negative_votes + 1));
        comment.voted = 1;
        binding.imageViewDislike.setImageResource(R.drawable.ic_dislike_vote);
        binding.dislikeCircle.setVisibility(View.VISIBLE);
    }

    public void setCommentListener(CommentListener commentListener) {
        this.commentListener = commentListener;
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
