package com.cubes.komentar.pavlovic.ui.details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import com.cubes.komentar.databinding.ActivityNewsDetailBinding;
import com.cubes.komentar.pavlovic.data.repository.DataRepository;
import com.cubes.komentar.pavlovic.data.response.ResponseDetail;
import com.cubes.komentar.pavlovic.ui.comments.AllCommentActivity;

public class NewsDetailActivity extends AppCompatActivity {

    private ActivityNewsDetailBinding binding;
    private int id;
    private DetailNewsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNewsDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        id = getIntent().getExtras().getInt("id");

        binding.imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        setupRecyclerView();
        loadDetailData();
        refresh();
    }

    private void setupRecyclerView(){

        binding.recyclerViewDetail.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new DetailNewsAdapter();
        binding.recyclerViewDetail.setAdapter(adapter);

    }

    public void loadDetailData() {

        DataRepository.getInstance().loadDetailData(id, new DataRepository.DetailResponseListener() {
            @Override
            public void onResponse(ResponseDetail.ResponseDetailData response) {

                adapter.setDataItems(response);

                binding.imageViewComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent commentIntent = new Intent(view.getContext(), AllCommentActivity.class);
                        commentIntent.putExtra("id",response.id);
                        view.getContext().startActivity(commentIntent);
                    }
                });

                binding.imageViewShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent();
                        i.setAction(Intent.ACTION_SEND);
                        i.putExtra(Intent.EXTRA_STREAM,response.url);
                        i.setType("text/plain");
                        startActivity(Intent.createChooser(i, null));
                    }
                });

                binding.refresh.setVisibility(View.GONE);
                binding.recyclerViewDetail.setVisibility(View.VISIBLE);
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
                loadDetailData();
            }
        });
    }
}