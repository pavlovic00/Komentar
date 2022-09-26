package com.cubes.komentar.pavlovic.ui.details;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.cubes.komentar.R;
import com.cubes.komentar.databinding.ActivityDetailsBinding;
import com.cubes.komentar.pavlovic.data.domain.News;
import com.cubes.komentar.pavlovic.data.domain.SaveNews;
import com.cubes.komentar.pavlovic.ui.comments.AllCommentActivity;
import com.cubes.komentar.pavlovic.ui.tools.SharedPrefs;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity implements DetailsPagerFragment.DetailListener {

    private ActivityDetailsBinding binding;
    private int newsId;
    private SaveNews saveNews;
    private String newsUrl;
    private News news;
    private ArrayList<SaveNews> saveNewsList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        newsId = getIntent().getExtras().getInt("news_id");
        int[] newsListId = getIntent().getIntArrayExtra("news_list_id");

        DetailsPagerAdapter adapter = new DetailsPagerAdapter(this, newsListId);
        binding.viewPager2.setAdapter(adapter);

        for (int i = 0; i < newsListId.length; i++) {
            if (newsId == newsListId[i]) {
                binding.viewPager2.setCurrentItem(i, false);
                break;
            }
        }

        reduceDragSensitivity(5);

        setClickListeners();
    }

    private void setClickListeners() {

        if (SharedPrefs.showNewsFromPref(DetailsActivity.this) != null) {
            saveNewsList = (ArrayList<SaveNews>) SharedPrefs.showNewsFromPref(DetailsActivity.this);
        }

        binding.imageBack.setOnClickListener(view -> finish());

        binding.allComments.setOnClickListener(view -> {
            Intent commentIntent = new Intent(view.getContext(), AllCommentActivity.class);
            commentIntent.putExtra("news_id", newsId);
            view.getContext().startActivity(commentIntent);
        });

        binding.imageViewShare.setOnClickListener(view -> {
            Intent i = new Intent();
            i.setAction(Intent.ACTION_SEND);
            i.putExtra(Intent.EXTRA_TEXT, newsUrl);
            i.setType("text/plain");
            startActivity(Intent.createChooser(i, null));
        });

        if (news != null && news.isSaved) {
            binding.unSave.setImageResource(R.drawable.ic_save);
        } else {
            binding.unSave.setImageResource(R.drawable.ic_un_save);
        }

        binding.unSave.setOnClickListener(view -> {
            if (news.isSaved) {
                binding.unSave.setImageResource(R.drawable.ic_un_save);
                SaveNews unSave = new SaveNews(saveNews.id, saveNews.title);

                for (int i = 0; i < saveNewsList.size(); i++) {
                    if (unSave.id == saveNewsList.get(i).id) {
                        saveNewsList.remove(saveNewsList.get(i));
                        SharedPrefs.saveNewsInPref(DetailsActivity.this, saveNewsList);
                    }
                }
            } else {
                binding.unSave.setImageResource(R.drawable.ic_save);
                SaveNews save = new SaveNews(saveNews.id, saveNews.title);

                if (SharedPrefs.showNewsFromPref(DetailsActivity.this) != null) {
                    saveNewsList = (ArrayList<SaveNews>) SharedPrefs.showNewsFromPref(DetailsActivity.this);

                    for (int i = 0; i < saveNewsList.size(); i++) {
                        if (save.id == saveNewsList.get(i).id) {
                            Toast.makeText(getApplicationContext(), "VEST JE SACUVANA!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }
                saveNewsList.add(saveNews);
                SharedPrefs.saveNewsInPref(DetailsActivity.this, saveNewsList);
            }
        });
    }

    @SuppressWarnings("ConstantConditions")
    public void reduceDragSensitivity(int sensitivity) {
        try {
            Field ff = ViewPager2.class.getDeclaredField("mRecyclerView");
            ff.setAccessible(true);
            RecyclerView recyclerView = (RecyclerView) ff.get(binding.viewPager2);
            Field touchSlopField = RecyclerView.class.getDeclaredField("mTouchSlop");
            touchSlopField.setAccessible(true);
            int touchSlop = (int) touchSlopField.get(recyclerView);
            touchSlopField.set(recyclerView, touchSlop * sensitivity);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    public void onDetailsResponseListener(int newsId, String newsUrl, SaveNews saveNews, News news) {
        this.newsId = newsId;
        this.newsUrl = newsUrl;
        this.saveNews = saveNews;
        this.news = news;

        if (news != null && news.isSaved) {
            binding.unSave.setImageResource(R.drawable.ic_save);
        } else {
            binding.unSave.setImageResource(R.drawable.ic_un_save);
        }
    }
}