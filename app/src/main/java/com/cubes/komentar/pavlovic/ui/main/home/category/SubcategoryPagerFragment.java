package com.cubes.komentar.pavlovic.ui.main.home.category;

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
import com.cubes.komentar.databinding.FragmentViewPagerSubcategoryBinding;
import com.cubes.komentar.pavlovic.data.domain.News;
import com.cubes.komentar.pavlovic.data.domain.SaveNews;
import com.cubes.komentar.pavlovic.data.source.repository.DataRepository;
import com.cubes.komentar.pavlovic.di.AppContainer;
import com.cubes.komentar.pavlovic.di.MyApplication;
import com.cubes.komentar.pavlovic.ui.comments.AllCommentActivity;
import com.cubes.komentar.pavlovic.ui.details.DetailsActivity;
import com.cubes.komentar.pavlovic.ui.main.latest.LatestAdapter;
import com.cubes.komentar.pavlovic.ui.tag.TagsActivity;
import com.cubes.komentar.pavlovic.ui.tools.MyMethodsClass;
import com.cubes.komentar.pavlovic.ui.tools.SharedPrefs;
import com.cubes.komentar.pavlovic.ui.tools.listener.NewsListener;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;

public class SubcategoryPagerFragment extends Fragment {

    private static final String ARG_CATEGORY_ID = "category_id";
    private static final String SUBCATEGORY_NAME = "subcategoryName";
    private FragmentViewPagerSubcategoryBinding binding;
    private int mCategoryId;
    private LatestAdapter adapter;
    private String subcategoryName;
    private int nextPage = 2;
    private FirebaseAnalytics mFirebaseAnalytics;
    private ArrayList<SaveNews> saveNewsList = new ArrayList<>();
    private DataRepository dataRepository;


    public SubcategoryPagerFragment() {
    }

    public static SubcategoryPagerFragment newInstance(String subcategoryName, int categoryId) {
        SubcategoryPagerFragment fragment = new SubcategoryPagerFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CATEGORY_ID, categoryId);
        args.putString(SUBCATEGORY_NAME, subcategoryName);
        fragment.setArguments(args);
        fragment.mCategoryId = categoryId;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mCategoryId = getArguments().getInt(ARG_CATEGORY_ID);
            subcategoryName = getArguments().getString(SUBCATEGORY_NAME);
        }

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(requireActivity());
        AppContainer appContainer = ((MyApplication) requireActivity().getApplication()).appContainer;
        dataRepository = appContainer.dataRepository;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentViewPagerSubcategoryBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.purple_light));
        binding.swipeRefresh.setOnRefreshListener(() -> {
            setupRecyclerView();
            loadCategoriesHomeData();
            binding.progressBar.setVisibility(View.GONE);
        });

        setupRecyclerView();
        loadCategoriesHomeData();
        refresh();
    }

    public void setupRecyclerView() {
        if (SharedPrefs.showNewsFromPref(requireActivity()) != null) {
            saveNewsList = (ArrayList<SaveNews>) SharedPrefs.showNewsFromPref(requireActivity());
        }

        binding.recyclerViewPager2.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new LatestAdapter((new NewsListener() {
            @Override
            public void onNewsClickedVP(int newsId, int[] newsIdList) {
                Intent intent = new Intent(getContext(), DetailsActivity.class);
                intent.putExtra("news_id", newsId);
                intent.putExtra("news_list_id", newsIdList);
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

        }), () -> dataRepository.loadCategoriesNewsData(mCategoryId, nextPage, new DataRepository.CategoriesNewsResponseListener() {
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
                binding.recyclerViewPager2.setVisibility(View.GONE);
                binding.refresh.setVisibility(View.VISIBLE);
            }
        }));

        binding.recyclerViewPager2.setAdapter(adapter);
    }

    public void loadCategoriesHomeData() {

        Bundle bundle = new Bundle();
        bundle.putString("subcategory", subcategoryName);
        mFirebaseAnalytics.logEvent("android_komentar", bundle);

        binding.progressBar.setVisibility(View.VISIBLE);
        binding.recyclerViewPager2.setVisibility(View.GONE);

        dataRepository.loadCategoriesNewsData(mCategoryId, 0, new DataRepository.CategoriesNewsResponseListener() {
            @Override
            public void onResponse(ArrayList<News> response) {
                checkSave(response, saveNewsList);

                adapter.setData(response);
                nextPage = 2;

                binding.refresh.setVisibility(View.GONE);
                binding.progressBar.setVisibility(View.GONE);
                binding.recyclerViewPager2.setVisibility(View.VISIBLE);
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
            loadCategoriesHomeData();
            binding.progressBar.setVisibility(View.GONE);
        });
    }
}