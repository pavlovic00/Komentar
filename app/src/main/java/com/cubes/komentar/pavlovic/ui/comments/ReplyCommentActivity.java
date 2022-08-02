package com.cubes.komentar.pavlovic.ui.comments;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.cubes.komentar.databinding.ActivityReplyCommentBinding;

public class ReplyCommentActivity extends AppCompatActivity {

    private ActivityReplyCommentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityReplyCommentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



    }

    //Jedna od najtezih stvari koje sam nasao. :(

//    private void postComment(String name, String email, String content) {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://komentar.rs/wp-json/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        RetrofitService service = retrofit.create(RetrofitService.class);
//
//        ResponseCommentSend.ResponseBody data = new ResponseCommentSend.ResponseBody(String.valueOf(id), String.valueOf(news), name, email, content);
//
//        service.createPost(data).enqueue(new Callback<ResponseCommentSend.ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseCommentSend.ResponseBody> call, Response<ResponseCommentSend.ResponseBody> response) {
//                binding.textViewName.setText("");
//                binding.textMail.setText("");
//                binding.textViewContent.setText("");
//                Toast.makeText(getApplicationContext(), response.code() + "Radi", Toast.LENGTH_LONG).show();
//                finish();
//            }
//
//            @Override
//            public void onFailure(Call<ResponseCommentSend.ResponseBody> call, Throwable t) {
//
//            }
//        });
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