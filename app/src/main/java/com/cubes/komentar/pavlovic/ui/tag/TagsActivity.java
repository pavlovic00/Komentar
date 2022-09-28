package com.cubes.komentar.pavlovic.ui.tag;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cubes.komentar.R;
import com.cubes.komentar.databinding.ActivityTagsBinding;
import com.cubes.komentar.pavlovic.data.domain.News;
import com.cubes.komentar.pavlovic.data.domain.SaveNews;
import com.cubes.komentar.pavlovic.data.source.repository.DataRepository;
import com.cubes.komentar.pavlovic.di.AppContainer;
import com.cubes.komentar.pavlovic.di.MyApplication;
import com.cubes.komentar.pavlovic.ui.comments.AllCommentActivity;
import com.cubes.komentar.pavlovic.ui.details.DetailsActivity;
import com.cubes.komentar.pavlovic.ui.tools.MyMethodsClass;
import com.cubes.komentar.pavlovic.ui.tools.SharedPrefs;
import com.cubes.komentar.pavlovic.ui.tools.listener.NewsListener;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;

public class TagsActivity extends AppCompatActivity {

    private ActivityTagsBinding binding;
    private int id;
    private TagsAdapter adapter;
    private int nextPage = 2;
    private String title;
    private FirebaseAnalytics mFirebaseAnalytics;
    private ArrayList<SaveNews> saveNewsList = new ArrayList<>();
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

        binding.swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.purple_light));
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
        if (SharedPrefs.showNewsFromPref(TagsActivity.this) != null) {
            saveNewsList = (ArrayList<SaveNews>) SharedPrefs.showNewsFromPref(TagsActivity.this);
        }

        binding.recyclerViewTags.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new TagsAdapter((new NewsListener() {
            @Override
            public void onNewsClickedVP(int newsId, int[] newsIdList) {
                Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                intent.putExtra("news_id", newsId);
                intent.putExtra("news_list_id", newsIdList);
                startActivity(intent);
            }

            @Override
            public void onCommentNewsClicked(int id) {
                Intent commentIntent = new Intent(getApplicationContext(), AllCommentActivity.class);
                commentIntent.putExtra("news_id", id);
                startActivity(commentIntent);
            }

            @Override
            public void onShareNewsClicked(String url) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_SEND);
                i.putExtra(Intent.EXTRA_TEXT, url);
                i.setType("text/plain");
                startActivity(Intent.createChooser(i, null));
            }

            @Override
            public void onSaveClicked(int id, String title) {
                SaveNews saveNews = new SaveNews(id, title);

                if (SharedPrefs.showNewsFromPref(TagsActivity.this) != null) {
                    saveNewsList = (ArrayList<SaveNews>) SharedPrefs.showNewsFromPref(TagsActivity.this);

                    for (int i = 0; i < saveNewsList.size(); i++) {
                        if (saveNews.id == saveNewsList.get(i).id) {
                            saveNewsList.remove(saveNewsList.get(i));
                            SharedPrefs.saveNewsInPref(TagsActivity.this, saveNewsList);
                            Toast.makeText(getApplicationContext(), "Uspešno ste izbacili vest iz liste.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                }
                saveNewsList.add(saveNews);
                SharedPrefs.saveNewsInPref(TagsActivity.this, saveNewsList);
                Toast.makeText(getApplicationContext(), "Uspešno ste sačuvali vest.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean isSaved(int id) {
                return MyMethodsClass.isSaved(id, TagsActivity.this);
            }

        }), () -> dataRepository.loadTagNewsData(id, nextPage, new DataRepository.TagNewsResponseListener() {
            @Override
            public void onResponse(ArrayList<News> response) {

                if (response == null || response.size() == 0) {
                    adapter.removeItem();
                } else {
                    checkSave(response, saveNewsList);
                    adapter.addNewsList(response);
                    nextPage++;
                }
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
        mFirebaseAnalytics.logEvent("android_komentar", bundle);

        binding.progressBar.setVisibility(View.VISIBLE);
        binding.recyclerViewTags.setVisibility(View.GONE);

        dataRepository.loadTagNewsData(id, 0, new DataRepository.TagNewsResponseListener() {
            @Override
            public void onResponse(ArrayList<News> response) {
                checkSave(response, saveNewsList);

                adapter.setTagData(response);
                nextPage = 2;

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

    public void checkSave(ArrayList<News> newsList, ArrayList<SaveNews> saveNews) {
        for (News news : newsList) {
            for (SaveNews save : saveNews) {
                if (news.id == save.id) {
                    news.isSaved = true;
                    break;
                }
            }
        }
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