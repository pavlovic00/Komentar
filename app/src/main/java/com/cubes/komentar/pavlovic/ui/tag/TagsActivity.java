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
import com.cubes.komentar.pavlovic.ui.tools.LoadingNewsListener;
import com.cubes.komentar.pavlovic.ui.tools.NewsListener;
import com.cubes.komentar.pavlovic.ui.details.NewsDetailActivity;

public class TagsActivity extends AppCompatActivity {

    private ActivityTagsBinding binding;
    private int id;
    private String title;
    private TagsAdapter adapter;
    private int nextPage = 1;


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
        adapter = new TagsAdapter();
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
            public void loadMoreNews() {
                DataRepository.getInstance().loadTagData(id, nextPage, new DataRepository.TagResponseListener() {
                    @Override
                    public void onResponse(ResponseNewsList responseNewsList) {
                        adapter.addNewsList(responseNewsList.data.news);

                        nextPage++;
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        binding.recyclerViewTags.setVisibility(View.GONE);
                        binding.refresh.setVisibility(View.VISIBLE);
                    }
                });
            }
        });

    }

    public void loadTagData() {

        binding.progressBar.setVisibility(View.VISIBLE);
        binding.recyclerViewTags.setVisibility(View.GONE);

        DataRepository.getInstance().loadTagData(id, nextPage, new DataRepository.TagResponseListener() {
            @Override
            public void onResponse(ResponseNewsList responseNewsList) {

                nextPage++;
                adapter.setDataTags(responseNewsList);

                binding.refresh.setVisibility(View.GONE);
                binding.progressBar.setVisibility(View.GONE);
                binding.recyclerViewTags.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
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
                setupRecyclerView();
                loadTagData();
                binding.progressBar.setVisibility(View.GONE);
            }
        });

    }
}