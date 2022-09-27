package com.cubes.komentar.pavlovic.ui.main.latest;

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

import com.cubes.komentar.R;
import com.cubes.komentar.databinding.FragmentLatestBinding;
import com.cubes.komentar.pavlovic.data.domain.News;
import com.cubes.komentar.pavlovic.data.domain.SaveNews;
import com.cubes.komentar.pavlovic.data.source.repository.DataRepository;
import com.cubes.komentar.pavlovic.di.AppContainer;
import com.cubes.komentar.pavlovic.di.MyApplication;
import com.cubes.komentar.pavlovic.ui.details.DetailsActivity;
import com.cubes.komentar.pavlovic.ui.tools.SharedPrefs;
import com.cubes.komentar.pavlovic.ui.tools.listener.NewsListener;

import java.util.ArrayList;

public class LatestFragment extends Fragment {

    private FragmentLatestBinding binding;
    private LatestAdapter adapter;
    private int nextPage = 2;
    private ArrayList<SaveNews> saveNewsList = new ArrayList<>();
    private DataRepository dataRepository;


    public static LatestFragment newInstance() {
        return new LatestFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppContainer appContainer = ((MyApplication) requireActivity().getApplication()).appContainer;
        dataRepository = appContainer.dataRepository;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentLatestBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.purple_light));
        binding.swipeRefresh.setOnRefreshListener(() -> {
            setupRecyclerView();
            loadDataLatest();
            binding.progressBar.setVisibility(View.GONE);
        });

        setupRecyclerView();
        loadDataLatest();
        refresh();
    }

    public void setupRecyclerView() {

        if (SharedPrefs.showNewsFromPref(requireActivity()) != null) {
            saveNewsList = (ArrayList<SaveNews>) SharedPrefs.showNewsFromPref(requireActivity());
        }

        binding.recyclerViewLatest.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new LatestAdapter((new NewsListener() {
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
        }), () -> dataRepository.loadLatestData(nextPage, new DataRepository.LatestResponseListener() {
            @Override
            public void onResponse(ArrayList<News> response) {

                if (response == null || response.size() == 0) {
                    adapter.removeItem();
                } else {
                    checkSave(response, saveNewsList);
                    adapter.addNewsList(response);
                    nextPage++;
                }
            }

            @Override
            public void onFailure(Throwable t) {
                binding.recyclerViewLatest.setVisibility(View.GONE);
                binding.refresh.setVisibility(View.VISIBLE);
            }
        }));

        binding.recyclerViewLatest.setAdapter(adapter);
    }

    public void loadDataLatest() {

        binding.progressBar.setVisibility(View.VISIBLE);
        binding.recyclerViewLatest.setVisibility(View.GONE);

        dataRepository.loadLatestData(1, new DataRepository.LatestResponseListener() {
            @Override
            public void onResponse(ArrayList<News> response) {
                checkSave(response, saveNewsList);

                adapter.setData(response);
                nextPage = 2;

                binding.refresh.setVisibility(View.GONE);
                binding.progressBar.setVisibility(View.GONE);
                binding.recyclerViewLatest.setVisibility(View.VISIBLE);
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

    public void checkSave(ArrayList<News> newsList, ArrayList<SaveNews> saveNews) {
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
            setupRecyclerView();
            loadDataLatest();
            binding.progressBar.setVisibility(View.GONE);
        });
    }
}