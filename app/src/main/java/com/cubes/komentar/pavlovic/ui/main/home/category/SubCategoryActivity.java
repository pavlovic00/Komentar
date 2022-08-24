package com.cubes.komentar.pavlovic.ui.main.home.category;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cubes.komentar.databinding.ActivityCategoryBinding;
import com.cubes.komentar.pavlovic.data.domain.News;
import com.cubes.komentar.pavlovic.data.source.repository.DataRepository;
import com.cubes.komentar.pavlovic.ui.details.NewsDetailActivity;
import com.cubes.komentar.pavlovic.ui.main.latest.LatestAdapter;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;

public class SubCategoryActivity extends AppCompatActivity {

    private ActivityCategoryBinding binding;
    private LatestAdapter adapter;
    private int id;
    private int nextPage = 2;
    private String category;
    private FirebaseAnalytics mFirebaseAnalytics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoryBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        category = getIntent().getExtras().getString("category");
        id = getIntent().getExtras().getInt("id");

        binding.textViewTag.setText(category);

        binding.imageBack.setOnClickListener(view1 -> finish());

        binding.swipeRefresh.setOnRefreshListener(() -> {
            setupRecyclerView();
            loadCategoryData();
            binding.progressBar.setVisibility(View.GONE);
        });

        setupRecyclerView();
        loadCategoryData();
        refresh();
    }

    public void setupRecyclerView() {

        binding.recyclerViewCategory.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new LatestAdapter();
        binding.recyclerViewCategory.setAdapter(adapter);

        adapter.setNewsListener(news -> {
            Intent i = new Intent(getApplicationContext(), NewsDetailActivity.class);
            i.putExtra("id", news.id);
            i.putExtra("title", news.title);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplicationContext().startActivity(i);
        });

        adapter.setLoadingNewsListener(() -> DataRepository.getInstance().loadCategoryNewsData(id, nextPage, new DataRepository.CategoryNewsResponseListener() {
            @Override
            public void onResponse(ArrayList<News> responseNewsList) {
                adapter.addNewsList(responseNewsList);

                nextPage++;
            }

            @Override
            public void onFailure(Throwable t) {
                binding.recyclerViewCategory.setVisibility(View.GONE);
                binding.refresh.setVisibility(View.VISIBLE);
            }

        }));

    }

    public void loadCategoryData() {

        Bundle bundle = new Bundle();
        bundle.putString("subcategory", category);
        mFirebaseAnalytics.logEvent("selected_subcategory", bundle);

        binding.progressBar.setVisibility(View.VISIBLE);
        binding.recyclerViewCategory.setVisibility(View.GONE);

        DataRepository.getInstance().loadCategoryNewsData(id, 1, new DataRepository.CategoryNewsResponseListener() {
            @Override
            public void onResponse(ArrayList<News> responseNewsList) {

                nextPage = 2;
                adapter.setData(responseNewsList);

                binding.refresh.setVisibility(View.GONE);
                binding.progressBar.setVisibility(View.GONE);
                binding.recyclerViewCategory.setVisibility(View.VISIBLE);
                binding.swipeRefresh.setRefreshing(false);
            }

            @Override
            public void onFailure(Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                binding.refresh.setVisibility(View.VISIBLE);
                binding.swipeRefresh.setRefreshing(false);
            }
        });

    }

    public void refresh() {

        binding.refresh.setOnClickListener(view -> {

            RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotate.setDuration(300);
            binding.refresh.startAnimation(rotate);
            setupRecyclerView();
            loadCategoryData();
            binding.progressBar.setVisibility(View.GONE);
        });
    }
}