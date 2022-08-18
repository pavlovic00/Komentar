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

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private final ArrayList<ResponseComment.Comment> allComments = new ArrayList<>();
    private CommentListener commentListener;


    public CommentAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ViewBinding binding;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());


        binding = RvItemCommentBinding.inflate(inflater, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ResponseComment.Comment comment = allComments.get(position);

        RvItemCommentBinding binding = (RvItemCommentBinding) holder.binding;

        String like = comment.positive_votes + "";
        String dislike = comment.negative_votes + "";

        binding.person.setText(comment.name);
        binding.date.setText(comment.created_at);
        binding.content.setText(comment.content);
        binding.like.setText(like);
        binding.dislike.setText(dislike);

        if (!allComments.get(position).parent_comment.equals("0")) {
            setMargins(binding.rootLayout);
        }

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

    @Override
    public int getItemCount() {
        return allComments.size();
    }

    public void updateList(ArrayList<ResponseComment.Comment> comments) {
        allComments.addAll(comments);
        notifyDataSetChanged();
    }

    public void setCommentListener(CommentListener commentListener) {
        this.commentListener = commentListener;
    }

    private void setMargins(View view) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(80, 0, 0, 0);
            view.requestLayout();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewBinding binding;

        public ViewHolder(ViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
