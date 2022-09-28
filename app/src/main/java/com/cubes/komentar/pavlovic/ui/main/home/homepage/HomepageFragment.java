package com.cubes.komentar.pavlovic.ui.main.home.homepage;

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
import com.cubes.komentar.databinding.FragmentHomepageBinding;
import com.cubes.komentar.pavlovic.data.domain.CategoryBox;
import com.cubes.komentar.pavlovic.data.domain.News;
import com.cubes.komentar.pavlovic.data.domain.NewsList;
import com.cubes.komentar.pavlovic.data.domain.SaveNews;
import com.cubes.komentar.pavlovic.data.source.repository.DataRepository;
import com.cubes.komentar.pavlovic.di.AppContainer;
import com.cubes.komentar.pavlovic.di.MyApplication;
import com.cubes.komentar.pavlovic.ui.comments.AllCommentActivity;
import com.cubes.komentar.pavlovic.ui.details.DetailsActivity;
import com.cubes.komentar.pavlovic.ui.tools.MyMethodsClass;
import com.cubes.komentar.pavlovic.ui.tools.SharedPrefs;
import com.cubes.komentar.pavlovic.ui.tools.listener.NewsListener;

import java.util.ArrayList;

public class HomepageFragment extends Fragment {

    private FragmentHomepageBinding binding;
    private HomepageAdapter adapter;
    private DataRepository dataRepository;
    private ArrayList<SaveNews> saveNewsList = new ArrayList<>();
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

        if (SharedPrefs.showNewsFromPref(requireActivity()) != null) {
            saveNewsList = (ArrayList<SaveNews>) SharedPrefs.showNewsFromPref(requireActivity());
        }

        binding.recyclerViewHomepage.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new HomepageAdapter(new NewsListener() {
            @Override
            public void onNewsClickedVP(int newsId, int[] newsIdList) {
                Intent intent = new Intent(getContext(), DetailsActivity.class);
                intent.putExtra("news_id", newsId);
                intent.putExtra("news_list_id", newsListId);
                startActivity(intent);
            }

            @Override
            public void onCommentNewsClicked(int id) {
                Intent commentIntent = new Intent(getContext(), AllCommentActivity.class);
                commentIntent.putExtra("news_id", id);
                startActivity(commentIntent);
            }

            @Override
            public void onShareNewsClicked(String url) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_SEND);
                i.putExtra(Intent.EXTRA_TEXT, url);
                i.setType("text/plain");
                startActivity(Intent.createChooser(i, null));
            }

            @Override
            public void onSaveClicked(int id, String title) {
                SaveNews saveNews = new SaveNews(id, title);

                if (SharedPrefs.showNewsFromPref(requireActivity()) != null) {
                    saveNewsList = (ArrayList<SaveNews>) SharedPrefs.showNewsFromPref(requireActivity());

                    for (int i = 0; i < saveNewsList.size(); i++) {
                        if (saveNews.id == saveNewsList.get(i).id) {
                            saveNewsList.remove(saveNewsList.get(i));
                            SharedPrefs.saveNewsInPref(requireActivity(), saveNewsList);
                            Toast.makeText(getContext(), "Uspešno ste izbacili vest iz liste.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                }
                saveNewsList.add(saveNews);
                SharedPrefs.saveNewsInPref(getActivity(), saveNewsList);
                Toast.makeText(getContext(), "Uspešno ste sačuvali vest.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean isSaved(int id) {
                return MyMethodsClass.isSaved(id, requireActivity());
            }

        }, (url, title) -> {
            Intent i = new Intent();
            i.setAction(Intent.ACTION_SEND);
            i.putExtra(Intent.EXTRA_TEXT, url);
            i.putExtra("title", title);
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
                checkSave(response.top, saveNewsList);
                checkSave(response.videos, saveNewsList);

                for (CategoryBox categoryBox : response.category) {
                    checkSave(categoryBox.news, saveNewsList);
                }

                adapter.setDataItems(response);
                newsListId = getAllId(response);

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
            loadHomeData();
            binding.progressBar.setVisibility(View.GONE);
        });
    }
}