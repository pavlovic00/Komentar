package com.cubes.komentar.pavlovic.data.source.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.cubes.komentar.pavlovic.data.domain.Category;
import com.cubes.komentar.pavlovic.data.domain.CategoryBox;
import com.cubes.komentar.pavlovic.data.domain.News;
import com.cubes.komentar.pavlovic.data.domain.NewsList;
import com.cubes.komentar.pavlovic.data.model.CategoryApi;
import com.cubes.komentar.pavlovic.data.model.CategoryBoxApi;
import com.cubes.komentar.pavlovic.data.model.CommentApi;
import com.cubes.komentar.pavlovic.data.model.NewsApi;
import com.cubes.komentar.pavlovic.data.model.NewsDetailApi;
import com.cubes.komentar.pavlovic.data.source.networking.RetrofitService;
import com.cubes.komentar.pavlovic.data.source.response.RequestComment;
import com.cubes.komentar.pavlovic.data.source.response.ResponseCategories;
import com.cubes.komentar.pavlovic.data.source.response.ResponseComment;
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

        void onResponse(ArrayList<News> response);

        void onFailure(Throwable t);
    }
    public void loadVideoData(int page, VideoResponseListener listener) {

        service.getVideo(page).enqueue(new Callback<ResponseNewsList>() {
            @Override
            public void onResponse(@NonNull Call<ResponseNewsList> call, @NonNull retrofit2.Response<ResponseNewsList> response) {

                if (response.body() != null
                        && response.isSuccessful()
                        && !response.body().data.news.isEmpty()) {
                    listener.onResponse(mapNewsResponse(response.body().data.news));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseNewsList> call, @NonNull Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    public interface LatestResponseListener {

        void onResponse(ArrayList<News> response);

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
                    listener.onResponse(mapNewsResponse(response.body().data.news));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseNewsList> call, @NonNull Throwable t) {
                listener.onFailure(t);
            }
        });

    }

    public interface SearchResponseListener {

        void onResponse(ArrayList<News> responseNewsList);

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
                    listener.onResponse(mapNewsResponse(response.body().data.news));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseNewsList> call, @NonNull Throwable t) {
                listener.onFailure(t);
            }
        });

    }

    public interface CategoryResponseListener {

        void onResponse(ArrayList<News> responseNewsList);

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
                    listener.onResponse(mapNewsResponse(response.body().data.news));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseNewsList> call, @NonNull Throwable t) {
                listener.onFailure(t);
            }
        });

    }

    public interface TagResponseListener {

        void onResponse(ArrayList<News> responseNewsList);

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
                    listener.onResponse(mapNewsResponse(response.body().data.news));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseNewsList> call, @NonNull Throwable t) {
                listener.onFailure(t);
            }
        });

    }

    public interface HomeResponseListener {

        void onResponse(NewsList response);

        void onFailure(Throwable t);
    }
    public void loadHomeData(HomeResponseListener listener) {

        service.getHomepage().enqueue(new Callback<ResponseHomepage>() {
            @Override
            public void onResponse(@NonNull Call<ResponseHomepage> call, @NonNull retrofit2.Response<ResponseHomepage> response) {
                if (response.body() != null
                        && response.isSuccessful()
                        && response.body().data != null) {


                    NewsList newsList = new NewsList();

                    newsList.slider = mapNewsResponse(response.body().data.slider);
                    newsList.videos = mapNewsResponse(response.body().data.videos);
                    newsList.most_read = mapNewsResponse(response.body().data.most_read);
                    newsList.most_commented = mapNewsResponse(response.body().data.most_comented);
                    newsList.top = mapNewsResponse(response.body().data.top);
                    newsList.editors_choice = mapNewsResponse(response.body().data.editors_choice);
                    newsList.latest = mapNewsResponse(response.body().data.latest);

                    ArrayList<CategoryBox> categoryBoxes = new ArrayList<>();

                    for (CategoryBoxApi model : response.body().data.category) {

                        CategoryBox categoryBox = new CategoryBox();

                        categoryBox.news = mapNewsResponse(model.news);
                        categoryBox.color = model.color;
                        categoryBox.id = model.id;
                        categoryBox.title = model.title;

                        categoryBoxes.add(categoryBox);
                    }

                    newsList.category = categoryBoxes;

                    listener.onResponse(newsList);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseHomepage> call, @NonNull Throwable t) {
                listener.onFailure(t);
            }
        });

    }

    public interface CategoriesResponseListener {

        void onResponse(ArrayList<Category> response);

        void onFailure(Throwable t);
    }
    public void loadCategoriesData(CategoriesResponseListener listener) {

        service.getCategoryNews().enqueue(new Callback<ResponseCategories>() {
            @Override
            public void onResponse(@NonNull Call<ResponseCategories> call, @NonNull retrofit2.Response<ResponseCategories> response) {
                if (response.body() != null
                        && response.isSuccessful()
                        && response.body().data != null) {
                    listener.onResponse(response.body().data);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseCategories> call, @NonNull Throwable t) {
                listener.onFailure(t);
            }
        });

    }

    public interface CommentResponseListener {

        void onResponse(ArrayList<CommentApi> response);

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

        void onResponse(NewsDetailApi response);

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

        void onResponse(ArrayList<News> response);

        void onFailure(Throwable t);
    }
    public void loadCategoriesNewsData(int id, int page, NewsResponseListener listener) {

        service.getAllNews(id, page).enqueue(new Callback<ResponseNewsList>() {
            @Override
            public void onResponse(@NonNull Call<ResponseNewsList> call, @NonNull retrofit2.Response<ResponseNewsList> response) {
                if (response.body() != null
                        && response.isSuccessful()
                        && response.body().data != null) {
                    listener.onResponse(mapNewsResponse(response.body().data.news));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseNewsList> call, @NonNull Throwable t) {
                listener.onFailure(t);
            }
        });

    }

    public interface PostRequestListener {

        void onResponse(RequestComment.RequestBody request);

        void onFailure(Throwable t);
    }
    public void postComment(String news, String name, String email, String content, PostRequestListener listener) {

        RequestComment.RequestBody data = new RequestComment.RequestBody(String.valueOf(news), name, email, content);

        service.createPost(data).enqueue(new Callback<RequestComment.RequestBody>() {
            @Override
            public void onResponse(@NonNull Call<RequestComment.RequestBody> call, @NonNull Response<RequestComment.RequestBody> request) {
                if (request.body() != null
                        && request.isSuccessful()
                        && request.code() >= 200) {
                    listener.onResponse(request.body());

                    Log.d("COMMENTPOST", "" + request.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<RequestComment.RequestBody> call, @NonNull Throwable t) {
                listener.onFailure(t);
            }
        });
    }
    public void replyComment(String news, String reply_id, String name, String email, String content, PostRequestListener listener) {

        RequestComment.RequestBody data = new RequestComment.RequestBody(String.valueOf(news), String.valueOf(reply_id), name, email, content);

        service.createPost(data).enqueue(new Callback<RequestComment.RequestBody>() {
            @Override
            public void onResponse(@NonNull Call<RequestComment.RequestBody> call, @NonNull Response<RequestComment.RequestBody> request) {
                if (request.body() != null
                        && request.isSuccessful()
                        && request.code() >= 200) {
                    listener.onResponse(request.body());

                    Log.d("COMMENTPOST", "" + request.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<RequestComment.RequestBody> call, @NonNull Throwable t) {
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


    private ArrayList<News> mapNewsResponse(ArrayList<NewsApi> newsFromResponse) {

        ArrayList<News> newsList = new ArrayList<>();

        for (NewsApi newsItemApi : newsFromResponse) {

            News news = new News();

            news.id = newsItemApi.id;
            news.image = newsItemApi.image;
            news.title = newsItemApi.title;
            news.created_at = newsItemApi.created_at;
            news.url = newsItemApi.url;

            Category category = new Category();

            category.id = newsItemApi.category.id;
            category.type = newsItemApi.category.type;
            category.name = newsItemApi.category.name;
            category.color = newsItemApi.category.color;

            ArrayList<Category> subcategories = new ArrayList<>();

            if (newsItemApi.category.subcategories != null && !newsItemApi.category.subcategories.isEmpty()) {

                for (CategoryApi subcategoryApi : newsItemApi.category.subcategories) {

                    Category subcategory = new Category();

                    subcategory.id = subcategoryApi.id;
                    subcategory.type = subcategoryApi.type;
                    subcategory.name = subcategoryApi.name;
                    subcategory.color = subcategoryApi.color;

                    subcategories.add(subcategory);
                }

            }

            category.subcategories = subcategories;

            news.category = category;

            newsList.add(news);
        }

        return newsList;
    }

}
