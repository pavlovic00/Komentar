package com.cubes.komentar.pavlovic.ui.comments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cubes.komentar.databinding.ActivityAllCommentBinding;
import com.cubes.komentar.pavlovic.data.domain.Comment;
import com.cubes.komentar.pavlovic.data.domain.Vote;
import com.cubes.komentar.pavlovic.data.source.repository.DataRepository;
import com.cubes.komentar.pavlovic.data.source.response.ResponseComment;
import com.cubes.komentar.pavlovic.ui.tools.SharedPrefs;
import com.cubes.komentar.pavlovic.ui.tools.listener.CommentListener;

import java.util.ArrayList;

public class AllCommentActivity extends AppCompatActivity {

    private final ArrayList<Comment> allComments = new ArrayList<>();
    private ActivityAllCommentBinding binding;
    private ArrayList<Vote> votes = new ArrayList<>();
    private CommentAdapter adapter;
    private int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAllCommentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        id = getIntent().getExtras().getInt("id");

        binding.imageBack.setOnClickListener(view1 -> finish());

        binding.swipeRefresh.setOnRefreshListener(() -> {
            setupRecyclerView();
            loadCommentData();
            binding.progressBar.setVisibility(View.GONE);
        });

        setupRecyclerView();
        loadCommentData();
        refresh();
    }

    public void setupRecyclerView() {

        allComments.clear();

        binding.recyclerViewComments.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new CommentAdapter();
        binding.recyclerViewComments.setAdapter(adapter);

        if (SharedPrefs.readListFromPref(AllCommentActivity.this) != null) {
            votes = (ArrayList<Vote>) SharedPrefs.readListFromPref(AllCommentActivity.this);
        }

        adapter.setCommentListener(new CommentListener() {
            @Override
            public void onCommentClicked(Comment comment) {
                Intent replyIntent = new Intent(getApplicationContext(), PostCommentActivity.class);
                replyIntent.putExtra("reply_id", comment.id);
                replyIntent.putExtra("news", comment.news);
                replyIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(replyIntent);
            }

            @Override
            public void like(Comment comment) {
                DataRepository.getInstance().voteComment(comment.id, new DataRepository.VoteCommentListener() {
                    @Override
                    public void onResponse(ResponseComment response) {
                        Toast.makeText(getApplicationContext(), "Bravo za LAJK!", Toast.LENGTH_SHORT).show();

                        Vote vote = new Vote(comment.id, true);

                        votes.add(vote);
                        SharedPrefs.writeListInPref(AllCommentActivity.this, votes);

                        adapter.setupLike(comment.id);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(getApplicationContext(), "Došlo je do greške!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void dislike(Comment comment) {
                DataRepository.getInstance().unVoteComment(comment.id, new DataRepository.VoteCommentListener() {
                    @Override
                    public void onResponse(ResponseComment response) {
                        Toast.makeText(getApplicationContext(), "Bravo za DISLAJK!", Toast.LENGTH_SHORT).show();

                        Vote vote = new Vote(comment.id, false);

                        votes.add(vote);
                        SharedPrefs.writeListInPref(AllCommentActivity.this, votes);

                        adapter.setupDislike(comment.id);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(getApplicationContext(), "Došlo je do greške!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    public void loadCommentData() {

        binding.progressBar.setVisibility(View.VISIBLE);
        binding.recyclerViewComments.setVisibility(View.GONE);

        DataRepository.getInstance().loadCommentData(id, new DataRepository.CommentResponseListener() {
            @Override
            public void onResponse(ArrayList<Comment> response) {

                if (response.equals(new ArrayList<>())) {
                    binding.obavestenje.setVisibility(View.VISIBLE);
                }

                setDataComment(response);

                binding.refresh.setVisibility(View.GONE);
                binding.progressBar.setVisibility(View.GONE);
                binding.recyclerViewComments.setVisibility(View.VISIBLE);
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

    public void setDataComment(ArrayList<Comment> comments) {

        for (Comment comment : comments) {
            allComments.add(comment);
            addChildren(comment.children);
        }

        if (votes != null) {
            setVoteData(allComments, votes);
        }

        adapter.updateList(allComments);
    }

    private void setVoteData(ArrayList<Comment> allComments, ArrayList<Vote> votes) {

        for (Comment comment : allComments) {
            for (Vote vote : votes) {
                if (comment.id.equals(vote.commentId)) {
                    comment.vote = vote;
                }
                if (comment.children != null) {
                    setVoteData(comment.children, votes);
                }
            }
        }
    }

    private void addChildren(ArrayList<Comment> comments) {
        if (comments != null && !comments.isEmpty()) {
            for (Comment comment : comments) {
                allComments.add(comment);
                addChildren(comment.children);
            }
        }
    }

    public void refresh() {

        binding.refresh.setOnClickListener(view -> {

            RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotate.setDuration(300);
            binding.refresh.startAnimation(rotate);
            loadCommentData();
            binding.progressBar.setVisibility(View.GONE);
        });
    }
}