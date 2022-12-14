package com.cubes.komentar.pavlovic.data.source.networking;

import com.cubes.komentar.pavlovic.data.source.response.RequestComment;
import com.cubes.komentar.pavlovic.data.source.response.ResponseCategories;
import com.cubes.komentar.pavlovic.data.source.response.ResponseComment;
import com.cubes.komentar.pavlovic.data.source.response.ResponseDetail;
import com.cubes.komentar.pavlovic.data.source.response.ResponseHomepage;
import com.cubes.komentar.pavlovic.data.source.response.ResponseHoroscope;
import com.cubes.komentar.pavlovic.data.source.response.ResponseNewsList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitService {

    @GET("api/latest")
    Call<ResponseNewsList> getLatestNews(@Query("page") int page);

    @GET("api/videos")
    Call<ResponseNewsList> getVideo(@Query("page") int page);

    @GET("api/search")
    Call<ResponseNewsList> getSearch(@Query("search_parameter") String search_parameter, @Query("page") int page);

    @GET("api/newsdetails")
    Call<ResponseDetail> getNewsDetail(@Query("id") int id);

    @GET("api/comments")
    Call<ResponseComment> getComment(@Query("id") int id);

    @GET("api/categories")
    Call<ResponseCategories> getCategoryNews();

    @GET("api/category/{id}")
    Call<ResponseNewsList> getAllNews(@Path("id") int id, @Query("page") int page);

    @GET("api/homepage")
    Call<ResponseHomepage> getHomepage();

    @GET("api/tag")
    Call<ResponseNewsList> getTag(@Query("tag") int tag, @Query("page") int page);

    @GET("api/horoscope")
    Call<ResponseHoroscope> getHoroscope();

    @POST("api/commentvote")
    Call<ResponseComment> postLike(@Query("comment") int id, @Query("vote") boolean vote);

    @POST("api/commentvote")
    Call<ResponseComment> postDislike(@Query("comment") int id, @Query("downvote") boolean vote);

    @POST("api/commentinsert")
    Call<RequestComment.RequestBody> createPost(@Body RequestComment.RequestBody body);
}
