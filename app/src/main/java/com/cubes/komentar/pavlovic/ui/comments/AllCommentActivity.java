package com.cubes.komentar.pavlovic.ui.comments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import com.cubes.komentar.databinding.ActivityAllCommentBinding;
import com.cubes.komentar.pavlovic.data.repository.DataRepository;
import com.cubes.komentar.pavlovic.data.response.ResponseComment;

import java.util.ArrayList;


public class AllCommentActivity extends AppCompatActivity {

    private ActivityAllCommentBinding binding;
    private CommentAdapter adapter;
    private int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAllCommentBinding.inflate(getLayoutInflater());
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
        loadCommentData();
        refresh();
    }

    public void setupRecyclerView(){

        binding.recyclerViewComments.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new CommentAdapter();
        binding.recyclerViewComments.setAdapter(adapter);

    }

    public void loadCommentData() {

        binding.progressBar.setVisibility(View.VISIBLE);
        binding.recyclerViewComments.setVisibility(View.GONE);

        DataRepository.getInstance().loadCommentData(id, new DataRepository.CommentResponseListener() {
            @Override
            public void onResponse(ArrayList<ResponseComment.Comment> response) {

                adapter.setDataComment(response);

                binding.refresh.setVisibility(View.GONE);
                binding.progressBar.setVisibility(View.GONE);
                binding.recyclerViewComments.setVisibility(View.VISIBLE);
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
                loadCommentData();
                binding.progressBar.setVisibility(View.GONE);
            }
        });
    }
}