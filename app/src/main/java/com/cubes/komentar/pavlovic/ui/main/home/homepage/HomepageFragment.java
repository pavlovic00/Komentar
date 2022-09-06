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

import com.cubes.komentar.R;
import com.cubes.komentar.databinding.FragmentHomepageBinding;
import com.cubes.komentar.pavlovic.data.domain.CategoryBox;
import com.cubes.komentar.pavlovic.data.domain.News;
import com.cubes.komentar.pavlovic.data.domain.NewsList;
import com.cubes.komentar.pavlovic.data.source.repository.DataRepository;
import com.cubes.komentar.pavlovic.di.AppContainer;
import com.cubes.komentar.pavlovic.di.MyApplication;
import com.cubes.komentar.pavlovic.ui.details.DetailsActivity;
import com.cubes.komentar.pavlovic.ui.tools.MyMethodsClass;

import java.util.ArrayList;

public class HomepageFragment extends Fragment {

    private FragmentHomepageBinding binding;
    private HomepageAdapter adapter;
    private DataRepository dataRepository;
    private int[] newsListId;


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

        binding.swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.purple_light));
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
            intent.putExtra("news_list_id", newsListId);
            startActivity(intent);
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
                newsListId = getAllId(response);
                binding.recyclerViewHomepage.setItemViewCacheSize(50);

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

    private int[] getAllId(NewsList response) {

        ArrayList<News> allNews = new ArrayList<>();

        allNews.addAll(response.slider);
        allNews.addAll(response.top);
        allNews.addAll(response.latest);
        allNews.addAll(response.mostRead);
        allNews.addAll(response.mostCommented);
        for (CategoryBox categoryBox : response.category) {
            if (categoryBox.title.equalsIgnoreCase("Sport")) {
                allNews.addAll(categoryBox.news);
            }
        }
        allNews.addAll(response.videos);
        for (CategoryBox categoryBox : response.category) {
            if (!categoryBox.title.equalsIgnoreCase("Sport")) {
                allNews.addAll(categoryBox.news);
            }
        }

        return MyMethodsClass.initNewsIdList(allNews);
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