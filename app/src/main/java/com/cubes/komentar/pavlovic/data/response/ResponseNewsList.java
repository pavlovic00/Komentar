package com.cubes.komentar.pavlovic.data.response;

import com.cubes.komentar.pavlovic.data.model.News;

import java.util.ArrayList;

public class ResponseNewsList extends ParentResponse {


    public ResponseData data;

    public class ResponseData {

        public ResponsePagination pagination;
        public ArrayList<News> news;
    }

    public class ResponsePagination {

        public int total;
        public int count;
        public int current_page;
        public boolean has_more_pages;
        public int last_page;
        public int per_page;

    }
}


