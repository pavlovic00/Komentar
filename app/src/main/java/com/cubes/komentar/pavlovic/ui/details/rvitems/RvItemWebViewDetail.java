package com.cubes.komentar.pavlovic.ui.details.rvitems;

import com.cubes.komentar.R;
import com.cubes.komentar.databinding.RvItemWebviewBinding;
import com.cubes.komentar.pavlovic.data.domain.NewsDetail;
import com.cubes.komentar.pavlovic.ui.details.DetailsAdapter;

public class RvItemWebViewDetail implements RecyclerViewItemDetail {

    private final NewsDetail data;


    public RvItemWebViewDetail(NewsDetail data) {
        this.data = data;
    }

    @Override
    public int getType() {
        return R.layout.rv_item_webview;
    }

    @Override
    public void bind(DetailsAdapter.ViewHolder holder) {

        RvItemWebviewBinding binding = (RvItemWebviewBinding) holder.binding;

        binding.webView.loadUrl("https://komentar.rs/wp-json/" + "api/newswebview?id=" + data.id + "&version=2");
    }
}
