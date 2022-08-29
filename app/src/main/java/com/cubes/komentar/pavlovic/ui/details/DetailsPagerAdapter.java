package com.cubes.komentar.pavlovic.ui.details;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class DetailsPagerAdapter extends FragmentStateAdapter {

    private final int[] newsListId;


    public DetailsPagerAdapter(@NonNull FragmentActivity fragmentActivity, int[] newsListId) {
        super(fragmentActivity);
        this.newsListId = newsListId;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return DetailsPagerFragment.newInstance(newsListId[position]);
    }

    @Override
    public int getItemCount() {
        return newsListId.length;
    }
}
