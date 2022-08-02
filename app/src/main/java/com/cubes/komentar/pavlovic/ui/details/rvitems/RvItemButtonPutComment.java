package com.cubes.komentar.pavlovic.ui.details.rvitems;

import android.content.Intent;
import android.view.View;

import com.cubes.komentar.databinding.RvItemButtonCommentBinding;
import com.cubes.komentar.pavlovic.ui.details.DetailNewsAdapter;
import com.cubes.komentar.pavlovic.ui.comments.ReplyActivity;

public class RvItemButtonPutComment implements RecyclerViewItemDetail {


    @Override
    public int getType() {
        return 4;
    }

    @Override
    public void bind(DetailNewsAdapter.DetailNewsViewHolder holder) {

        RvItemButtonCommentBinding binding = (RvItemButtonCommentBinding) holder.binding;

        binding.buttonComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyIntent = new Intent(view.getContext(), ReplyActivity.class);

//                replyIntent.putExtra("news", data.news);
//                replyIntent.putExtra("id", data.id);

                replyIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(replyIntent);
            }
        });
    }
}
