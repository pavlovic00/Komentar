package com.cubes.komentar.pavlovic.ui.details;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.cubes.komentar.databinding.ActivityDetailsBinding;
import com.cubes.komentar.pavlovic.ui.comments.AllCommentActivity;

public class DetailsActivity extends AppCompatActivity implements DetailsPagerFragment.DetailListener {

    private ActivityDetailsBinding binding;
    private int newsId;
    private String newsUrl;


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

        setClickListeners();
    }

    private void setClickListeners() {

        binding.imageBack.setOnClickListener(view1 -> finish());

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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    public void onDetailsResponseListener(int newsId, String newsUrl) {
        this.newsId = newsId;
        this.newsUrl = newsUrl;
    }
}