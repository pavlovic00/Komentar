package com.cubes.komentar.pavlovic.ui.details;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cubes.komentar.databinding.ActivityNewsDetailBinding;
import com.cubes.komentar.pavlovic.data.model.News;
import com.cubes.komentar.pavlovic.data.model.Tags;
import com.cubes.komentar.pavlovic.data.source.repository.DataRepository;
import com.cubes.komentar.pavlovic.data.source.response.ResponseComment;
import com.cubes.komentar.pavlovic.data.source.response.ResponseDetail;
import com.cubes.komentar.pavlovic.ui.comments.AllCommentActivity;
import com.cubes.komentar.pavlovic.ui.comments.PostCommentActivity;
import com.cubes.komentar.pavlovic.ui.tag.TagsActivity;
import com.cubes.komentar.pavlovic.ui.tools.CommentListener;
import com.cubes.komentar.pavlovic.ui.tools.NewsDetailListener;

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

        binding.imageBack.setOnClickListener(view1 -> finish());

        setupRecyclerView();
        loadDetailData();
        refresh();
    }

    private void setupRecyclerView() {

        binding.recyclerViewDetail.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new DetailNewsAdapter(new CommentListener() {
            @Override
            public void onCommentClicked(ResponseComment.Comment comment) {
                Intent replyIntent = new Intent(getApplicationContext(), PostCommentActivity.class);
                replyIntent.putExtra("reply_id", comment.id);
                replyIntent.putExtra("news", comment.news);
                replyIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(replyIntent);
            }

            @Override
            public void like(String commentId) {
                DataRepository.getInstance().voteComment(commentId, new DataRepository.VoteCommentListener() {
                    @Override
                    public void onResponse(ResponseComment response) {
                        Toast.makeText(getApplicationContext(), "Bravo za LAJK!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(getApplicationContext(), "Doslo je do greske!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void dislike(String commentId) {
                DataRepository.getInstance().unVoteComment(commentId, new DataRepository.VoteCommentListener() {
                    @Override
                    public void onResponse(ResponseComment response) {
                        Toast.makeText(getApplicationContext(), "Bravo za DISLAJK!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(getApplicationContext(), "Doslo je do greske!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }, new NewsDetailListener() {
            @Override
            public void onNewsCLicked(News news) {
                Intent startDetailIntent = new Intent(getApplicationContext(), NewsDetailActivity.class);
                startDetailIntent.putExtra("id", news.id);
                startActivity(startDetailIntent);
            }

            @Override
            public void onTagClicked(Tags tags) {
                Intent tagsIntent = new Intent(getApplicationContext(), TagsActivity.class);
                tagsIntent.putExtra("id", tags.id);
                tagsIntent.putExtra("title", tags.title);
                tagsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(tagsIntent);
            }

            @Override
            public void onPutCommentClicked(ResponseDetail.ResponseDetailData data) {
                Intent i = new Intent(getApplicationContext(), PostCommentActivity.class);
                i.putExtra("id", data.id);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }

            @Override
            public void onAllCommentClicked(ResponseDetail.ResponseDetailData data) {
                Intent commentIntent = new Intent(getApplicationContext(), AllCommentActivity.class);
                commentIntent.putExtra("id", data.id);
                commentIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(commentIntent);
            }
        });

        binding.recyclerViewDetail.setAdapter(adapter);
    }

    public void loadDetailData() {

        binding.progressBar.setVisibility(View.VISIBLE);
        binding.recyclerViewDetail.setVisibility(View.GONE);

        DataRepository.getInstance().loadDetailData(id, new DataRepository.DetailResponseListener() {
            @Override
            public void onResponse(ResponseDetail.ResponseDetailData response) {

                adapter.setDataItems(response);

                binding.imageViewComment.setOnClickListener(view -> {
                    Intent commentIntent = new Intent(view.getContext(), AllCommentActivity.class);
                    commentIntent.putExtra("id", response.id);
                    view.getContext().startActivity(commentIntent);
                });

                binding.imageViewShare.setOnClickListener(view -> {
                    Intent i = new Intent();
                    i.setAction(Intent.ACTION_SEND);
                    i.putExtra(Intent.EXTRA_STREAM, response.url);
                    i.setType("text/plain");
                    startActivity(Intent.createChooser(i, null));
                });

                binding.refresh.setVisibility(View.GONE);
                binding.progressBar.setVisibility(View.GONE);
                binding.recyclerViewDetail.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                binding.refresh.setVisibility(View.VISIBLE);
            }
        });
    }

    public void refresh() {

        binding.refresh.setOnClickListener(view -> {

            RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotate.setDuration(300);
            binding.refresh.startAnimation(rotate);
            loadDetailData();
            binding.progressBar.setVisibility(View.GONE);
        });
    }
}