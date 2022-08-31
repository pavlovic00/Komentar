package com.cubes.komentar.pavlovic.data.source.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.cubes.komentar.pavlovic.data.domain.Category;
import com.cubes.komentar.pavlovic.data.domain.CategoryBox;
import com.cubes.komentar.pavlovic.data.domain.Comment;
import com.cubes.komentar.pavlovic.data.domain.News;
import com.cubes.komentar.pavlovic.data.domain.NewsDetail;
import com.cubes.komentar.pavlovic.data.domain.NewsList;
import com.cubes.komentar.pavlovic.data.domain.Tags;
import com.cubes.komentar.pavlovic.data.model.CategoryApi;
import com.cubes.komentar.pavlovic.data.model.CategoryBoxApi;
import com.cubes.komentar.pavlovic.data.model.CommentApi;
import com.cubes.komentar.pavlovic.data.model.NewsApi;
import com.cubes.komentar.pavlovic.data.model.TagsApi;
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

public class DataRepository {

    private final RetrofitService api;


    public DataRepository(RetrofitService api) {
        this.api = api;
    }


    public interface VideoResponseListener {

        void onResponse(ArrayList<News> response);

        void onFailure(Throwable t);
    }
    public void loadVideoData(int page, VideoResponseListener listener) {

        api.getVideo(page).enqueue(new Callback<ResponseNewsList>() {
            @Override
            public void onResponse(@NonNull Call<ResponseNewsList> call, @NonNull retrofit2.Response<ResponseNewsList> response) {

                if (response.body() != null
                        && response.isSuccessful()
                        && !response.body().data.news.isEmpty()) {
                    listener.onResponse(mapNews(response.body().data.news));
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

        api.getLatestNews(page).enqueue(new Callback<ResponseNewsList>() {
            @Override
            public void onResponse(@NonNull Call<ResponseNewsList> call, @NonNull retrofit2.Response<ResponseNewsList> response) {
                if (response.body() != null
                        && response.isSuccessful()
                        && !response.body().data.news.isEmpty()) {
                    listener.onResponse(mapNews(response.body().data.news));
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

        api.getSearch(term, page).enqueue(new Callback<ResponseNewsList>() {
            @Override
            public void onResponse(@NonNull Call<ResponseNewsList> call, @NonNull retrofit2.Response<ResponseNewsList> response) {
                if (response.body() != null
                        && response.isSuccessful()) {
                    listener.onResponse(mapNews(response.body().data.news));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseNewsList> call, @NonNull Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    public interface TagNewsResponseListener {

        void onResponse(ArrayList<News> responseNewsList);

        void onFailure(Throwable t);
    }
    public void loadTagNewsData(int id, int page, TagNewsResponseListener listener) {

        api.getTag(id, page).enqueue(new Callback<ResponseNewsList>() {
            @Override
            public void onResponse(@NonNull Call<ResponseNewsList> call, @NonNull retrofit2.Response<ResponseNewsList> response) {
                if (response.body() != null
                        && response.isSuccessful()
                        && !response.body().data.news.isEmpty()) {
                    listener.onResponse(mapNews(response.body().data.news));
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

        api.getHomepage().enqueue(new Callback<ResponseHomepage>() {
            @Override
            public void onResponse(@NonNull Call<ResponseHomepage> call, @NonNull retrofit2.Response<ResponseHomepage> response) {
                if (response.body() != null
                        && response.isSuccessful()
                        && response.body().data != null) {

                    NewsList newsList = new NewsList();

                    newsList.slider = mapNews(response.body().data.slider);
                    newsList.videos = mapNews(response.body().data.videos);
                    newsList.mostRead = mapNews(response.body().data.most_read);
                    newsList.mostCommented = mapNews(response.body().data.most_comented);
                    newsList.top = mapNews(response.body().data.top);
                    newsList.editorsChoice = mapNews(response.body().data.editors_choice);
                    newsList.latest = mapNews(response.body().data.latest);

                    ArrayList<CategoryBox> categoryBoxes = new ArrayList<>();

                    for (CategoryBoxApi model : response.body().data.category) {

                        CategoryBox categoryBox = new CategoryBox();

                        categoryBox.news = mapNews(model.news);
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

        api.getCategoryNews().enqueue(new Callback<ResponseCategories>() {
            @Override
            public void onResponse(@NonNull Call<ResponseCategories> call, @NonNull retrofit2.Response<ResponseCategories> response) {
                if (response.body() != null
                        && response.body().data != null
                        && response.isSuccessful()) {

                    ArrayList<Category> categories = new ArrayList<>();

                    for (CategoryApi categoryApi : response.body().data) {

                        Category category = mapCategory(categoryApi);

                        categories.add(category);

                    }

                    listener.onResponse(categories);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseCategories> call, @NonNull Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    public interface CategoriesNewsResponseListener {

        void onResponse(ArrayList<News> response);

        void onFailure(Throwable t);
    }
    public void loadCategoriesNewsData(int id, int page, CategoriesNewsResponseListener listener) {

        api.getAllNews(id, page).enqueue(new Callback<ResponseNewsList>() {
            @Override
            public void onResponse(@NonNull Call<ResponseNewsList> call, @NonNull retrofit2.Response<ResponseNewsList> response) {
                if (response.body() != null
                        && response.isSuccessful()) {
                    listener.onResponse(mapNews(response.body().data.news));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseNewsList> call, @NonNull Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    public interface DetailResponseListener {

        void onResponse(NewsDetail response);

        void onFailure(Throwable t);
    }
    public void loadDetailData(int id, DetailResponseListener listener) {

        api.getNewsDetail(id).enqueue(new Callback<ResponseDetail>() {
            @Override
            public void onResponse(@NonNull Call<ResponseDetail> call, @NonNull retrofit2.Response<ResponseDetail> response) {
                if (response.body() != null
                        && response.isSuccessful()
                        && response.body().data != null) {

                    NewsDetail newsDetail = new NewsDetail();
                    ArrayList<Tags> tags = new ArrayList<>();

                    for (TagsApi tagsApi : response.body().data.tags){
                        Tags tag = new Tags();

                        tag.id = tagsApi.id;
                        tag.title = tagsApi.title;

                        tags.add(tag);
                    }

                    newsDetail.tags = tags;

                    newsDetail.id = response.body().data.id;
                    newsDetail.commentEnabled = response.body().data.comment_enabled == 1;
                    newsDetail.commentsCount = response.body().data.comments_count;
                    newsDetail.relatedNews = mapNews(response.body().data.related_news);
                    newsDetail.topComments = mapComment(response.body().data.comments_top_n);
                    newsDetail.url = response.body().data.url;
                    newsDetail.title = response.body().data.title;

                    listener.onResponse(newsDetail);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseDetail> call, @NonNull Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    public interface CommentResponseListener {

        void onResponse(ArrayList<Comment> response);

        void onFailure(Throwable t);
    }
    public void loadCommentData(int id, CommentResponseListener listener) {

        api.getComment(id).enqueue(new Callback<ResponseComment>() {
            @Override
            public void onResponse(@NonNull Call<ResponseComment> call, @NonNull retrofit2.Response<ResponseComment> response) {
                if (response.body() != null
                        && response.isSuccessful()
                        && response.body().data != null) {

                    listener.onResponse(mapComment(response.body().data));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseComment> call, @NonNull Throwable t) {
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

        api.createPost(data).enqueue(new Callback<RequestComment.RequestBody>() {
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

        api.createPost(data).enqueue(new Callback<RequestComment.RequestBody>() {
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

        api.postLike(Integer.parseInt(id), true).enqueue(new Callback<ResponseComment>() {
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

        api.postDislike(Integer.parseInt(id), true).enqueue(new Callback<ResponseComment>() {
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


    private ArrayList<News> mapNews(ArrayList<NewsApi> newsFromResponse) {

        ArrayList<News> newsList = new ArrayList<>();

        for (NewsApi newsItemApi : newsFromResponse) {

            News news = new News();

            news.id = newsItemApi.id;
            news.image = newsItemApi.image;
            news.title = newsItemApi.title;
            news.createdAt = newsItemApi.created_at;
            news.url = newsItemApi.url;

            news.category = mapCategory(newsItemApi.category);

            newsList.add(news);

        }

        return newsList;
    }
    private Category mapCategory(CategoryApi categoryApi) {

        Category category = new Category();

        category.id = categoryApi.id;
        category.type = categoryApi.type;
        category.name = categoryApi.name;
        category.color = categoryApi.color;

        ArrayList<Category> subcategories = new ArrayList<>();

        if (categoryApi.subcategories != null && !categoryApi.subcategories.isEmpty()) {

            for (CategoryApi subcategoryApi : categoryApi.subcategories) {

                Category subcategory = new Category();

                subcategory.id = subcategoryApi.id;
                subcategory.type = subcategoryApi.type;
                subcategory.name = subcategoryApi.name;
                subcategory.color = subcategoryApi.color;

                subcategories.add(subcategory);
            }

        }
        category.subcategories = subcategories;

        return category;
    }
    private ArrayList<Comment> mapComment(ArrayList<CommentApi> commentsFromResponse) {

        ArrayList<Comment> allComments = new ArrayList<>();

        for (CommentApi commentApi : commentsFromResponse) {

            Comment comment = new Comment();

            comment.likes = commentApi.negative_votes;
            comment.dislikes = commentApi.positive_votes;
            comment.createdAt = commentApi.created_at;
            comment.newsId = commentApi.news;
            comment.name = commentApi.name;
            comment.parentCommentId = commentApi.parent_comment;
            comment.commentId = commentApi.id;
            comment.content = commentApi.content;

            if (commentApi.children != null && !commentApi.children.isEmpty()) {
                comment.children = mapComment(commentApi.children);
            }

            allComments.add(comment);
        }

        return allComments;
    }
}
