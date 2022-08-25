package com.cubes.komentar.pavlovic.ui.tag.rvitems;

import com.cubes.komentar.R;
import com.cubes.komentar.databinding.RvItemAdsViewBinding;
import com.cubes.komentar.pavlovic.ui.tag.TagsAdapter;
import com.google.android.gms.ads.AdRequest;

public class RvItemAdsTag implements RecyclerViewItemTag {


    public RvItemAdsTag() {
    }

    @Override
    public int getType() {
        return R.layout.rv_item_ads_view;
    }

    @Override
    public void bind(TagsAdapter.ViewHolder holder) {

        RvItemAdsViewBinding binding = (RvItemAdsViewBinding) holder.binding;

        AdRequest adRequest = new AdRequest.Builder().build();
        binding.adsView.loadAd(adRequest);
    }
}
