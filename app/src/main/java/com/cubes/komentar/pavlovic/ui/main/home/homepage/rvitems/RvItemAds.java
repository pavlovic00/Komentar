package com.cubes.komentar.pavlovic.ui.main.home.homepage.rvitems;

import com.cubes.komentar.R;
import com.cubes.komentar.databinding.RvItemAdsViewBinding;
import com.cubes.komentar.pavlovic.ui.main.home.homepage.HomepageAdapter;
import com.google.android.gms.ads.AdRequest;

public class RvItemAds implements RecyclerViewItemHomepage {


    public RvItemAds() {
    }

    @Override
    public int getType() {
        return R.layout.rv_item_ads_view;
    }

    @Override
    public void bind(HomepageAdapter.ViewHolder holder) {

        RvItemAdsViewBinding binding = (RvItemAdsViewBinding) holder.binding;

        AdRequest adRequest = new AdRequest.Builder().build();
        binding.adsView.loadAd(adRequest);
    }
}
