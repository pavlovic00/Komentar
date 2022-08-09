package com.cubes.komentar.pavlovic.ui.details.rvitems;

import android.content.Intent;

import com.cubes.komentar.databinding.RvItemButtonCommentBinding;
import com.cubes.komentar.pavlovic.ui.comments.PostComment;
import com.cubes.komentar.pavlovic.ui.details.DetailNewsAdapter;

public class RvItemButtonPutComment implements RecyclerViewItemDetail {


    @Override
    public int getType() {
        return 4;
    }

    @Override
    public void bind(DetailNewsAdapter.DetailNewsViewHolder holder) {

        RvItemButtonCommentBinding binding = (RvItemButtonCommentBinding) holder.binding;

        binding.buttonComment.setOnClickListener(view -> {
            Intent replyIntent = new Intent(view.getContext(), PostComment.class);



            replyIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            view.getContext().startActivity(replyIntent);
        });
    }
}
