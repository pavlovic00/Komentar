package com.cubes.komentar.pavlovic.ui.details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cubes.komentar.databinding.ActivityNewsDetailBinding;
import com.cubes.komentar.pavlovic.data.repository.DataRepository;
import com.cubes.komentar.pavlovic.data.response.ResponseDetail;
import com.cubes.komentar.pavlovic.ui.comments.AllCommentActivity;

public class NewsDetailActivity extends AppCompatActivity {

    private ActivityNewsDetailBinding binding;
    private int id;
    public ResponseDetail.ResponseDetailData dataResponse;


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

        loadDetailData();

        binding.imageViewComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent commentIntent = new Intent(view.getContext(), AllCommentActivity.class);
                commentIntent.putExtra("id",id);
                view.getContext().startActivity(commentIntent);
            }
        });


        binding.imageViewShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_SEND);
                i.putExtra(Intent.EXTRA_STREAM, dataResponse.url);
                i.setType("text/plain");
                startActivity(Intent.createChooser(i, null));
            }
        });
    }

    public void loadDetailData() {

        DataRepository.getInstance().loadDetailData(id, new DataRepository.DetailResponseListener() {
            @Override
            public void onResponse(ResponseDetail response) {
                dataResponse = response.data;

                binding.recyclerViewDetail.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                binding.recyclerViewDetail.setAdapter(new DetailNewsAdapter(dataResponse, getApplicationContext()));
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

    }
}