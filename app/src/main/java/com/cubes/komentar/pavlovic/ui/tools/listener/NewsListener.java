package com.cubes.komentar.pavlovic.ui.tools.listener;

public interface NewsListener {

    void onNewsClickedVP(int newsId, int[] newsIdList);

    default void onSaveClicked(int id, String title) {

    }

    default void deleteNews(int id, String title) {

    }

    default boolean isSaved(int id) {
        return false;
    }

    default void onCommentNewsClicked(int id) {

    }

    default void onShareNewsClicked(String url) {

    }

}
