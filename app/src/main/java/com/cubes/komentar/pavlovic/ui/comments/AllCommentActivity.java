package com.cubes.komentar.pavlovic.ui.comments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.cubes.komentar.databinding.ActivityAllCommentBinding;
import com.cubes.komentar.pavlovic.data.repository.DataRepository;
import com.cubes.komentar.pavlovic.data.response.ResponseComment;

import java.util.ArrayList;


public class AllCommentActivity extends AppCompatActivity {

    private ActivityAllCommentBinding binding;
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

        loadCommentData();
    }

    public void loadCommentData() {

        DataRepository.getInstance().loadCommentData(id, new DataRepository.CommentResponseListener() {
            @Override
            public void onResponse(ArrayList<ResponseComment.ResponseCommentData> response) {
                binding.recyclerViewComments.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                binding.recyclerViewComments.setAdapter(new CommentAdapter(getApplicationContext(), response));
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

    }
}