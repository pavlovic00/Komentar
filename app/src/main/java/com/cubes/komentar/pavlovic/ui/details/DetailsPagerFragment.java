package com.cubes.komentar.pavlovic.ui.details;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
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

import com.cubes.komentar.R;
import com.cubes.komentar.databinding.FragmentDetailsBinding;
import com.cubes.komentar.pavlovic.data.domain.Comment;
import com.cubes.komentar.pavlovic.data.domain.News;
import com.cubes.komentar.pavlovic.data.domain.NewsDetail;
import com.cubes.komentar.pavlovic.data.domain.SaveNews;
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
import com.cubes.komentar.pavlovic.ui.tools.listener.DetailsListener;
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
    private DetailListener detailListener;
    private SaveNews saveNews;
    private News news;
    private ArrayList<SaveNews> saveNewsList = new ArrayList<>();
    private ArrayList<Vote> votes = new ArrayList<>();
    private final ArrayList<Comment> allComments = new ArrayList<>();
    private DataRepository dataRepository;


    public interface DetailListener {
        void onDetailsResponseListener(int newsId, String newsUrl, SaveNews saveNews, News news);
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
        this.detailListener = (DetailListener) context;
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

        binding.swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.purple_light));
        binding.swipeRefresh.setOnRefreshListener(() -> {
            setupRecyclerView();
            loadDetailData();
            binding.progressBar.setVisibility(View.GONE);
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.nestedScrollView.setOnScrollChangeListener((View.OnScrollChangeListener) (view1, i, i1, i2, i3) -> {
                int length = binding.nestedScrollView.getChildAt(0).getHeight() - binding.nestedScrollView.getHeight();

                binding.progressIndicator.setMax(length);
                binding.progressIndicator.setProgress(i1);
            });
        }

        setupRecyclerView();
        loadDetailData();
        refresh();
    }

    @Override
    public void onResume() {
        super.onResume();

        detailListener.onDetailsResponseListener(newsId, newsUrl, saveNews, news);
    }

    private void setupRecyclerView() {

        if (SharedPrefs.showNewsFromPref(requireActivity()) != null) {
            saveNewsList = (ArrayList<SaveNews>) SharedPrefs.showNewsFromPref(requireActivity());
        }

        allComments.clear();

        if (SharedPrefs.readListFromPref(requireActivity()) != null) {
            votes = (ArrayList<Vote>) SharedPrefs.readListFromPref(requireActivity());
        }

        binding.recyclerViewDetails.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new DetailsAdapter(new DetailsListener() {
            @Override
            public void onNewsClickedVP(int newsId, int[] newsIdList) {
                Intent intent = new Intent(getContext(), DetailsActivity.class);
                intent.putExtra("news_id", newsId);
                intent.putExtra("news_list_id", newsIdList);
                startActivity(intent);
            }

            @Override
            public void onSaveClicked(int id, String title) {
                SaveNews saveNews = new SaveNews(id, title);

                if (SharedPrefs.showNewsFromPref(requireActivity()) != null) {
                    saveNewsList = (ArrayList<SaveNews>) SharedPrefs.showNewsFromPref(requireActivity());

                    for (int i = 0; i < saveNewsList.size(); i++) {
                        if (saveNews.id == saveNewsList.get(i).id) {
                            Toast.makeText(getContext(), "VEST JE SACUVANA!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }
                saveNewsList.add(saveNews);
                SharedPrefs.saveNewsInPref(requireActivity(), saveNewsList);
            }

            @Override
            public void onUnSaveClicked(int id, String title) {
                SaveNews saveNews = new SaveNews(id, title);

                for (int i = 0; i < saveNewsList.size(); i++) {
                    if (saveNews.id == saveNewsList.get(i).id) {
                        saveNewsList.remove(saveNewsList.get(i));
                        SharedPrefs.saveNewsInPref(requireActivity(), saveNewsList);
                    }
                }
            }

            @Override
            public void onTagClicked(Tags tags) {
                Intent tagsIntent = new Intent(getContext(), TagsActivity.class);
                tagsIntent.putExtra("id", tags.id);
                tagsIntent.putExtra("title", tags.title);
                startActivity(tagsIntent);
            }

            @Override
            public void onPutCommentClicked(NewsDetail data) {
                Intent i = new Intent(getContext(), PostCommentActivity.class);
                i.putExtra("news_id", data.id);
                startActivity(i);
            }

            @Override
            public void onAllCommentClicked(NewsDetail data) {
                Intent commentIntent = new Intent(getContext(), AllCommentActivity.class);
                commentIntent.putExtra("news_id", data.id);
                startActivity(commentIntent);
            }

            @Override
            public void onCommentClicked(Comment comment) {
                Intent replyIntent = new Intent(getContext(), PostCommentActivity.class);
                replyIntent.putExtra("reply_id", comment.commentId);
                replyIntent.putExtra("news", comment.newsId);
                startActivity(replyIntent);
            }

            @Override
            public void like(Comment comment) {
                dataRepository.voteComment(comment.commentId, new DataRepository.VoteCommentListener() {
                    @Override
                    public void onResponse(ResponseComment response) {
                        Toast.makeText(getContext(), "Bravo za LAJK!", Toast.LENGTH_SHORT).show();

                        Vote vote = new Vote(comment.commentId, true);
                        votes.add(vote);
                        SharedPrefs.writeListInPref(requireActivity(), votes);

                        adapter.setupLike(comment.commentId);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(getContext(), "Doslo je do greske!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void dislike(Comment comment) {
                dataRepository.unVoteComment(comment.commentId, new DataRepository.VoteCommentListener() {
                    @Override
                    public void onResponse(ResponseComment response) {
                        Toast.makeText(getContext(), "Bravo za DISLAJK!", Toast.LENGTH_SHORT).show();

                        Vote vote = new Vote(comment.commentId, false);
                        votes.add(vote);
                        SharedPrefs.writeListInPref(requireActivity(), votes);

                        adapter.setupDislike(comment.commentId);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(getContext(), "Doslo je do greske!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        binding.recyclerViewDetails.setAdapter(adapter);

    }

    public void loadDetailData() {

        binding.progressBar.setVisibility(View.VISIBLE);
        binding.linearLayout.setVisibility(View.GONE);
        binding.left.setVisibility(View.GONE);
        binding.right.setVisibility(View.GONE);

        dataRepository.loadDetailData(newsId, new DataRepository.DetailResponseListener() {
            @Override
            public void onResponse(NewsDetail response) {
                checkSaveRelated(response.relatedNews, saveNewsList);
                adapter.setDataItems(response, () -> {
                    binding.progressBar.setVisibility(View.GONE);
                    binding.linearLayout.setVisibility(View.VISIBLE);
                    binding.left.setVisibility(View.VISIBLE);
                    binding.right.setVisibility(View.VISIBLE);
                    binding.left.animate().alpha(0).setDuration(1500);
                    binding.right.animate().alpha(0).setDuration(1500);
                });

                setDataComment(response.topComments);

                newsId = response.id;
                newsUrl = response.url;
                title = response.title;

                Bundle bundle = new Bundle();
                bundle.putString("news", title);
                mFirebaseAnalytics.logEvent("android_komentar", bundle);

                saveNews = new SaveNews(response.id, response.title);
                news = new News();
                news.id = response.id;
                news.title = response.title;
                checkSave(response.id);

                detailListener.onDetailsResponseListener(newsId, newsUrl, saveNews, news);

                binding.refresh.setVisibility(View.GONE);
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
        allComments.addAll(comments);

        if (votes != null) {
            setVoteData(allComments, votes);
        }

        adapter.updateCommentsList(allComments);
    }

    private void setVoteData(ArrayList<Comment> allComments, ArrayList<Vote> votes) {
        for (Comment comment : allComments) {
            for (Vote vote : votes) {
                if (comment.commentId.equals(vote.commentId)) {
                    comment.vote = vote;
                }
            }
        }
    }

    public void checkSave(int newsId) {

        if (SharedPrefs.showNewsFromPref(requireActivity()) != null) {
            ArrayList<SaveNews> saveNews = (ArrayList<SaveNews>) SharedPrefs.showNewsFromPref(requireActivity());

            for (SaveNews save : saveNews) {
                if (newsId == save.id) {
                    news.isSaved = true;
                    break;
                }
            }
        }
    }

    public void checkSaveRelated(ArrayList<News> newsList, ArrayList<SaveNews> saveNews) {
        for (News news : newsList) {
            for (SaveNews save : saveNews) {
                if (news.id == save.id) {
                    news.isSaved = true;
                    break;
                }
            }
        }
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