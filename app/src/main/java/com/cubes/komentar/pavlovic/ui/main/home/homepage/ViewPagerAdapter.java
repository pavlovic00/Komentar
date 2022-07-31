package com.cubes.komentar.pavlovic.ui.main.home.homepage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.cubes.komentar.pavlovic.data.model.News;
import com.cubes.komentar.pavlovic.data.response.responsecategories.ResponseCategoriesData;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<ResponseCategoriesData> categoriesList;
    private ArrayList<News> newsList = new ArrayList<>();


    public ViewPagerAdapter(@NonNull FragmentManager fm, ArrayList<ResponseCategoriesData> list) {
        super(fm);
        this.categoriesList = list;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        if (position == 0){
            HomepageFragment fragment = HomepageFragment.newInstance(newsList);
            return  fragment;
        }

        else {
            ResponseCategoriesData categoriesData = categoriesList.get(position-1);

            ViewPagerFragment fragment = ViewPagerFragment.newInstance(categoriesData.id);
            return fragment;
        }
    }

    @Override
    public int getCount() {
        return categoriesList.size()+1;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        if (position == 0) {
            return "Naslovna";
        }
        else{
            ResponseCategoriesData category = categoriesList.get(position-1);
            return category.name;
            }
        }
}
