package com.cubes.komentar.pavlovic.data.source.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.cubes.komentar.pavlovic.data.source.networking.RetrofitService;
import com.cubes.komentar.pavlovic.data.source.response.ResponseCategories;
import com.cubes.komentar.pavlovic.data.source.response.ResponseComment;
import com.cubes.komentar.pavlovic.data.source.response.ResponseCommentSend;
import com.cubes.komentar.pavlovic.data.source.response.ResponseDetail;
import com.cubes.komentar.pavlovic.data.source.response.ResponseHomepage;
import com.cubes.komentar.pavlovic.data.source.response.ResponseNewsList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataRepository {

    private static DataRepository instance;
    private RetrofitService service;

    private DataRepository() {
        callRetrofit();
    }

    public static DataRepository getInstance() {
        if (instance == null) {
            instance = new DataRepository();
        }
        return instance;
    }

    public void callRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://komentar.rs/wp-json/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(RetrofitService.class);
    }

    public interface VideoResponseListener {

        void onResponse(ResponseNewsList.ResponseData response);

        void onFailure(Throwable t);
    }
    public void loadVideoData(int page, VideoResponseListener listener) {

        service.getVideo(page).enqueue(new Callback<ResponseNewsList>() {
            @Override
            public void onResponse(@NonNull Call<ResponseNewsList> call, @NonNull retrofit2.Response<ResponseNewsList> response) {

                if (response.body() != null
                        && response.isSuccessful()
                        && response.body().data != null
                        && !response.body().data.news.isEmpty()) {
                    listener.onResponse(response.body().data);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseNewsList> call, @NonNull Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    public interface LatestResponseListener {

        void onResponse(ResponseNewsList.ResponseData response);

        void onFailure(Throwable t);
    }
    public void loadLatestData(int page, LatestResponseListener listener) {

        service.getLatestNews(page).enqueue(new Callback<ResponseNewsList>() {
            @Override
            public void onResponse(@NonNull Call<ResponseNewsList> call, @NonNull retrofit2.Response<ResponseNewsList> response) {
                if (response.body() != null
                        && response.isSuccessful()
                        && response.body().data != null
                        && !response.body().data.news.isEmpty()) {
                    listener.onResponse(response.body().data);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseNewsList> call, @NonNull Throwable t) {
                listener.onFailure(t);
            }
        });

    }

    public interface SearchResponseListener {

        void onResponse(ResponseNewsList.ResponseData responseNewsList);

        void onFailure(Throwable t);
    }
    public void loadSearchData(String term, int page, SearchResponseListener listener) {

        service.getSearch(term, page).enqueue(new Callback<ResponseNewsList>() {
            @Override
            public void onResponse(@NonNull Call<ResponseNewsList> call, @NonNull retrofit2.Response<ResponseNewsList> response) {
                if (response.body() != null
                        && response.isSuccessful()
                        && response.body().data != null
                        && !response.body().data.news.isEmpty()) {
                    listener.onResponse(response.body().data);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseNewsList> call, @NonNull Throwable t) {
                listener.onFailure(t);
            }
        });

    }

    public interface CategoryResponseListener {

        void onResponse(ResponseNewsList.ResponseData responseNewsList);

        void onFailure(Throwable t);
    }
    public void loadCategoryData(int id, int page, CategoryResponseListener listener) {

        service.getAllNews(id, page).enqueue(new Callback<ResponseNewsList>() {
            @Override
            public void onResponse(@NonNull Call<ResponseNewsList> call, @NonNull retrofit2.Response<ResponseNewsList> response) {
                if (response.body() != null
                        && response.isSuccessful()
                        && response.body().data != null
                        && !response.body().data.news.isEmpty()) {
                    listener.onResponse(response.body().data);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseNewsList> call, @NonNull Throwable t) {
                listener.onFailure(t);
            }
        });

    }

    public interface TagResponseListener {

        void onResponse(ResponseNewsList responseNewsList);

        void onFailure(Throwable t);
    }
    public void loadTagData(int id, int page, TagResponseListener listener) {

        service.getTag(id, page).enqueue(new Callback<ResponseNewsList>() {
            @Override
            public void onResponse(@NonNull Call<ResponseNewsList> call, @NonNull retrofit2.Response<ResponseNewsList> response) {
                if (response.body() != null
                        && response.isSuccessful()
                        && response.body().data != null
                        && !response.body().data.news.isEmpty()) {
                    listener.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseNewsList> call, @NonNull Throwable t) {
                listener.onFailure(t);
            }
        });

    }

    public interface HomeResponseListener {

        void onResponse(ResponseHomepage.ResponseHomepageData response);

        void onFailure(Throwable t);
    }
    public void loadHomeData(HomeResponseListener listener) {

        service.getHomepage().enqueue(new Callback<ResponseHomepage>() {
            @Override
            public void onResponse(@NonNull Call<ResponseHomepage> call, @NonNull retrofit2.Response<ResponseHomepage> response) {
                if (response.body() != null
                        && response.isSuccessful()
                        && response.body().data != null) {
                    listener.onResponse(response.body().data);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseHomepage> call, @NonNull Throwable t) {
                listener.onFailure(t);
            }
        });

    }

    public interface CategoriesResponseListener {

        void onResponse(ResponseCategories response);

        void onFailure(Throwable t);
    }
    public void loadCategoriesData(CategoriesResponseListener listener) {

        service.getCategoryNews().enqueue(new Callback<ResponseCategories>() {
            @Override
            public void onResponse(@NonNull Call<ResponseCategories> call, @NonNull retrofit2.Response<ResponseCategories> response) {
                if (response.body() != null
                        && response.isSuccessful()
                        && response.body().data != null) {
                    listener.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseCategories> call, @NonNull Throwable t) {
                listener.onFailure(t);
            }
        });

    }

    public interface CommentResponseListener {

        void onResponse(ArrayList<ResponseComment.Comment> response);

        void onFailure(Throwable t);
    }
    public void loadCommentData(int id, CommentResponseListener listener) {

        service.getComment(id).enqueue(new Callback<ResponseComment>() {
            @Override
            public void onResponse(@NonNull Call<ResponseComment> call, @NonNull retrofit2.Response<ResponseComment> response) {
                if (response.body() != null
                        && response.isSuccessful()
                        && response.body().data != null) {
                    listener.onResponse(response.body().data);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseComment> call, @NonNull Throwable t) {
                listener.onFailure(t);
            }
        });

    }

    public interface DetailResponseListener {

        void onResponse(ResponseDetail.ResponseDetailData response);

        void onFailure(Throwable t);
    }
    public void loadDetailData(int id, DetailResponseListener listener) {

        service.getNewsDetail(id).enqueue(new Callback<ResponseDetail>() {
            @Override
            public void onResponse(@NonNull Call<ResponseDetail> call, @NonNull retrofit2.Response<ResponseDetail> response) {
                if (response.body() != null
                        && response.isSuccessful()
                        && response.body().data != null) {
                    listener.onResponse(response.body().data);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseDetail> call, @NonNull Throwable t) {
                listener.onFailure(t);
            }
        });

    }

    public interface NewsResponseListener {

        void onResponse(ResponseNewsList.ResponseData response);

        void onFailure(Throwable t);
    }
    public void loadCategoriesNewsData(int id, int page, NewsResponseListener listener) {

        service.getAllNews(id, page).enqueue(new Callback<ResponseNewsList>() {
            @Override
            public void onResponse(@NonNull Call<ResponseNewsList> call, @NonNull retrofit2.Response<ResponseNewsList> response) {
                if (response.body() != null
                        && response.isSuccessful()
                        && response.body().data != null) {
                    listener.onResponse(response.body().data);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseNewsList> call, @NonNull Throwable t) {
                listener.onFailure(t);
            }
        });

    }

    public interface PostRequestListener {

        void onResponse(ResponseCommentSend.ResponseBody request);

        void onFailure(Throwable t);
    }
    public void postComment(String news, String name, String email, String content, PostRequestListener listener) {

        ResponseCommentSend.ResponseBody data = new ResponseCommentSend.ResponseBody(String.valueOf(news), name, email, content);

        service.createPost(data).enqueue(new Callback<ResponseCommentSend.ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseCommentSend.ResponseBody> call, @NonNull Response<ResponseCommentSend.ResponseBody> request) {
                if (request.body() != null
                        && request.isSuccessful()
                        && request.code() >= 200) {
                    listener.onResponse(request.body());

                    Log.d("COMMENTPOST", "" + request.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseCommentSend.ResponseBody> call, @NonNull Throwable t) {
                listener.onFailure(t);
            }
        });
    }
    public void replyComment(String news, String reply_id, String name, String email, String content, PostRequestListener listener) {

        ResponseCommentSend.ResponseBody data = new ResponseCommentSend.ResponseBody(String.valueOf(news), String.valueOf(reply_id), name, email, content);

        service.createPost(data).enqueue(new Callback<ResponseCommentSend.ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseCommentSend.ResponseBody> call, @NonNull Response<ResponseCommentSend.ResponseBody> request) {
                if (request.body() != null
                        && request.isSuccessful()
                        && request.code() >= 200) {
                    listener.onResponse(request.body());

                    Log.d("COMMENTPOST", "" + request.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseCommentSend.ResponseBody> call, @NonNull Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    public interface VoteCommentListener {

        void onResponse(ResponseComment request);

        void onFailure(Throwable t);
    }
    public void voteComment(String id, VoteCommentListener listener) {

        service.postLike(Integer.parseInt(id), true).enqueue(new Callback<ResponseComment>() {
            @Override
            public void onResponse(@NonNull Call<ResponseComment> call, @NonNull Response<ResponseComment> request) {
                if (request.body() != null
                        && request.isSuccessful()
                        && request.code() >= 200
                        && request.body().data != null) {
                    listener.onResponse(request.body());
                    Log.d("LIKE", "" + request.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseComment> call, @NonNull Throwable t) {
                listener.onFailure(t);
            }
        });
    }
    public void unVoteComment(String id, VoteCommentListener listener) {

        service.postDislike(Integer.parseInt(id), true).enqueue(new Callback<ResponseComment>() {
            @Override
            public void onResponse(@NonNull Call<ResponseComment> call, @NonNull Response<ResponseComment> request) {
                if (request.body() != null
                        && request.isSuccessful()
                        && request.code() >= 200
                        && request.body().data != null) {
                    listener.onResponse(request.body());
                    Log.d("DISLIKE", "" + request.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseComment> call, @NonNull Throwable t) {
                listener.onFailure(t);
            }
        });
    }
}
