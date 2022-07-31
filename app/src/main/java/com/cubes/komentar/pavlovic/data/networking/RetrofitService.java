package com.cubes.komentar.pavlovic.data.networking;

import com.cubes.komentar.pavlovic.data.response.response.Response;
import com.cubes.komentar.pavlovic.data.response.responsecategories.ResponseCategories;
import com.cubes.komentar.pavlovic.data.response.responsecomment.ResponseComment;
import com.cubes.komentar.pavlovic.data.response.responsedetail.ResponseDetail;
import com.cubes.komentar.pavlovic.data.response.responsehomepage.ResponseHomepage;
import com.cubes.komentar.pavlovic.data.response.send.ResponseBody;
import com.cubes.komentar.pavlovic.data.response.send.ResponseCommentSend;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitService {

    @GET("api/latest")
    Call<Response>getLatestNews();

    @GET("api/videos")
    Call<Response>getVideo();

    @GET("api/search")
    Call<Response>getSearch(@Query("search_parameter")String search_parameter);

    @GET("api/newsdetails")
    Call<ResponseDetail>getNewsDetail(@Query("id")int id);

    @GET("api/comments")
    Call<ResponseComment>getComment(@Query("id")int id);

    @GET("api/categories")
    Call<ResponseCategories> getCategoryNews();

    @GET("api/category/{id}")
    Call<Response> getAllNews(@Path("id") int id);

    @GET("api/homepage")
    Call<ResponseHomepage> getHomepage();

    @GET("api/tag")
    Call<Response> getTag(@Query("tag")int tag);

    @POST("api/commentvote")
    Call<ResponseComment>postLike(@Query("comment")int id,@Query("vote")boolean vote);

    @POST("api/commentvote")
    Call<ResponseComment>postDislike(@Query("comment")int id,@Query("downvote")boolean vote);

    @POST("api/commentinsert")
    Call<ResponseCommentSend>postComment(@Field("news") String news,
                                         @Field("reply_id") String reply_id,
                                         @Field("name") String name,
                                         @Field("email") String email,
                                         @Field("content") String content);

    @POST("api/commentinsert")
    Call<ResponseBody> createPost(@Body ResponseBody body);
}
