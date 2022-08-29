package com.cubes.komentar.pavlovic.ui.main.search.rvitems;

import com.cubes.komentar.R;
import com.cubes.komentar.databinding.RvItemAdsViewBinding;
import com.cubes.komentar.pavlovic.ui.main.search.SearchAdapter;
import com.google.android.gms.ads.AdRequest;

public class RvItemAdsSearch implements RecyclerViewItemSearch {


    public RvItemAdsSearch() {
    }

    @Override
    public int getType() {
        return R.layout.rv_item_ads_view;
    }

    @Override
    public void bind(SearchAdapter.ViewHolder holder) {

        RvItemAdsViewBinding binding = (RvItemAdsViewBinding) holder.binding;

        AdRequest adRequest = new AdRequest.Builder().build();
        binding.adsView.loadAd(adRequest);
    }
}
