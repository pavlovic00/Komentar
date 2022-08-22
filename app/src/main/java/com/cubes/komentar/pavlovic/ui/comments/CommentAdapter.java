package com.cubes.komentar.pavlovic.ui.comments;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.cubes.komentar.R;
import com.cubes.komentar.databinding.RvItemCommentChildBinding;
import com.cubes.komentar.databinding.RvItemCommentParentBinding;
import com.cubes.komentar.pavlovic.data.model.CommentApi;
import com.cubes.komentar.pavlovic.ui.comments.rvitems.RecyclerViewItemComment;
import com.cubes.komentar.pavlovic.ui.comments.rvitems.RvItemCommentChild;
import com.cubes.komentar.pavlovic.ui.comments.rvitems.RvItemCommentParent;
import com.cubes.komentar.pavlovic.ui.tools.CommentListener;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private final ArrayList<RecyclerViewItemComment> items = new ArrayList<>();
    private CommentListener commentListener;


    public CommentAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ViewBinding binding;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == R.layout.rv_item_comment_child) {
            binding = RvItemCommentChildBinding.inflate(inflater, parent, false);
        } else {
            binding = RvItemCommentParentBinding.inflate(inflater, parent, false);
        }
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        this.items.get(position).bind(holder);
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getType();
    }

    public void updateList(ArrayList<CommentApi> comments) {
        for (CommentApi comment : comments) {

            if (comment.parent_comment.equals("0")) {
                items.add(new RvItemCommentParent(comment, commentListener));
            } else {
                items.add(new RvItemCommentChild(comment, commentListener));
            }
        }

        notifyDataSetChanged();
    }

    public void setupLike(String commentId) {
        for (RecyclerViewItemComment comment : items) {
            if (comment.getCommentsId().equals(commentId)) {
                comment.updateLike();
                break;
            }
        }
    }

    public void setupDislike(String commentId) {
        for (RecyclerViewItemComment comment : items) {
            if (comment.getCommentsId().equals(commentId)) {
                comment.updateDislike();
                break;
            }
        }
    }

    public void setCommentListener(CommentListener commentListener) {
        this.commentListener = commentListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewBinding binding;

        public ViewHolder(ViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
