package com.cubes.komentar.pavlovic.data.response;

import java.util.ArrayList;

public class ResponseCategories extends ParentResponse {


    public ArrayList<ResponseCategoriesData> data;

    public class ResponseCategoriesData {

        public boolean open;
        public String type;
        public int id;
        public String name;
        public String color;
        public String main_image;
        public String description;
        public ArrayList<ResponseCategoriesData> subcategories;

        public ResponseCategoriesData(String type, int id, String name, String color, String main_image, String description, ArrayList<ResponseCategoriesData> subcategories) {
            this.open = true;
            this.type = type;
            this.id = id;
            this.name = name;
            this.color = color;
            this.main_image = main_image;
            this.description = description;
            this.subcategories = subcategories;
        }
    }

    public class ResponseCategoriesSubcategories {

        public String type;
        public int id;
        public String name;
        public String color;
        public String main_image;
        public String description;
        public ArrayList<ResponseCategoriesSubcategories> subcategories;

    }
}
