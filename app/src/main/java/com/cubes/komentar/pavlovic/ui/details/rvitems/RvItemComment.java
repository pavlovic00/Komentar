package com.cubes.komentar.pavlovic.ui.details.rvitems;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.cubes.komentar.databinding.RvItemCommentBinding;
import com.cubes.komentar.pavlovic.data.networking.RetrofitService;
import com.cubes.komentar.pavlovic.data.response.responsecomment.ResponseComment;
import com.cubes.komentar.pavlovic.data.response.responsecomment.ResponseCommentData;
import com.cubes.komentar.pavlovic.ui.details.DetailNewsAdapter;
import com.cubes.komentar.pavlovic.ui.comments.ReplyActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RvItemComment implements RecyclerViewItemDetail{

    ResponseCommentData data;
    private int like;
    private int dislike;

    public RvItemComment(ResponseCommentData data) {
        this.data = data;
    }

    @Override
    public int getType() {
        return 6;
    }

    @Override
    public void bind(DetailNewsAdapter.DetailNewsViewHolder holder) {

        RvItemCommentBinding binding = (RvItemCommentBinding) holder.binding;

        like = data.positive_votes;
        dislike = data.negative_votes;

        binding.textViewPerson.setText(data.name);
        binding.textViewDate.setText(data.created_at);
        binding.textViewContent.setText(data.content);
        binding.textViewLike.setText(like+"");
        binding.textViewDissLike.setText(dislike+"");


        //Nisam imao vremena da usavrsim ovo, ali ako je problem poslacu kad usavrsim :)


        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("https://komentar.rs/wp-json/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitService service = retrofit.create(RetrofitService.class);


        binding.imageViewLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                service.postLike(Integer.parseInt(data.id),true).enqueue(new Callback<ResponseComment>() {
                    @Override
                    public void onResponse(Call<ResponseComment> call, Response<ResponseComment> response) {
                        like++;
                        binding.textViewLike.setText((like)+"");
                        Toast.makeText(view.getContext().getApplicationContext(),"Bravo za LAJK",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponseComment> call, Throwable t) {

                    }
                });

            }
        });


        binding.imageViewDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                service.postDislike(Integer.parseInt(data.id),true).enqueue(new Callback<ResponseComment>() {
                    @Override
                    public void onResponse(Call<ResponseComment> call, Response<ResponseComment> response) {
                        dislike++;
                        binding.textViewDissLike.setText((dislike)+"");
                        Toast.makeText(view.getContext().getApplicationContext(),"Bravo za DISLAJK",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponseComment> call, Throwable t) {

                    }
                });
            }
        });

        binding.buttonReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyIntent = new Intent(view.getContext(), ReplyActivity.class);
                if (data.id != null) {
                    replyIntent.putExtra("main_id", data.id);
                }
                    if (data.children.size() > 0) {
                        replyIntent.putExtra("reply_id", data.children.get(Integer.parseInt(data.id)).id);
                    }

                view.getContext().startActivity(replyIntent);
            }
        });

    }
}
