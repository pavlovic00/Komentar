package com.cubes.komentar.pavlovic.ui.main.home.category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import com.cubes.komentar.databinding.ActivityCategoryBinding;
import com.cubes.komentar.pavlovic.data.model.News;
import com.cubes.komentar.pavlovic.data.repository.DataRepository;
import com.cubes.komentar.pavlovic.data.response.ResponseNewsList;
import com.cubes.komentar.pavlovic.data.tools.LoadingNewsListener;
import com.cubes.komentar.pavlovic.data.tools.NewsListener;
import com.cubes.komentar.pavlovic.ui.details.NewsDetailActivity;
import com.cubes.komentar.pavlovic.ui.main.latest.LatestAdapter;

public class CategoryActivity extends AppCompatActivity {

    private ActivityCategoryBinding binding;
    private String category;
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

        setupRecyclerView();
        loadCategoryData();
        refresh();
    }

    public void setupRecyclerView() {

        binding.recyclerViewCategory.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new LatestAdapter();
        binding.recyclerViewCategory.setAdapter(adapter);

        adapter.setNewsListener(new NewsListener() {
            @Override
            public void onNewsCLicked(News news) {
                Intent i = new Intent(getApplicationContext(), NewsDetailActivity.class);
                i.putExtra("id", news.id);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(i);
            }
        });

        adapter.setLoadingNewsListener(new LoadingNewsListener() {
            @Override
            public void loadMoreNews(int page) {
                DataRepository.getInstance().loadCategoryData(id, page, new DataRepository.CategoryResponseListener() {
                    @Override
                    public void onResponse(ResponseNewsList.ResponseData responseNewsList) {
                        adapter.addNewsList(responseNewsList.news);

                        if (responseNewsList.news.size() < 20) {
                            adapter.setFinished(true);
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        binding.recyclerViewCategory.setVisibility(View.GONE);
                        binding.refresh.setVisibility(View.VISIBLE);
                        adapter.setFinished(true);
                    }

                });
            }
        });

    }

    public void loadCategoryData() {

        DataRepository.getInstance().loadCategoryData(id, page, new DataRepository.CategoryResponseListener() {
            @Override
            public void onResponse(ResponseNewsList.ResponseData responseNewsList) {

                adapter.setData(responseNewsList);

                binding.refresh.setVisibility(View.GONE);
                binding.recyclerViewCategory.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Throwable t) {
                binding.refresh.setVisibility(View.VISIBLE);
            }
        });

    }

    public void refresh() {

        binding.refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotate.setDuration(300);
                binding.refresh.startAnimation(rotate);
                loadCategoryData();
            }
        });
    }
}