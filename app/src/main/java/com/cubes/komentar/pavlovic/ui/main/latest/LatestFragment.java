package com.cubes.komentar.pavlovic.ui.main.latest;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import com.cubes.komentar.databinding.FragmentLatestBinding;
import com.cubes.komentar.pavlovic.data.tools.LoadingNewsListener;
import com.cubes.komentar.pavlovic.data.tools.NewsListener;
import com.cubes.komentar.pavlovic.data.model.News;
import com.cubes.komentar.pavlovic.data.repository.DataRepository;
import com.cubes.komentar.pavlovic.data.response.ResponseNewsList;
import com.cubes.komentar.pavlovic.ui.details.NewsDetailActivity;

public class LatestFragment extends Fragment {

    private FragmentLatestBinding binding;
    private LatestAdapter adapter;
    private int page = 1;


    public LatestFragment() {
        // Required empty public constructor
    }

    public static LatestFragment newInstance() {
        LatestFragment fragment = new LatestFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentLatestBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupRecyclerView();
        loadDataLatest();
        refresh();
    }

    public void setupRecyclerView(){
        binding.recyclerViewLatest.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new LatestAdapter();
        binding.recyclerViewLatest.setAdapter(adapter);

        adapter.setNewsListener(new NewsListener() {
            @Override
            public void onNewsCLicked(News news) {
                Intent i = new Intent(getContext(), NewsDetailActivity.class);
                i.putExtra("id", news.id);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(i);
            }
        });

        adapter.setLoadingNewsListener(new LoadingNewsListener() {
            @Override
            public void loadMoreNews(int page) {
                DataRepository.getInstance().loadLatestData(page, new DataRepository.LatestResponseListener() {
                    @Override
                    public void onResponse(ResponseNewsList.ResponseData response) {
                        adapter.addNewsList(response.news);

                        if (response.news.size() < 20) {
                            adapter.setFinished(true);
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        binding.recyclerViewLatest.setVisibility(View.GONE);
                        binding.refresh.setVisibility(View.VISIBLE);
                        adapter.setFinished(true);
                    }
                });
            }
        });
    }

    public void loadDataLatest() {

        DataRepository.getInstance().loadLatestData(page, new DataRepository.LatestResponseListener() {
            @Override
            public void onResponse(ResponseNewsList.ResponseData response) {

                adapter.setData(response);

                binding.refresh.setVisibility(View.GONE);
                binding.recyclerViewLatest.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Throwable t) {
                binding.refresh.setVisibility(View.VISIBLE);
            }
        });

    }

    public void refresh() {

        binding.refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotate.setDuration(300);
                binding.refresh.startAnimation(rotate);
                loadDataLatest();
            }
        });

    }

}