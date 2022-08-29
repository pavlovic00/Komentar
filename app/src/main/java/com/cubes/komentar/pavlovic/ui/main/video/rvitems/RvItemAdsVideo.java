package com.cubes.komentar.pavlovic.ui.main.video.rvitems;

import android.graphics.Color;

import com.cubes.komentar.R;
import com.cubes.komentar.databinding.RvItemAdsViewBinding;
import com.cubes.komentar.pavlovic.ui.main.video.VideoAdapter;
import com.google.android.gms.ads.AdRequest;

public class RvItemAdsVideo implements RecyclerViewItemVideo {


    public RvItemAdsVideo() {
    }

    @Override
    public int getType() {
        return R.layout.rv_item_ads_view;
    }

    @Override
    public void bind(VideoAdapter.ViewHolder holder) {

        RvItemAdsViewBinding binding = (RvItemAdsViewBinding) holder.binding;

        binding.rootLayout.setBackgroundColor(Color.parseColor("#0F2039"));

        AdRequest adRequest = new AdRequest.Builder().build();
        binding.adsView.loadAd(adRequest);
    }
}
