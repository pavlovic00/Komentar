package com.cubes.komentar.pavlovic.ui.tag;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import com.cubes.komentar.databinding.ActivityTagsBinding;
import com.cubes.komentar.pavlovic.data.model.News;
import com.cubes.komentar.pavlovic.data.repository.DataRepository;
import com.cubes.komentar.pavlovic.data.response.ResponseNewsList;
import com.cubes.komentar.pavlovic.data.tools.LoadingNewsListener;
import com.cubes.komentar.pavlovic.data.tools.NewsListener;
import com.cubes.komentar.pavlovic.ui.details.NewsDetailActivity;
import com.cubes.komentar.pavlovic.ui.main.search.SearchAdapter;

public class TagsActivity extends AppCompatActivity {

    private ActivityTagsBinding binding;
    private int id;
    private String title;
    private SearchAdapter adapter;
    private int page = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTagsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        id = getIntent().getExtras().getInt("id");
        title = getIntent().getExtras().getString("title");

        binding.textViewTag.setText(title);

        binding.imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        setupRecyclerView();
        loadTagData();
        refresh();
    }

    public void setupRecyclerView() {

        binding.recyclerViewTags.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new SearchAdapter();
        binding.recyclerViewTags.setAdapter(adapter);

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
                DataRepository.getInstance().loadTagData(id, page, new DataRepository.TagResponseListener() {
                    @Override
                    public void onResponse(ResponseNewsList responseNewsList) {
                        adapter.addNewsList(responseNewsList.data.news);

                        if (responseNewsList.data.news.size() < 20) {
                            adapter.setFinished(true);
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        binding.recyclerViewTags.setVisibility(View.GONE);
                        binding.refresh.setVisibility(View.VISIBLE);
                        adapter.setFinished(true);
                    }
                });
            }
        });

    }

    public void loadTagData() {

        DataRepository.getInstance().loadTagData(id, page, new DataRepository.TagResponseListener() {
            @Override
            public void onResponse(ResponseNewsList responseNewsList) {

                adapter.setDataTags(responseNewsList);

                binding.refresh.setVisibility(View.GONE);
                binding.recyclerViewTags.setVisibility(View.VISIBLE);
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
                loadTagData();
            }
        });

    }
}