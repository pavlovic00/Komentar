package com.cubes.komentar.pavlovic.ui.details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cubes.komentar.databinding.FragmentDetailsBinding;
import com.cubes.komentar.pavlovic.data.domain.Comment;
import com.cubes.komentar.pavlovic.data.domain.NewsDetail;
import com.cubes.komentar.pavlovic.data.domain.Tags;
import com.cubes.komentar.pavlovic.data.domain.Vote;
import com.cubes.komentar.pavlovic.data.source.repository.DataRepository;
import com.cubes.komentar.pavlovic.data.source.response.ResponseComment;
import com.cubes.komentar.pavlovic.di.AppContainer;
import com.cubes.komentar.pavlovic.di.MyApplication;
import com.cubes.komentar.pavlovic.ui.comments.AllCommentActivity;
import com.cubes.komentar.pavlovic.ui.comments.PostCommentActivity;
import com.cubes.komentar.pavlovic.ui.tag.TagsActivity;
import com.cubes.komentar.pavlovic.ui.tools.SharedPrefs;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;

public class DetailsPagerFragment extends Fragment {

    private FragmentDetailsBinding binding;
    private FirebaseAnalytics mFirebaseAnalytics;
    private static final String NEWS_ID = "news_id";
    private int newsId;
    private String title;
    private String newsUrl;
    private DetailsAdapter adapter;
    private DetailsListener detailsListener;
    private ArrayList<Vote> votes = new ArrayList<>();
    private DataRepository dataRepository;


    public interface DetailsListener {
        void onDetailsResponseListener(int newsId, String newsUrl);
    }

    public DetailsPagerFragment() {
    }

    public static DetailsPagerFragment newInstance(int newsId) {
        DetailsPagerFragment fragment = new DetailsPagerFragment();
        Bundle args = new Bundle();
        args.putInt(NEWS_ID, newsId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.detailsListener = (DetailsListener) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            newsId = getArguments().getInt(NEWS_ID);
        }

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(requireContext());
        AppContainer appContainer = ((MyApplication) requireActivity().getApplication()).appContainer;
        dataRepository = appContainer.dataRepository;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.swipeRefresh.setOnRefreshListener(() -> {
            setupRecyclerView();
            loadDetailData();
            binding.progressBar.setVisibility(View.GONE);
        });

        setupRecyclerView();
        loadDetailData();
        refresh();
    }

    @Override
    public void onResume() {
        super.onResume();

        detailsListener.onDetailsResponseListener(newsId, newsUrl);
    }

    private void setupRecyclerView() {

        if (SharedPrefs.readListFromPref(requireActivity()) != null) {
            votes = (ArrayList<Vote>) SharedPrefs.readListFromPref(requireActivity());
        }

        binding.recyclerViewHomepage.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new DetailsAdapter(new com.cubes.komentar.pavlovic.ui.tools.listener.DetailsListener() {
            @Override
            public void onNewsClickedVP(int newsId, int[] newsIdList) {
                Intent intent = new Intent(getContext(), DetailsActivity.class);
                intent.putExtra("news_id", newsId);
                intent.putExtra("news_list_id", newsIdList);
                startActivity(intent);
            }

            @Override
            public void onTagClicked(Tags tags) {
                Intent tagsIntent = new Intent(getContext(), TagsActivity.class);
                tagsIntent.putExtra("id", tags.id);
                tagsIntent.putExtra("title", tags.title);
                tagsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(tagsIntent);
            }

            @Override
            public void onPutCommentClicked(NewsDetail data) {
                Intent i = new Intent(getContext(), PostCommentActivity.class);
                i.putExtra("id", data.id);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }

            @Override
            public void onAllCommentClicked(NewsDetail data) {
                Intent commentIntent = new Intent(getContext(), AllCommentActivity.class);
                commentIntent.putExtra("id", data.id);
                commentIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(commentIntent);
            }

            @Override
            public void onCommentClicked(Comment comment) {
                Intent replyIntent = new Intent(getContext(), PostCommentActivity.class);
                replyIntent.putExtra("reply_id", comment.id);
                replyIntent.putExtra("news", comment.news);
                replyIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(replyIntent);
            }

            @Override
            public void like(Comment comment) {
                dataRepository.voteComment(comment.id, new DataRepository.VoteCommentListener() {
                    @Override
                    public void onResponse(ResponseComment response) {
                        Toast.makeText(getContext(), "Bravo za LAJK!", Toast.LENGTH_SHORT).show();

                        Vote vote = new Vote(comment.id, true);
                        votes.add(vote);
                        SharedPrefs.writeListInPref(requireActivity(), votes);

                        adapter.setupLike(comment.id);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(getContext(), "Doslo je do greske!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void dislike(Comment comment) {
                dataRepository.unVoteComment(comment.id, new DataRepository.VoteCommentListener() {
                    @Override
                    public void onResponse(ResponseComment response) {
                        Toast.makeText(getContext(), "Bravo za DISLAJK!", Toast.LENGTH_SHORT).show();

                        Vote vote = new Vote(comment.id, false);
                        votes.add(vote);
                        SharedPrefs.writeListInPref(requireActivity(), votes);

                        adapter.setupDislike(comment.id);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(getContext(), "Doslo je do greske!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        binding.recyclerViewHomepage.setAdapter(adapter);
    }

    public void loadDetailData() {

        binding.progressBar.setVisibility(View.VISIBLE);
        binding.recyclerViewHomepage.setVisibility(View.GONE);

        dataRepository.loadDetailData(newsId, new DataRepository.DetailResponseListener() {
            @Override
            public void onResponse(NewsDetail response) {
                adapter.setDataItems(response);

                newsId = response.id;
                newsUrl = response.url;
                title = response.title;

                Bundle bundle = new Bundle();
                bundle.putString("news", title);
                mFirebaseAnalytics.logEvent("selected_news", bundle);

                detailsListener.onDetailsResponseListener(newsId, newsUrl);

                binding.refresh.setVisibility(View.GONE);
                binding.progressBar.setVisibility(View.GONE);
                binding.recyclerViewHomepage.setVisibility(View.VISIBLE);
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