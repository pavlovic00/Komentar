package com.cubes.komentar.pavlovic.ui.main.search;

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
import android.widget.Toast;

import com.cubes.komentar.databinding.FragmentSearchBinding;
import com.cubes.komentar.pavlovic.data.model.News;
import com.cubes.komentar.pavlovic.data.repository.DataRepository;
import com.cubes.komentar.pavlovic.data.response.ResponseNewsList;
import com.cubes.komentar.pavlovic.data.tools.LoadingNewsListener;
import com.cubes.komentar.pavlovic.data.tools.NewsListener;

import java.util.ArrayList;


public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;
    private ArrayList<News> searchList;
    private SearchAdapter adapter;
    private int page = 1;


    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSearchBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.imageViewSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String term = binding.editTextSearch.getText().toString();

                if (term.length() < 3) {
                    Toast.makeText(getContext(),
                            "Unesite neki karakter za pretragu!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Log.d("TAG", "onClick:Search " + term);

                loadSearchData();
            }
        });

        refresh();

    }

    public void refresh() {

        binding.refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotate.setDuration(300);
                binding.refresh.startAnimation(rotate);
                loadSearchData();
            }
        });

    }

    //Load Search Data.
    public void loadSearchData() {

        DataRepository.getInstance().loadSearchData(String.valueOf(binding.editTextSearch.getText()), page, new DataRepository.SearchResponseListener() {
            @Override
            public void onResponse(ResponseNewsList responseNewsList) {
                searchList = responseNewsList.data.news;
                updateUI();

                binding.refresh.setVisibility(View.GONE);
                binding.recyclerViewSearch.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Throwable t) {
                binding.refresh.setVisibility(View.VISIBLE);
            }
        });

    }

    public void updateUI() {
        binding.recyclerViewSearch.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new SearchAdapter(getContext(), searchList);

        adapter.setNewsListener(new NewsListener() {
            @Override
            public void onNewsCLicked(News news) {
                DataRepository.getInstance().getNewsDetails(getContext(), news);
            }
        });

        loadMoreNews();

        binding.recyclerViewSearch.setAdapter(adapter);
    }

    public void loadMoreNews() {

        adapter.setLoadingNewsListener(new LoadingNewsListener() {
            @Override
            public void loadMoreNews(int page) {
                DataRepository.getInstance().loadSearchData(String.valueOf(binding.editTextSearch.getText()), page, new DataRepository.SearchResponseListener() {
                    @Override
                    public void onResponse(ResponseNewsList responseNewsList) {
                        adapter.addNewsList(responseNewsList.data.news);

                        if (responseNewsList.data.news.size() < 20) {
                            adapter.setFinished(true);
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        binding.recyclerViewSearch.setVisibility(View.GONE);
                        binding.refresh.setVisibility(View.VISIBLE);
                        adapter.setFinished(true);
                    }
                });
            }
        });

    }
}