package com.cubes.komentar.pavlovic.ui.comments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.cubes.komentar.R;
import com.cubes.komentar.databinding.RvItemCommentChildrenBinding;
import com.cubes.komentar.databinding.RvItemCommentParentBinding;
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

        if (allComments.get(position).parent_comment.equals("0")) {

            RvItemCommentParentBinding bindingParent = (RvItemCommentParentBinding) holder.binding;

            bindingParent.person.setText(comment.name);
            bindingParent.date.setText(comment.created_at);
            bindingParent.content.setText(comment.content);
            bindingParent.like.setText(comment.positive_votes + "");
            bindingParent.dislike.setText(comment.negative_votes + "");


            bindingParent.imageViewLike.setOnClickListener(view -> {
                if (!comment.voted) {

                    commentListener.like(comment.id);

                    bindingParent.like.setText(String.valueOf(comment.positive_votes + 1));
                    comment.voted = true;
                    bindingParent.imageViewLike.setImageResource(R.drawable.ic_like_vote);
                    bindingParent.likeCircle.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(view.getContext().getApplicationContext(), "Već ste glasali!", Toast.LENGTH_SHORT).show();
                }
            });
            bindingParent.imageViewDislike.setOnClickListener(view -> {
                if (!comment.voted) {

                    commentListener.dislike(comment.id);

                    bindingParent.dislike.setText(String.valueOf(comment.negative_votes + 1));
                    comment.voted = true;
                    bindingParent.imageViewDislike.setImageResource(R.drawable.ic_dislike_vote);
                    bindingParent.dislikeCircle.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(view.getContext().getApplicationContext(), "Već ste glasali!", Toast.LENGTH_SHORT).show();
                }
            });
            bindingParent.buttonReply.setOnClickListener(view -> commentListener.onNewsCLicked(comment));
        } else {

            RvItemCommentChildrenBinding bindingChildren = (RvItemCommentChildrenBinding) holder.binding;

            bindingChildren.person.setText(comment.name);
            bindingChildren.date.setText(comment.created_at);
            bindingChildren.content.setText(comment.content);
            bindingChildren.like.setText(comment.positive_votes + "");
            bindingChildren.dislike.setText(comment.negative_votes + "");

            bindingChildren.imageViewLike.setOnClickListener(view -> {
                if (!comment.voted) {

                    commentListener.like(comment.id);

                    bindingChildren.like.setText(String.valueOf(comment.positive_votes + 1));
                    comment.voted = true;
                    bindingChildren.imageViewLike.setImageResource(R.drawable.ic_like_vote);
                    bindingChildren.likeCircle.setVisibility(View.VISIBLE);
                    Toast.makeText(view.getContext().getApplicationContext(), "Bravo za LAJK!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(view.getContext().getApplicationContext(), "Već ste glasali!", Toast.LENGTH_SHORT).show();
                }
            });
            bindingChildren.imageViewDislike.setOnClickListener(view -> {
                if (!comment.voted) {

                    commentListener.dislike(comment.id);

                    bindingChildren.dislike.setText(String.valueOf(comment.negative_votes + 1));
                    comment.voted = true;
                    bindingChildren.imageViewDislike.setImageResource(R.drawable.ic_dislike_vote);
                    bindingChildren.dislikeCircle.setVisibility(View.VISIBLE);
                    Toast.makeText(view.getContext().getApplicationContext(), "Bravo za DISLAJK!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(view.getContext().getApplicationContext(), "Već ste glasali!", Toast.LENGTH_SHORT).show();
                }
            });
            bindingChildren.buttonReply.setOnClickListener(view -> commentListener.onNewsCLicked(comment));
        }
    }

    @Override
    public int getItemCount() {
        return allComments.size();
    }

    public void setCommentListener(CommentListener commentListener) {
        this.commentListener = commentListener;
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

    public static class CommentViewHolder extends RecyclerView.ViewHolder {

        public ViewBinding binding;

        public CommentViewHolder(ViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
