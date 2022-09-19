package com.cubes.komentar.pavlovic.ui.tools;

import com.cubes.komentar.pavlovic.data.domain.News;
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

}
