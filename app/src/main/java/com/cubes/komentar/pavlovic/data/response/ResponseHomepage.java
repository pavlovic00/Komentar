package com.cubes.komentar.pavlovic.data.response;

import com.cubes.komentar.pavlovic.data.model.News;

import java.util.ArrayList;

public class ResponseHomepage extends ParentResponse {


    public ResponseHomepageData data;

    public static class ResponseHomepageData {

        public ArrayList<News> slider;
        public ArrayList<News> top;
        public ArrayList<News> editors_choice;
        public ArrayList<ResponseHomePageDataCategoryBox> category;
        public ArrayList<News> latest;
        public ArrayList<News> most_read;
        public ArrayList<News> most_comented;
        public ArrayList<News> videos;

    }

    public static class ResponseHomePageDataCategoryBox {

        public int id;
        public String title;
        public String color;
        public ArrayList<News> news;

    }
}
