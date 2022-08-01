package com.cubes.komentar.pavlovic.ui.main.home.category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.cubes.komentar.databinding.ActivityCategoryBinding;
import com.cubes.komentar.pavlovic.data.model.News;
import com.cubes.komentar.pavlovic.data.repository.DataRepository;
import com.cubes.komentar.pavlovic.data.response.ResponseNewsList;
import com.cubes.komentar.pavlovic.data.response.ResponseCategories;
import com.cubes.komentar.pavlovic.data.tools.LoadingNewsListener;
import com.cubes.komentar.pavlovic.data.tools.NewsListener;
import com.cubes.komentar.pavlovic.ui.main.latest.LatestAdapter;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {

    private ActivityCategoryBinding binding;
    private String category;
    private ResponseCategories.ResponseCategoriesData categoryData;
    public ArrayList<News> newsList;
    private LatestAdapter adapter;
    private int id;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoryBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        category = getIntent().getExtras().getString("category");
        id = getIntent().getExtras().getInt("id");

        binding.textViewTag.setText(category);


        binding.imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        loadCategoryData();
    }

    public void loadCategoryData() {

        DataRepository.getInstance().loadCategoryData(id, page, new DataRepository.CategoryResponseListener() {
            @Override
            public void onResponse(ResponseNewsList responseNewsList) {
                if (responseNewsList != null) {
                    newsList = responseNewsList.data.news;
                }
                updateUI();
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

    }


    public void updateUI() {

        binding.recyclerViewCategory.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new LatestAdapter(getApplicationContext(), newsList);

        adapter.setNewsListener(new NewsListener() {
            @Override
            public void onNewsCLicked(News news) {
                DataRepository.getInstance().getNewsDetails(CategoryActivity.this, news);
            }
        });

        loadMoreNews();

        binding.recyclerViewCategory.setAdapter(adapter);

    }


    public void loadMoreNews() {

        adapter.setLoadingNewsListener(new LoadingNewsListener() {
            @Override
            public void loadMoreNews(int page) {
                DataRepository.getInstance().loadCategoryData(id, page, new DataRepository.CategoryResponseListener() {
                    @Override
                    public void onResponse(ResponseNewsList responseNewsList) {
                        adapter.addNewsList(responseNewsList.data.news);

                        if (responseNewsList.data.news.size() < 20) {
                            adapter.setFinished(true);
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        adapter.setFinished(true);
                    }

                });
            }
        });

    }
}