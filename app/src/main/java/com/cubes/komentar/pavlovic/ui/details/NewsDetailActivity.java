package com.cubes.komentar.pavlovic.ui.details;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cubes.komentar.R;
import com.cubes.komentar.databinding.ActivityNewsDetailBinding;
import com.cubes.komentar.databinding.RvItemCommentBinding;
import com.cubes.komentar.pavlovic.data.model.News;
import com.cubes.komentar.pavlovic.data.model.Tags;
import com.cubes.komentar.pavlovic.data.model.Vote;
import com.cubes.komentar.pavlovic.data.source.repository.DataRepository;
import com.cubes.komentar.pavlovic.data.source.response.ResponseComment;
import com.cubes.komentar.pavlovic.data.source.response.ResponseDetail;
import com.cubes.komentar.pavlovic.ui.comments.AllCommentActivity;
import com.cubes.komentar.pavlovic.ui.comments.PostCommentActivity;
import com.cubes.komentar.pavlovic.ui.comments.SharedPrefs;
import com.cubes.komentar.pavlovic.ui.tag.TagsActivity;
import com.cubes.komentar.pavlovic.ui.tools.NewsDetailListener;

import java.util.ArrayList;

public class NewsDetailActivity extends AppCompatActivity {

    private ActivityNewsDetailBinding binding;
    private int id;
    private ArrayList<Vote> votes = new ArrayList<>();
    private NewsDetailAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNewsDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        id = getIntent().getExtras().getInt("id");

        binding.imageBack.setOnClickListener(view1 -> finish());

        binding.swipeRefresh.setOnRefreshListener(() -> {
            setupRecyclerView();
            loadDetailData();
            binding.progressBar.setVisibility(View.GONE);
        });

        setupRecyclerView();
        loadDetailData();
        refresh();
    }

    private void setupRecyclerView() {

        if (SharedPrefs.readListFromPref(NewsDetailActivity.this) != null) {
            votes = (ArrayList<Vote>) SharedPrefs.readListFromPref(NewsDetailActivity.this);
        }

        binding.recyclerViewDetail.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new NewsDetailAdapter(new NewsDetailListener() {

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

            @Override
            public void onCommentClicked(ResponseComment.Comment comment) {
                Intent replyIntent = new Intent(getApplicationContext(), PostCommentActivity.class);
                replyIntent.putExtra("reply_id", comment.id);
                replyIntent.putExtra("news", comment.news);
                replyIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(replyIntent);
            }

            @Override
            public void like(ResponseComment.Comment comment, RvItemCommentBinding bindingComment) {
                DataRepository.getInstance().voteComment(comment.id, new DataRepository.VoteCommentListener() {
                    @Override
                    public void onResponse(ResponseComment response) {
                        Toast.makeText(getApplicationContext(), "Bravo za LAJK!", Toast.LENGTH_SHORT).show();

                        Vote vote = new Vote(comment.id, true);

                        votes.add(vote);
                        SharedPrefs.writeListInPref(NewsDetailActivity.this, votes);

                        bindingComment.like.setText(String.valueOf(comment.positive_votes + 1));
                        bindingComment.imageViewLike.setImageResource(R.drawable.ic_like_vote);
                        bindingComment.likeCircle.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(getApplicationContext(), "Doslo je do greske!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void dislike(ResponseComment.Comment comment, RvItemCommentBinding bindingComment) {
                DataRepository.getInstance().unVoteComment(comment.id, new DataRepository.VoteCommentListener() {
                    @Override
                    public void onResponse(ResponseComment response) {
                        Toast.makeText(getApplicationContext(), "Bravo za DISLAJK!", Toast.LENGTH_SHORT).show();

                        Vote vote = new Vote(comment.id, false);

                        votes.add(vote);
                        SharedPrefs.writeListInPref(NewsDetailActivity.this, votes);

                        bindingComment.dislike.setText(String.valueOf(comment.negative_votes + 1));
                        bindingComment.imageViewDislike.setImageResource(R.drawable.ic_dislike_vote);
                        bindingComment.dislikeCircle.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(getApplicationContext(), "Doslo je do greske!", Toast.LENGTH_SHORT).show();
                    }
                });
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
                binding.swipeRefresh.setRefreshing(false);
            }

            @Override
            public void onFailure(Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                binding.refresh.setVisibility(View.VISIBLE);
                binding.swipeRefresh.setRefreshing(false);
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