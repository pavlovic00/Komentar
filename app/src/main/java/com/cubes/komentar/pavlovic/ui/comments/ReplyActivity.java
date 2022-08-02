package com.cubes.komentar.pavlovic.ui.comments;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.cubes.komentar.databinding.ActivityReplyBinding;
import com.cubes.komentar.pavlovic.data.networking.RetrofitService;
import com.cubes.komentar.pavlovic.data.response.ResponseCommentSend;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReplyActivity extends AppCompatActivity {

    private ActivityReplyBinding binding;
    private int id;
    private String reply_id;
    private ResponseCommentSend.ResponseBody responseBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityReplyBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        reply_id = getIntent().getExtras().getString("reply_id");
        id = getIntent().getExtras().getInt("main_id");


        String name = binding.textViewName.getText().toString();
        String email = binding.textMail.getText().toString();
        String content = binding.textViewContent.getText().toString();

        binding.commentSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.textViewName.getText().toString().isEmpty() &&
                        binding.textMail.getText().toString().isEmpty() &&
                        binding.textViewContent.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Popunite sva polja", Toast.LENGTH_LONG).show();
                } else {
                    postComment(name, email, content);
                }
            }
        });

        binding.imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    //Jedna od najtezih stvari koje sam nasao. :(
    private void postComment(String name, String email, String content) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://komentar.rs/wp-json/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitService service = retrofit.create(RetrofitService.class);

        ResponseCommentSend.ResponseBody data = new ResponseCommentSend.ResponseBody(String.valueOf(reply_id), name, email, content);

        service.createPost(data).enqueue(new Callback<ResponseCommentSend.ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseCommentSend.ResponseBody> call, Response<ResponseCommentSend.ResponseBody> response) {
                binding.textViewName.setText("");
                binding.textMail.setText("");
                binding.textViewContent.setText("");
                Toast.makeText(getApplicationContext(), response.code() + "Radi", Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onFailure(Call<ResponseCommentSend.ResponseBody> call, Throwable t) {

            }
        });


        //Nisam imao vremena da usavrsim ovo, ali ako je problem poslacu kad usavrsim :)

//        binding.commentSend.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Retrofit retrofit= new Retrofit.Builder()
//                        .baseUrl("https://komentar.rs/wp-json/")
//                        .addConverterFactory(GsonConverterFactory.create())
//                        .build();
//
//                RetrofitService service = retrofit.create(RetrofitService.class);
//
//
//                service.postComment(id,reply_id,
//                        binding.textViewName.getText().toString(),
//                        binding.textMail.getText().toString(),
//                        binding.textViewContent.getText().toString()).enqueue(new Callback<ResponseCommentSend>() {
//                    @Override
//                    public void onResponse(Call<ResponseCommentSend> call, Response<ResponseCommentSend> response) {
//                        responseBody = response.body().data;
//
//                        Toast.makeText(getApplicationContext(),"RADI",Toast.LENGTH_LONG).show();
//
//                        //finish();
//                    }
//
//                    @Override
//                    public void onFailure(Call<ResponseCommentSend> call, Throwable t) {
//
//                    }
//                });
//
//            }
//        });
    }
}