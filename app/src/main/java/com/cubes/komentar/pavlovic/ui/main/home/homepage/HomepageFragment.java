package com.cubes.komentar.pavlovic.ui.main.home.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cubes.komentar.databinding.FragmentHomepageBinding;
import com.cubes.komentar.pavlovic.data.domain.NewsList;
import com.cubes.komentar.pavlovic.data.source.repository.DataRepository;
import com.cubes.komentar.pavlovic.di.AppContainer;
import com.cubes.komentar.pavlovic.di.MyApplication;
import com.cubes.komentar.pavlovic.ui.details.DetailsActivity;

public class HomepageFragment extends Fragment {

    private FragmentHomepageBinding binding;
    public HomepageAdapter adapter;
    private DataRepository dataRepository;


    public static HomepageFragment newInstance() {
        return new HomepageFragment();
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
        binding = FragmentHomepageBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.swipeRefresh.setOnRefreshListener(() -> {
            setupRecyclerView();
            loadHomeData();
            binding.progressBar.setVisibility(View.GONE);
        });

        setupRecyclerView();
        loadHomeData();
        refresh();
    }

    public void setupRecyclerView() {

        binding.recyclerViewHomepage.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new HomepageAdapter((newsId, newsIdList) -> {
            Intent intent = new Intent(getContext(), DetailsActivity.class);
            intent.putExtra("news_id", newsId);
            intent.putExtra("news_list_id", newsIdList);
            startActivity(intent);
        }, news -> {
            Intent i = new Intent();
            i.setAction(Intent.ACTION_SEND);
            i.putExtra(Intent.EXTRA_TEXT, news.url);
            i.putExtra("title", news.title);
            i.setType("text/plain");
            Intent shareIntent = Intent.createChooser(i, null);
            startActivity(shareIntent);
        });

        binding.recyclerViewHomepage.setAdapter(adapter);
    }

    public void loadHomeData() {

        binding.progressBar.setVisibility(View.VISIBLE);
        binding.recyclerViewHomepage.setVisibility(View.GONE);

        dataRepository.loadHomeData(new DataRepository.HomeResponseListener() {
            @Override
            public void onResponse(NewsList response) {

                adapter.setDataItems(response);

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
            loadHomeData();
            binding.progressBar.setVisibility(View.GONE);
        });
    }
}