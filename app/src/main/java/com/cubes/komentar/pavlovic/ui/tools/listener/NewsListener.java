package com.cubes.komentar.pavlovic.ui.tools.listener;

public interface NewsListener {

    void onNewsClickedVP(int newsId, int[] newsIdList);

    default void onSaveClicked(int id, String title) {

    }

    default void onUnSaveClicked(int id, String title) {

    }

}
