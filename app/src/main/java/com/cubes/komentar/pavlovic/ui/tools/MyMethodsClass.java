package com.cubes.komentar.pavlovic.ui.tools;

import android.app.Activity;

import com.cubes.komentar.pavlovic.data.domain.CategoryBox;
import com.cubes.komentar.pavlovic.data.domain.News;
import com.cubes.komentar.pavlovic.data.domain.NewsList;
import com.cubes.komentar.pavlovic.data.domain.SaveNews;

import java.util.ArrayList;

public class MyMethodsClass {


    public static int[] initNewsIdList(ArrayList<News> newsList) {

        int[] newsIdList = new int[newsList.size()];

        for (int i = 0; i < newsList.size(); i++) {
            newsIdList[i] = newsList.get(i).id;
        }

        return newsIdList;
    }

    public static int[] initSaveNewsIdList(ArrayList<SaveNews> newsList) {

        int[] newsIdList = new int[newsList.size()];

        for (int i = 0; i < newsList.size(); i++) {
            newsIdList[i] = newsList.get(i).id;
        }

        return newsIdList;
    }

    //dodato
    public static boolean isSaved(int newsId, Activity activity) {
        if (SharedPrefs.showNewsFromPref(activity) != null) {

            for (int i = 0; i < SharedPrefs.showNewsFromPref(activity).size(); i++) {
                if (newsId == SharedPrefs.showNewsFromPref(activity).get(i).id) {
                    return true;
                }
            }
        }
        return false;
    }

    //dodato
    public static ArrayList<News> getAllNews(NewsList response) {
        ArrayList<News> allNews = new ArrayList<>();

        allNews.addAll(response.slider);
        allNews.addAll(response.top);
        allNews.addAll(response.latest);
        allNews.addAll(response.mostRead);
        allNews.addAll(response.mostCommented);
        allNews.addAll(response.editorsChoice);
        for (CategoryBox category : response.category) {
            if (category.title.equalsIgnoreCase("Sport")) {
                allNews.addAll(category.news);
            }
        }
        allNews.addAll(response.videos);
        for (CategoryBox category : response.category) {
            if (!category.title.equalsIgnoreCase("Sport")) {
                allNews.addAll(category.news);
            }
        }

        return allNews;
    }

}
