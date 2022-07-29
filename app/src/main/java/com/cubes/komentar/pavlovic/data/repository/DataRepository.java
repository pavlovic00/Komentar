package com.cubes.komentar.pavlovic.data.repository;

import android.content.Context;
import android.content.Intent;

import com.cubes.komentar.pavlovic.data.DataContainer;
import com.cubes.komentar.pavlovic.data.response.ResponseForPaging;
import com.cubes.komentar.pavlovic.data.model.News;
import com.cubes.komentar.pavlovic.data.networking.RetrofitService;
import com.cubes.komentar.pavlovic.data.response.response.Response;
import com.cubes.komentar.pavlovic.data.response.responsecategories.ResponseCategories;
import com.cubes.komentar.pavlovic.data.response.responsecomment.ResponseComment;
import com.cubes.komentar.pavlovic.data.response.responsedetail.ResponseDetail;
import com.cubes.komentar.pavlovic.data.response.responsehomepage.ResponseHomepage;
import com.cubes.komentar.pavlovic.ui.details.NewsDetailActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataRepository {

    private static DataRepository instance;
    private Retrofit retrofit;
    private RetrofitService service;
    private Context context;
    private News news;
    private ArrayList<News> newsArrayList;

    private DataRepository() {
        callRetrofit();
    }

    public static DataRepository getInstance()
    {
    if (instance == null){
        instance = new DataRepository();
    }
    return instance;
    }

    public void callRetrofit(){
        retrofit= new Retrofit.Builder()
                .baseUrl("https://komentar.rs/wp-json/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(RetrofitService.class);
    }

    public interface VideoResponseListener {

        void onResponse(com.cubes.komentar.pavlovic.data.response.response.Response response);

        void onFailure(Throwable t);
    }

    public interface LatestResponseListener {

        void onResponse(com.cubes.komentar.pavlovic.data.response.response.Response response);

        void onFailure(Throwable t);
    }

    public interface SearchResponseListener {

        void onResponse(com.cubes.komentar.pavlovic.data.response.response.Response response);

        void onFailure(Throwable t);
    }

    public interface CategoryResponseListener {

        void onResponse(com.cubes.komentar.pavlovic.data.response.response.Response response);

        void onFailure(Throwable t);
    }

    public interface TagResponseListener {

        void onResponse(com.cubes.komentar.pavlovic.data.response.response.Response response);

        void onFailure(Throwable t);
    }

    public interface HomeResponseListener {

        void onResponse(ResponseHomepage response);

        void onFailure(Throwable t);
    }

    public interface CategoriesResponseListener {

        void onResponse(ResponseCategories response);

        void onFailure(Throwable t);
    }

    public interface CommentResponseListener {

        void onResponse(ResponseComment response);

        void onFailure(Throwable t);
    }

    public interface DetailResponseListener {

        void onResponse(ResponseDetail response);

        void onFailure(Throwable t);
    }

    public interface NewsResponseListener {

        void onResponse(Response response);

        void onFailure(Throwable t);
    }


    //za video.
    public void loadVideoData(int page, VideoResponseListener listener){

        service.getVideo(page).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

                if (response.body() != null
                    && response.isSuccessful()
                    && response.body().data != null
                    && !response.body().data.news.isEmpty()){
                    listener.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    //za latest.
    public void loadLatestData(int page, LatestResponseListener listener){

        service.getLatestNews(page).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.body() != null
                        && response.isSuccessful()
                        && response.body().data != null){
                    listener.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                listener.onFailure(t);
            }
        });

    }

    //za search.
    public void loadSearchData(String term, SearchResponseListener listener){

        service.getSearch(term).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.body() != null
                        && response.isSuccessful()
                        && response.body().data != null
                        && !response.body().data.news.isEmpty()){
                    listener.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                listener.onFailure(t);
            }
        });

    }

    //za category.
    public void loadCategoryData(int id,int page, CategoryResponseListener listener){

        service.getAllNews(id, page).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.body() != null
                        && response.isSuccessful()
                        && response.body().data != null
                        && !response.body().data.news.isEmpty()){
                    listener.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                listener.onFailure(t);
            }
        });

    }

    //za tag.
    public void loadTagData(int id, TagResponseListener listener){

        service.getTag(id).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.body() != null
                        && response.isSuccessful()
                        && response.body().data != null
                        && !response.body().data.news.isEmpty()){
                    listener.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                listener.onFailure(t);
            }
        });

    }

    //za home.
    public void loadHomeData(HomeResponseListener listener){

        service.getHomepage().enqueue(new Callback<ResponseHomepage>() {
            @Override
            public void onResponse(Call<ResponseHomepage> call, retrofit2.Response<ResponseHomepage> response) {
                if (response.body() != null
                        && response.isSuccessful()
                        && response.body().data != null){
                    listener.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseHomepage> call, Throwable t) {
                listener.onFailure(t);
            }
        });

    }

    //za categories.
    public void loadCategoriesData(CategoriesResponseListener listener){

        service.getCategoryNews().enqueue(new Callback<ResponseCategories>() {
            @Override
            public void onResponse(Call<ResponseCategories> call, retrofit2.Response<ResponseCategories> response) {
                if (response.body() != null
                        && response.isSuccessful()
                        && response.body().data != null){
                    listener.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseCategories> call, Throwable t) {
                listener.onFailure(t);
            }
        });

    }

    //za comment.
    public void loadCommentData(int id, CommentResponseListener listener){

        service.getComment(id).enqueue(new Callback<ResponseComment>() {
            @Override
            public void onResponse(Call<ResponseComment> call, retrofit2.Response<ResponseComment> response) {
                if (response.body() != null
                        && response.isSuccessful()
                        && response.body().data != null){
                    listener.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseComment> call, Throwable t) {
                listener.onFailure(t);
            }
        });

    }

    //za detail.
    public void loadDetailData(int id, DetailResponseListener listener){

        service.getNewsDetail(id).enqueue(new Callback<ResponseDetail>() {
            @Override
            public void onResponse(Call<ResponseDetail> call, retrofit2.Response<ResponseDetail> response) {
                if (response.body() != null
                        && response.isSuccessful()
                        && response.body().data != null){
                    listener.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseDetail> call, Throwable t) {
                listener.onFailure(t);
            }
        });

    }

    //loading...
    public void getNewsDetails(Context context, News news){

        service.getNewsDetailPaging(news.id).enqueue(new Callback<ResponseForPaging>() {
            @Override
            public void onResponse(Call<ResponseForPaging> call, retrofit2.Response<ResponseForPaging> response) {
                News newsDetails = response.body().data;

                DataRepository.getInstance().loadCommentData(news.id, new CommentResponseListener() {
                    @Override
                    public void onResponse(ResponseComment response) {
                        DataContainer.commentList = response.data;

                        Intent i = new Intent(context, NewsDetailActivity.class);
                        i.putExtra("news", newsDetails);
                        context.startActivity(i);
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });

            }

            @Override
            public void onFailure(Call<ResponseForPaging> call, Throwable t) {

            }
        });

    }

    //loading news za categories...
    public void loadCategoriesNewsData(int id, int page, NewsResponseListener listener){

        service.getAllNews(id,page).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.body() != null
                        && response.isSuccessful()
                        && response.body().data != null){
                    listener.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                    listener.onFailure(t);
            }
        });

    }
}
