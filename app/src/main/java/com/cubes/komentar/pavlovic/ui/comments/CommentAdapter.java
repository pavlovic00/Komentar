package com.cubes.komentar.pavlovic.ui.comments;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cubes.komentar.databinding.RvItemCommentBinding;
import com.cubes.komentar.pavlovic.data.networking.RetrofitService;
import com.cubes.komentar.pavlovic.data.response.ResponseComment;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private Context context;
    private ArrayList<ResponseComment.ResponseCommentData> dataList;
    ResponseComment.ResponseCommentData data;
    private int like;
    private int dislike;

    public CommentAdapter(Context context, ArrayList<ResponseComment.ResponseCommentData> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public CommentAdapter.CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RvItemCommentBinding binding = RvItemCommentBinding.inflate(LayoutInflater.from(context), parent, false);

        return new CommentAdapter.CommentViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.CommentViewHolder holder, int position) {

        ResponseComment.ResponseCommentData comment = dataList.get(position);

        like = comment.positive_votes;
        dislike = comment.negative_votes;

        holder.binding.textViewPerson.setText(comment.name);
        holder.binding.textViewDate.setText(comment.created_at);
        holder.binding.textViewContent.setText(comment.content);
        holder.binding.textViewLike.setText(like + "");
        holder.binding.textViewDissLike.setText(dislike + "");

        if (comment.children != null) {

            if (comment.children.size() > 0) {

                holder.binding.textViewOdgovori.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        comment.open = !comment.open;

                        if (comment.open) {
                            holder.binding.recyclerViewChildren.setLayoutManager(new LinearLayoutManager(context));
                            holder.binding.recyclerViewChildren.setAdapter(new CommentAdapter(context, comment.children));

                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl("https://komentar.rs/wp-json/")
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();

                            RetrofitService service = retrofit.create(RetrofitService.class);


                            holder.binding.imageViewLike.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    service.postLike(Integer.parseInt(comment.id), true).enqueue(new Callback<ResponseComment>() {
                                        @Override
                                        public void onResponse(Call<ResponseComment> call, Response<ResponseComment> response) {
                                            like++;
                                            holder.binding.textViewLike.setText((like) + "");
                                            Toast.makeText(view.getContext().getApplicationContext(), "Bravo za LAJK!", Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onFailure(Call<ResponseComment> call, Throwable t) {

                                        }
                                    });

                                }
                            });


                            holder.binding.imageViewDislike.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    service.postDislike(Integer.parseInt(comment.id), true).enqueue(new Callback<ResponseComment>() {
                                        @Override
                                        public void onResponse(Call<ResponseComment> call, Response<ResponseComment> response) {
                                            dislike++;
                                            holder.binding.textViewDissLike.setText((dislike) + "");
                                            Toast.makeText(view.getContext().getApplicationContext(), "Bravo za DISLAJK!", Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onFailure(Call<ResponseComment> call, Throwable t) {

                                        }
                                    });
                                }
                            });

                            holder.binding.buttonReply.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent replyIntent = new Intent(view.getContext(), ReplyActivity.class);
                                    if (data.id != null) {
                                        replyIntent.putExtra("main_id", data.id);
                                    }
                                    if (data.children.size() > 0) {
                                        replyIntent.putExtra("reply_id", data.children.get(Integer.parseInt(data.id)).id);
                                    }
                                    replyIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                    view.getContext().startActivity(replyIntent);
                                }
                            });

                        } else {
                            holder.binding.recyclerViewChildren.setLayoutManager(new LinearLayoutManager(context));
                            holder.binding.recyclerViewChildren.setAdapter(new CommentAdapter(context, new ArrayList<ResponseComment.ResponseCommentData>()));
                        }

                    }
                });
            }
        }

        holder.binding.buttonReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {                                        //Jako bitna linija koda.
                Intent replyIntent = new Intent(context, ReplyActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(replyIntent);
            }
        });


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://komentar.rs/wp-json/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitService service = retrofit.create(RetrofitService.class);


        holder.binding.imageViewLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                service.postLike(Integer.parseInt(comment.id), true).enqueue(new Callback<ResponseComment>() {
                    @Override
                    public void onResponse(Call<ResponseComment> call, Response<ResponseComment> response) {
                        like++;
                        holder.binding.textViewLike.setText((like) + "");
                        Toast.makeText(view.getContext().getApplicationContext(), "Bravo za LAJK!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponseComment> call, Throwable t) {

                    }
                });

            }
        });


        holder.binding.imageViewDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                service.postDislike(Integer.parseInt(comment.id), true).enqueue(new Callback<ResponseComment>() {
                    @Override
                    public void onResponse(Call<ResponseComment> call, Response<ResponseComment> response) {
                        dislike++;
                        holder.binding.textViewDissLike.setText((dislike) + "");
                        Toast.makeText(view.getContext().getApplicationContext(), "Bravo za DISLAJK!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponseComment> call, Throwable t) {

                    }
                });
            }
        });


        holder.binding.buttonReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyIntent = new Intent(context, ReplyActivity.class);
                if (data.id != null) {
                    replyIntent.putExtra("main_id", data.id);
                }
                if (data.children.size() > 0) {
                    replyIntent.putExtra("reply_id", data.children.get(Integer.parseInt(data.id)).id);
                }
                replyIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                context.startActivity(replyIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {

        private RvItemCommentBinding binding;

        public CommentViewHolder(RvItemCommentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
