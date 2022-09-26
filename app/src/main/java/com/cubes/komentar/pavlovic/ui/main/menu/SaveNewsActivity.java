package com.cubes.komentar.pavlovic.ui.main.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cubes.komentar.R;
import com.cubes.komentar.databinding.ActivitySaveNewsBinding;
import com.cubes.komentar.pavlovic.data.domain.SaveNews;
import com.cubes.komentar.pavlovic.ui.details.DetailsActivity;
import com.cubes.komentar.pavlovic.ui.tools.SharedPrefs;
import com.cubes.komentar.pavlovic.ui.tools.listener.NewsListener;

import java.util.ArrayList;

public class SaveNewsActivity extends AppCompatActivity {

    private ActivitySaveNewsBinding binding;
    private ArrayList<SaveNews> saveNewsList = new ArrayList<>();
    private SaveNewsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySaveNewsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.imageBack.setOnClickListener(view1 -> finish());

        binding.swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.purple_light));
        binding.swipeRefresh.setOnRefreshListener(() -> {
            setupRecyclerView();
            loadSaveNewsData();
            binding.progressBar.setVisibility(View.GONE);
        });

        binding.progressBar.setVisibility(View.VISIBLE);
        binding.recyclerViewSaveNews.setVisibility(View.GONE);

        setupRecyclerView();
        loadSaveNewsData();
        refresh();
    }

    public void setupRecyclerView() {

        if (SharedPrefs.showNewsFromPref(SaveNewsActivity.this) != null) {
            saveNewsList = (ArrayList<SaveNews>) SharedPrefs.showNewsFromPref(SaveNewsActivity.this);
        }

        binding.recyclerViewSaveNews.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new SaveNewsAdapter(new NewsListener() {
            @Override
            public void onUnSaveClicked(int id, String title) {
                SaveNews saveNews = new SaveNews(id, title);

                for (int i = 0; i < saveNewsList.size(); i++) {
                    if (saveNews.id == saveNewsList.get(i).id) {
                        saveNewsList.remove(saveNewsList.get(i));
                        SharedPrefs.saveNewsInPref(SaveNewsActivity.this, saveNewsList);
                        setupRecyclerView();
                        loadSaveNewsData();
                    }
                }

            }

            @Override
            public void onNewsClickedVP(int newsId, int[] newsIdList) {
                Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                intent.putExtra("news_id", newsId);
                intent.putExtra("news_list_id", newsIdList);
                startActivity(intent);
            }
        });

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0
        ) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                adapter.onRowMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                SharedPrefs.showNewsFromPref(SaveNewsActivity.this).clear();
                SharedPrefs.saveNewsInPref(SaveNewsActivity.this, adapter.getNewList());

                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }
        };

        binding.recyclerViewSaveNews.setAdapter(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(binding.recyclerViewSaveNews);
    }

    public void loadSaveNewsData() {

        saveNewsList = (ArrayList<SaveNews>) SharedPrefs.showNewsFromPref(this);
        adapter.setSaveNewsData(saveNewsList);

        binding.refresh.setVisibility(View.GONE);
        binding.progressBar.setVisibility(View.GONE);
        binding.recyclerViewSaveNews.setVisibility(View.VISIBLE);
        binding.swipeRefresh.setRefreshing(false);
    }

    public void refresh() {

        binding.refresh.setOnClickListener(view -> {
            RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotate.setDuration(300);
            binding.refresh.startAnimation(rotate);
            setupRecyclerView();
            loadSaveNewsData();
            binding.progressBar.setVisibility(View.GONE);
        });
    }
}