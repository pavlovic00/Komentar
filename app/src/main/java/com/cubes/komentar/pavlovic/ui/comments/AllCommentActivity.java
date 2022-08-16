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
import com.cubes.komentar.pavlovic.data.model.Vote;
import com.cubes.komentar.pavlovic.data.source.repository.DataRepository;
import com.cubes.komentar.pavlovic.data.source.response.ResponseComment;
import com.cubes.komentar.pavlovic.ui.tools.CommentListener;

import java.util.ArrayList;


public class AllCommentActivity extends AppCompatActivity {

    private final ArrayList<ResponseComment.Comment> allComments = new ArrayList<>();
    private ActivityAllCommentBinding binding;
    private ArrayList<Vote> votes = new ArrayList<>();
    private CommentAdapter adapter;
    private int id;
    private ResponseComment.Comment comment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAllCommentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        id = getIntent().getExtras().getInt("id");

        binding.imageBack.setOnClickListener(view1 -> finish());

        setupRecyclerView();
        loadCommentData();
        refresh();
    }

    public void setupRecyclerView() {

        binding.recyclerViewComments.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new CommentAdapter();
        binding.recyclerViewComments.setAdapter(adapter);

        if (SharedPrefs.readListFromPref(AllCommentActivity.this) != null) {
            votes = (ArrayList<Vote>) SharedPrefs.readListFromPref(AllCommentActivity.this);
        }

        adapter.setCommentListener(new CommentListener() {
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

                        Vote vote = new Vote(commentId, true);

                        votes.add(vote);
                        SharedPrefs.writeListInPref(AllCommentActivity.this, votes);

                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(getApplicationContext(), "Došlo je do greške!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void dislike(String commentId) {
                DataRepository.getInstance().unVoteComment(commentId, new DataRepository.VoteCommentListener() {
                    @Override
                    public void onResponse(ResponseComment response) {
                        Toast.makeText(getApplicationContext(), "Bravo za DISLAJK!", Toast.LENGTH_SHORT).show();

                        Vote vote = new Vote(commentId, false);

                        votes.add(vote);
                        SharedPrefs.writeListInPref(AllCommentActivity.this, votes);
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
            public void onResponse(ArrayList<ResponseComment.Comment> response) {

                setDataComment(response);

                binding.refresh.setVisibility(View.GONE);
                binding.progressBar.setVisibility(View.GONE);
                binding.recyclerViewComments.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                binding.refresh.setVisibility(View.VISIBLE);
            }
        });

    }

    public void setDataComment(ArrayList<ResponseComment.Comment> commentData) {

        ArrayList<ResponseComment.Comment> comments = commentData;

        for (ResponseComment.Comment comment : comments) {
            allComments.add(comment);
            addChildren(comment.children);
        }

        if (votes != null) {
            setVoteData(allComments, votes);
        }

        adapter.updateList(allComments);
    }

    private void setVoteData(ArrayList<ResponseComment.Comment> allComments, ArrayList<Vote> votes) {

        for (ResponseComment.Comment comment : allComments) {
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

    private void addChildren(ArrayList<ResponseComment.Comment> comments) {
        if (comments != null && !comments.isEmpty()) {
            for (ResponseComment.Comment comment : comments) {
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