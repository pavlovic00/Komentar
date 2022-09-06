package com.cubes.komentar.pavlovic.ui.details.rvitems;

import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cubes.komentar.R;
import com.cubes.komentar.databinding.RvItemWebviewBinding;
import com.cubes.komentar.pavlovic.data.domain.NewsDetail;
import com.cubes.komentar.pavlovic.data.source.networking.NewsRetrofit;
import com.cubes.komentar.pavlovic.ui.details.DetailsAdapter;
import com.cubes.komentar.pavlovic.ui.tools.listener.WebViewListener;

public class RvItemWebViewDetail implements RecyclerViewItemDetail {

    private final NewsDetail data;
    private final WebViewListener webViewListener;


    public RvItemWebViewDetail(NewsDetail data, WebViewListener webViewListener) {
        this.data = data;
        this.webViewListener = webViewListener;
    }

    @Override
    public int getType() {
        return R.layout.rv_item_webview;
    }

    @Override
    public void bind(DetailsAdapter.ViewHolder holder) {

        RvItemWebviewBinding binding = (RvItemWebviewBinding) holder.binding;

        binding.webView.loadUrl(NewsRetrofit.BASE_URL + "api/newswebview?id=" + data.id + "&version=2");

        binding.webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageCommitVisible(WebView view, String url) {
                super.onPageCommitVisible(view, url);
                webViewListener.onWebViewLoaded();
            }
        });
    }
}
