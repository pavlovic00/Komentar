package com.cubes.komentar.pavlovic.ui.details.rvitems;

import com.cubes.komentar.R;
import com.cubes.komentar.databinding.RvItemWebviewBinding;
import com.cubes.komentar.pavlovic.data.model.NewsDetailApi;
import com.cubes.komentar.pavlovic.ui.details.NewsDetailAdapter;

public class RvItemWebViewDetail implements RecyclerViewItemDetail {

    private final NewsDetailApi data;


    public RvItemWebViewDetail(NewsDetailApi data) {
        this.data = data;
    }

    @Override
    public int getType() {
        return R.layout.rv_item_webview;
    }

    @Override
    public void bind(NewsDetailAdapter.ViewHolder holder) {

        RvItemWebviewBinding binding = (RvItemWebviewBinding) holder.binding;

        binding.webView.loadUrl("https://komentar.rs/wp-json/" + "api/newswebview?id=" + data.id + "&version=2");
    }
}
