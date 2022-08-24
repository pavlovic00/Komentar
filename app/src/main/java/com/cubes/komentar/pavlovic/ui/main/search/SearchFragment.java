package com.cubes.komentar.pavlovic.ui.main.search;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cubes.komentar.databinding.FragmentSearchBinding;
import com.cubes.komentar.pavlovic.data.domain.News;
import com.cubes.komentar.pavlovic.data.source.repository.DataRepository;
import com.cubes.komentar.pavlovic.ui.details.NewsDetailActivity;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;


public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;
    private SearchAdapter adapter;
    private int nextPage = 2;
    private FirebaseAnalytics mFirebaseAnalytics;


    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSearchBinding.inflate(inflater, container, false);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(requireActivity());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.editTextSearch.post(() -> {
            binding.editTextSearch.requestFocus();
            InputMethodManager i = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            i.showSoftInput(binding.editTextSearch, InputMethodManager.SHOW_IMPLICIT);
        });

        binding.editTextSearch.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                binding.progressBar.setVisibility(View.VISIBLE);
                binding.recyclerViewSearch.setVisibility(View.INVISIBLE);
                String term = binding.editTextSearch.getText().toString();
                if (term.length() < 3) {
                    Toast.makeText(getContext(),
                            "Unesite karakter za pretragu!", Toast.LENGTH_SHORT).show();
                    binding.progressBar.setVisibility(View.GONE);
                } else {
                    setupRecyclerView();
                    loadSearchData();
                    hideKeyboard(requireActivity());
                    binding.obavestenje.setVisibility(View.GONE);
                    binding.imageViewObavestenje.setVisibility(View.GONE);
                    return true;
                }
            }
            return false;
        });

        binding.imageViewSearch.setOnClickListener(view1 -> {

            String term = binding.editTextSearch.getText().toString();

            if (term.length() < 3) {
                Toast.makeText(getContext(),
                        "Unesite karakter za pretragu!", Toast.LENGTH_SHORT).show();

            } else {
                setupRecyclerView();
                loadSearchData();
                hideKeyboard(requireActivity());
                binding.obavestenje.setVisibility(View.GONE);
                binding.imageViewObavestenje.setVisibility(View.GONE);
            }
        });

        binding.swipeRefresh.setOnRefreshListener(() -> {
            String term = binding.editTextSearch.getText().toString();

            if (term.length() < 3) {
                Toast.makeText(getContext(),
                        "Unesite karakter za pretragu!", Toast.LENGTH_SHORT).show();
                binding.swipeRefresh.setRefreshing(false);
                return;
            }
            setupRecyclerView();
            loadSearchData();
            binding.progressBar.setVisibility(View.GONE);
            binding.swipeRefresh.setRefreshing(false);
        });

        refresh();
    }

    public void setupRecyclerView() {
        binding.recyclerViewSearch.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new SearchAdapter();
        binding.recyclerViewSearch.setAdapter(adapter);


        adapter.setNewsListener(news -> {
            Intent i = new Intent(getContext(), NewsDetailActivity.class);
            i.putExtra("id", news.id);
            i.putExtra("title", news.title);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        });

        adapter.setLoadingNewsListener(() -> DataRepository.getInstance().loadSearchData(String.valueOf(binding.editTextSearch.getText()), nextPage, new DataRepository.SearchResponseListener() {
            @Override
            public void onResponse(ArrayList<News> responseData) {
                adapter.addNewsList(responseData);

                nextPage++;
            }

            @Override
            public void onFailure(Throwable t) {
                binding.recyclerViewSearch.setVisibility(View.GONE);
                binding.refresh.setVisibility(View.VISIBLE);
            }
        }));


    }

    public void loadSearchData() {

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SEARCH_TERM, String.valueOf(binding.editTextSearch.getText()));
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SEARCH, bundle);

        binding.progressBar.setVisibility(View.VISIBLE);
        binding.recyclerViewSearch.setVisibility(View.GONE);

        DataRepository.getInstance().loadSearchData(String.valueOf(binding.editTextSearch.getText()), 1, new DataRepository.SearchResponseListener() {
            @Override
            public void onResponse(ArrayList<News> responseData) {

                setupRecyclerView();

                adapter.setDataSearch(responseData);

                nextPage = 2;

                binding.refresh.setVisibility(View.GONE);
                binding.progressBar.setVisibility(View.GONE);
                binding.recyclerViewSearch.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                binding.refresh.setVisibility(View.VISIBLE);
            }
        });

    }

    public void refresh() {

        binding.refresh.setOnClickListener(view -> {

            RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotate.setDuration(300);
            binding.refresh.startAnimation(rotate);
            setupRecyclerView();
            loadSearchData();
            binding.progressBar.setVisibility(View.GONE);
        });

    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}