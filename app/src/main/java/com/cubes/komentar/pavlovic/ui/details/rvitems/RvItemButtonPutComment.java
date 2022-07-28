package com.cubes.komentar.pavlovic.ui.details.rvitems;

import android.content.Intent;
import android.view.View;

import com.cubes.komentar.databinding.RvItemButtonCommentBinding;
import com.cubes.komentar.pavlovic.ui.details.DetailNewsAdapter;
import com.cubes.komentar.pavlovic.ui.comments.ReplyActivity;

public class RvItemButtonPutComment implements RecyclerViewItemDetail{


    @Override
    public int getType() {
        return 5;
    }

    @Override
    public void bind(DetailNewsAdapter.DetailNewsViewHolder holder) {

        RvItemButtonCommentBinding binding = (RvItemButtonCommentBinding) holder.binding;

        binding.buttonComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyIntent = new Intent(view.getContext(), ReplyActivity.class);
//                if (data != null) {
//                    replyIntent.putExtra("main_id", data.id);
//                    if (data.children.size() > 0) {
//                        replyIntent.putExtra("reply_id", data.children.get(Integer.parseInt(data.id)).id);
//                    }
//                }
                view.getContext().startActivity(replyIntent);
            }
        });
    }
}
