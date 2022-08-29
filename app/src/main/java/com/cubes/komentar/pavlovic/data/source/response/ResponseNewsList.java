package com.cubes.komentar.pavlovic.data.source.response;

import com.cubes.komentar.pavlovic.data.model.NewsApi;
import com.cubes.komentar.pavlovic.data.model.PaginationApi;

import java.util.ArrayList;

public class ResponseNewsList extends ParentResponse {

    public ResponseNewsListData data;

    public static class ResponseNewsListData {

        public PaginationApi pagination;
        public ArrayList<NewsApi> news;

    }
}
