package com.cubes.komentar.pavlovic.ui.main.video;

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
import com.cubes.komentar.databinding.FragmentVideoBinding;
import com.cubes.komentar.pavlovic.data.domain.News;
import com.cubes.komentar.pavlovic.data.domain.SaveNews;
import com.cubes.komentar.pavlovic.data.source.repository.DataRepository;
import com.cubes.komentar.pavlovic.di.AppContainer;
import com.cubes.komentar.pavlovic.di.MyApplication;
import com.cubes.komentar.pavlovic.ui.details.DetailsActivity;
import com.cubes.komentar.pavlovic.ui.tools.SharedPrefs;
import com.cubes.komentar.pavlovic.ui.tools.listener.NewsListener;

import java.util.ArrayList;

public class VideoFragment extends Fragment {

    private FragmentVideoBinding binding;
    private VideoAdapter adapter;
    private int nextPage = 2;
    private ArrayList<SaveNews> saveNewsList = new ArrayList<>();
    private DataRepository dataRepository;


    public static VideoFragment newInstance() {
        return new VideoFragment();
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

        binding = FragmentVideoBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.purple_light));
        binding.swipeRefresh.setOnRefreshListener(() -> {
            setupRecyclerView();
            loadDataVideo();
            binding.progressBar.setVisibility(View.GONE);
        });

        setupRecyclerView();
        loadDataVideo();
        refresh();
    }

    public void setupRecyclerView() {
        if (SharedPrefs.showNewsFromPref(requireActivity()) != null) {
            saveNewsList = (ArrayList<SaveNews>) SharedPrefs.showNewsFromPref(requireActivity());
        }

        binding.recyclerViewVideo.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new VideoAdapter((new NewsListener() {
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
        }), () -> dataRepository.loadVideoData(nextPage, new DataRepository.VideoResponseListener() {
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
                binding.recyclerViewVideo.setVisibility(View.GONE);
                binding.refresh.setVisibility(View.VISIBLE);
            }
        }), (url, title) -> {
            Intent i = new Intent();
            i.setAction(Intent.ACTION_SEND);
            i.putExtra(Intent.EXTRA_TEXT, url);
            i.putExtra("title", title);
            i.setType("text/plain");
            Intent shareIntent = Intent.createChooser(i, null);
            startActivity(shareIntent);
        });

        binding.recyclerViewVideo.setAdapter(adapter);
    }

    public void loadDataVideo() {

        binding.progressBar.setVisibility(View.VISIBLE);
        binding.recyclerViewVideo.setVisibility(View.GONE);

        dataRepository.loadVideoData(0, new DataRepository.VideoResponseListener() {
            @Override
            public void onResponse(ArrayList<News> response) {
                checkSave(response, saveNewsList);

                adapter.setData(response);
                nextPage = 2;

                binding.refresh.setVisibility(View.GONE);
                binding.progressBar.setVisibility(View.GONE);
                binding.recyclerViewVideo.setVisibility(View.VISIBLE);
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
            loadDataVideo();
            binding.progressBar.setVisibility(View.GONE);
        });
    }
}