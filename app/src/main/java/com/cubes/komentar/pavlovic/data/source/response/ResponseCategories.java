package com.cubes.komentar.pavlovic.data.source.response;

import java.util.ArrayList;

public class ResponseCategories extends ParentResponse {

    public ArrayList<ResponseCategoriesData> data;


    public static class ResponseCategoriesData {

        public boolean open;
        public String type;
        public int id;
        public String name;
        public String color;
        public String main_image;
        public String description;
        public ArrayList<ResponseCategoriesData> subcategories;
    }

}
