package com.cubes.komentar.pavlovic.ui.details.rvitems;

import com.cubes.komentar.databinding.RvItemWebviewBinding;
import com.cubes.komentar.pavlovic.data.response.responsedetail.ResponseDetailData;
import com.cubes.komentar.pavlovic.ui.details.DetailNewsAdapter;

public class RvItemWebviewDetail implements RecyclerViewItemDetail {

    private ResponseDetailData data;

    public RvItemWebviewDetail(ResponseDetailData data) {
        this.data = data;
    }

    @Override
    public int getType() {
        return 1;
    }

    @Override
    public void bind(DetailNewsAdapter.DetailNewsViewHolder holder) {

        RvItemWebviewBinding binding = (RvItemWebviewBinding) holder.binding;

        binding.webView.loadUrl("https://komentar.rs/wp-json/" + "api/newswebview?id=" + data.id + "&version=2");

    }
}
