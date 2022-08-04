package com.cubes.komentar.pavlovic.data.networking;

import com.cubes.komentar.pavlovic.data.response.ResponseForPaging;
import com.cubes.komentar.pavlovic.data.response.ResponseNewsList;
import com.cubes.komentar.pavlovic.data.response.ResponseCategories;
import com.cubes.komentar.pavlovic.data.response.ResponseComment;
import com.cubes.komentar.pavlovic.data.response.ResponseDetail;
import com.cubes.komentar.pavlovic.data.response.ResponseHomepage;
import com.cubes.komentar.pavlovic.data.response.ResponseCommentSend;

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

    @GET("api/newsdetails")
    Call<ResponseForPaging> getNewsDetailPaging(@Query("id") int id);

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

    @POST("api/commentvote")
    Call<ResponseComment> postLike(@Query("comment") int id, @Query("vote") boolean vote);

    @POST("api/commentvote")
    Call<ResponseComment> postDislike(@Query("comment") int id, @Query("downvote") boolean vote);

    @POST("api/commentinsert")
    Call<ResponseCommentSend.ResponseBody> createPost(@Body ResponseCommentSend.ResponseBody body);
}
