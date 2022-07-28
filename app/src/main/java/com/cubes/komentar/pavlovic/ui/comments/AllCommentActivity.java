package com.cubes.komentar.pavlovic.ui.comments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.cubes.komentar.databinding.ActivityAllCommentBinding;
import com.cubes.komentar.pavlovic.data.DataContainer;


public class AllCommentActivity extends AppCompatActivity {

    private ActivityAllCommentBinding binding;
//    private int id;
//    public ResponseDetailData dataResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAllCommentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //Proba za lajk.

//        id = getIntent().getExtras().getInt("id");
//
//        Retrofit retrofit= new Retrofit.Builder()
//                .baseUrl("https://komentar.rs/wp-json/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        RetrofitService service = retrofit.create(RetrofitService.class);
//
//        service.getNewsDetail(id).enqueue(new Callback<ResponseDetail>() {
//            @Override
//            public void onResponse(Call<ResponseDetail> call, Response<ResponseDetail> response) {
//                dataResponse = response.body().data;
//
//                service.getComment(id).enqueue(new Callback<ResponseComment>() {
//                    @Override
//                    public void onResponse(Call<ResponseComment> call, Response<ResponseComment> response) {
//                        DataContainer.commentList = response.body().data;
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<ResponseComment> call, Throwable t) {
//
//                    }
//                });
//            }
//
//            @Override
//            public void onFailure(Call<ResponseDetail> call, Throwable t) {
//
//            }
//        });

        binding.recyclerViewComments.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        binding.recyclerViewComments.setAdapter(new CommentAdapter(getApplicationContext(), DataContainer.commentList));

        binding.imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}