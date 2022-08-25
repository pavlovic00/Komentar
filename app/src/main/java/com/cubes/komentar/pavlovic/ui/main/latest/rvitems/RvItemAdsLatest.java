package com.cubes.komentar.pavlovic.ui.main.latest.rvitems;

import com.cubes.komentar.R;
import com.cubes.komentar.databinding.RvItemAdsViewBinding;
import com.cubes.komentar.pavlovic.ui.main.latest.LatestAdapter;
import com.google.android.gms.ads.AdRequest;

public class RvItemAdsLatest implements RecyclerViewItemLatest {


    public RvItemAdsLatest() {
    }

    @Override
    public int getType() {
        return R.layout.rv_item_ads_view;
    }

    @Override
    public void bind(LatestAdapter.ViewHolder holder) {

        RvItemAdsViewBinding binding = (RvItemAdsViewBinding) holder.binding;

        AdRequest adRequest = new AdRequest.Builder().build();
        binding.adsView.loadAd(adRequest);
    }
}
