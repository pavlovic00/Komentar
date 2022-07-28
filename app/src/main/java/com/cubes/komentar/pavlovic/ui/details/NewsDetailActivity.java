package com.cubes.komentar.pavlovic.ui.details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cubes.komentar.databinding.ActivityNewsDetailBinding;
import com.cubes.komentar.pavlovic.data.DataContainer;
import com.cubes.komentar.pavlovic.data.networking.RetrofitService;
import com.cubes.komentar.pavlovic.data.repository.DataRepository;
import com.cubes.komentar.pavlovic.data.response.responsecomment.ResponseComment;
import com.cubes.komentar.pavlovic.data.response.responsedetail.ResponseDetail;
import com.cubes.komentar.pavlovic.data.response.responsedetail.ResponseDetailData;
import com.cubes.komentar.pavlovic.ui.comments.AllCommentActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsDetailActivity extends AppCompatActivity {

    private ActivityNewsDetailBinding binding;
    private int id;
    public ResponseDetailData dataResponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding= ActivityNewsDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        id = getIntent().getExtras().getInt("id");

        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("https://komentar.rs/wp-json/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitService service = retrofit.create(RetrofitService.class);

        service.getNewsDetail(id).enqueue(new Callback<ResponseDetail>() {
            @Override
            public void onResponse(Call<ResponseDetail> call, Response<ResponseDetail> response) {
                dataResponse = response.body().data;

                service.getComment(id).enqueue(new Callback<ResponseComment>() {
                    @Override
                    public void onResponse(Call<ResponseComment> call, Response<ResponseComment> response) {
                        DataContainer.commentList = response.body().data;

                        binding.recyclerViewDetail.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        binding.recyclerViewDetail.setAdapter(new DetailNewsAdapter(dataResponse,DataContainer.commentList,getApplicationContext()));
                    }

                    @Override
                    public void onFailure(Call<ResponseComment> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onFailure(Call<ResponseDetail> call, Throwable t) {

            }
        });

        binding.imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.imageViewShare.setOnClickListener(view1 -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,dataResponse.url);
            sendIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
        });

        binding.imageViewComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent commentIntent = new Intent(view.getContext(), AllCommentActivity.class);
                view.getContext().startActivity(commentIntent);
            }
        });

    }

    public void loadCommentData(){

        DataRepository.getInstance().loadDetailData(id, new DataRepository.DetailResponseListener() {
            @Override
            public void onResponse(ResponseDetail response) {
                dataResponse = response.data;

                DataRepository.getInstance().loadCommentData(id, new DataRepository.CommentResponseListener() {
                    @Override
                    public void onResponse(ResponseComment response) {
                        binding.recyclerViewDetail.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        binding.recyclerViewDetail.setAdapter(new DetailNewsAdapter(dataResponse, response.data, getApplicationContext()));
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });

            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

    }
}