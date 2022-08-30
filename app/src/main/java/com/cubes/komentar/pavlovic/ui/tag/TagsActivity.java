package com.cubes.komentar.pavlovic.ui.tag;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cubes.komentar.databinding.ActivityTagsBinding;
import com.cubes.komentar.pavlovic.data.domain.News;
import com.cubes.komentar.pavlovic.data.source.repository.DataRepository;
import com.cubes.komentar.pavlovic.di.AppContainer;
import com.cubes.komentar.pavlovic.di.MyApplication;
import com.cubes.komentar.pavlovic.ui.details.DetailsActivity;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;

public class TagsActivity extends AppCompatActivity {

    private ActivityTagsBinding binding;
    private int id;
    private TagsAdapter adapter;
    private int nextPage = 2;
    private String title;
    private FirebaseAnalytics mFirebaseAnalytics;
    private DataRepository dataRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTagsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        id = getIntent().getExtras().getInt("id");
        title = getIntent().getExtras().getString("title");

        binding.textViewTag.setText(title);

        binding.imageBack.setOnClickListener(view1 -> finish());

        binding.swipeRefresh.setOnRefreshListener(() -> {
            setupRecyclerView();
            loadTagData();
            binding.progressBar.setVisibility(View.GONE);
        });

        AppContainer appContainer = ((MyApplication) getApplication()).appContainer;
        dataRepository = appContainer.dataRepository;

        setupRecyclerView();
        loadTagData();
        refresh();
    }

    public void setupRecyclerView() {

        binding.recyclerViewTags.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new TagsAdapter((newsId, newsIdList) -> {
            Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
            intent.putExtra("news_id", newsId);
            intent.putExtra("news_list_id", newsIdList);
            startActivity(intent);
        }, () -> dataRepository.loadTagNewsData(id, nextPage, new DataRepository.TagNewsResponseListener() {
            @Override
            public void onResponse(ArrayList<News> responseNewsList) {
                adapter.addNewsList(responseNewsList);

                nextPage++;
            }

            @Override
            public void onFailure(Throwable t) {
                binding.recyclerViewTags.setVisibility(View.GONE);
                binding.refresh.setVisibility(View.VISIBLE);
            }
        }));

        binding.recyclerViewTags.setAdapter(adapter);
    }

    public void loadTagData() {

        Bundle bundle = new Bundle();
        bundle.putString("tags", title);
        mFirebaseAnalytics.logEvent("selected_tags", bundle);

        binding.progressBar.setVisibility(View.VISIBLE);
        binding.recyclerViewTags.setVisibility(View.GONE);

        dataRepository.loadTagNewsData(id, 0, new DataRepository.TagNewsResponseListener() {
            @Override
            public void onResponse(ArrayList<News> responseNewsList) {

                nextPage = 2;
                adapter.setTagData(responseNewsList);

                binding.refresh.setVisibility(View.GONE);
                binding.progressBar.setVisibility(View.GONE);
                binding.recyclerViewTags.setVisibility(View.VISIBLE);
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
            loadTagData();
            binding.progressBar.setVisibility(View.GONE);
        });

    }
}