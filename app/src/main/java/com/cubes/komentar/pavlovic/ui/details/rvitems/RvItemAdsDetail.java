package com.cubes.komentar.pavlovic.ui.details.rvitems;

import com.cubes.komentar.R;
import com.cubes.komentar.databinding.RvItemAdsViewBinding;
import com.cubes.komentar.pavlovic.ui.details.DetailsAdapter;
import com.google.android.gms.ads.AdRequest;

public class RvItemAdsDetail implements RecyclerViewItemDetail {


    public RvItemAdsDetail() {
    }

    @Override
    public int getType() {
        return R.layout.rv_item_ads_view;
    }

    @Override
    public void bind(DetailsAdapter.ViewHolder holder) {

        RvItemAdsViewBinding binding = (RvItemAdsViewBinding) holder.binding;

        AdRequest adRequest = new AdRequest.Builder().build();
        binding.adsView.loadAd(adRequest);
    }
}
