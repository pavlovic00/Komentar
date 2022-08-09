package com.cubes.komentar.pavlovic.ui.main.home.homepage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.cubes.komentar.pavlovic.data.source.response.ResponseCategories;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final ArrayList<ResponseCategories.ResponseCategoriesData> categoriesList;


    public ViewPagerAdapter(@NonNull FragmentManager fm, ArrayList<ResponseCategories.ResponseCategoriesData> list) {
        super(fm);
        this.categoriesList = list;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        if (position == 0) {
            return HomepageFragment.newInstance();
        } else {
            ResponseCategories.ResponseCategoriesData categoriesData = categoriesList.get(position - 1);

            return ViewPagerFragment.newInstance(categoriesData.id);
        }
    }

    @Override
    public int getCount() {
        return categoriesList.size() + 1;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        if (position == 0) {
            return "Naslovna";
        } else {
            ResponseCategories.ResponseCategoriesData category = categoriesList.get(position - 1);
            return category.name;
        }
    }
}
