package com.cubes.komentar.pavlovic.ui.details.rvitems;

import com.cubes.komentar.databinding.RvItemFirstItemDetailBinding;
import com.cubes.komentar.pavlovic.data.response.ResponseDetail;
import com.cubes.komentar.pavlovic.ui.details.DetailNewsAdapter;

public class RvItemFirstItemDetail implements RecyclerViewItemDetail {

    private ResponseDetail.ResponseDetailData data;


    public RvItemFirstItemDetail(ResponseDetail.ResponseDetailData data) {
        this.data = data;
    }

    @Override
    public int getType() {
        return 0;
    }

    @Override
    public void bind(DetailNewsAdapter.DetailNewsViewHolder holder) {

        RvItemFirstItemDetailBinding binding = (RvItemFirstItemDetailBinding) holder.binding;

        binding.textViewAuthor.setText(data.author_name);
        binding.textViewIzvor.setText(data.source);
        binding.textViewTitle.setText(data.title);
        binding.textViewDate.setText(data.created_at);
        binding.textViewDescription.setText(data.description);
        binding.textViewCommentCount.setText("" + data.comments_count);

    }
}
