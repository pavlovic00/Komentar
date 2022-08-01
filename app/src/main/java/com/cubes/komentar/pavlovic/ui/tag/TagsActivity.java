package com.cubes.komentar.pavlovic.ui.tag;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.cubes.komentar.databinding.ActivityTagsBinding;
import com.cubes.komentar.pavlovic.data.model.News;
import com.cubes.komentar.pavlovic.data.repository.DataRepository;
import com.cubes.komentar.pavlovic.data.response.ResponseNewsList;
import com.cubes.komentar.pavlovic.data.tools.LoadingNewsListener;
import com.cubes.komentar.pavlovic.data.tools.NewsListener;
import com.cubes.komentar.pavlovic.ui.main.search.SearchAdapter;

import java.util.ArrayList;

public class TagsActivity extends AppCompatActivity {

    private ActivityTagsBinding binding;
    private int id;
    private String title;
    private ArrayList<News> newsList;
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

        loadTagData();

        binding.imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void loadTagData() {

        DataRepository.getInstance().loadTagData(id, page, new DataRepository.TagResponseListener() {
            @Override
            public void onResponse(ResponseNewsList responseNewsList) {
                newsList = responseNewsList.data.news;
                updateUI();
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

    }

    public void updateUI() {
        binding.recyclerViewTags.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new SearchAdapter(getApplicationContext(), newsList);

        adapter.setNewsListener(new NewsListener() {
            @Override
            public void onNewsCLicked(News news) {
                DataRepository.getInstance().getNewsDetails(getApplicationContext(), news);
            }
        });

        loadMoreNews();

        binding.recyclerViewTags.setAdapter(adapter);
    }

    public void loadMoreNews() {

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

                    }
                });
            }
        });

    }
}