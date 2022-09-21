package com.cubes.komentar.pavlovic.ui.main.menu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cubes.komentar.R;
import com.cubes.komentar.databinding.ActivityHomeBinding;
import com.cubes.komentar.pavlovic.data.domain.Category;
import com.cubes.komentar.pavlovic.data.source.repository.DataRepository;
import com.cubes.komentar.pavlovic.di.AppContainer;
import com.cubes.komentar.pavlovic.di.MyApplication;
import com.cubes.komentar.pavlovic.ui.main.home.HomeFragment;
import com.cubes.komentar.pavlovic.ui.main.home.category.CategoryAdapter;
import com.cubes.komentar.pavlovic.ui.main.home.category.SubcategoryActivity;
import com.cubes.komentar.pavlovic.ui.main.latest.LatestFragment;
import com.cubes.komentar.pavlovic.ui.main.search.SearchFragment;
import com.cubes.komentar.pavlovic.ui.main.video.VideoFragment;
import com.cubes.komentar.pavlovic.ui.tools.SharedPrefs;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;


public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private DataRepository dataRepository;


    @SuppressLint({"NonConstantResourceId", "RtlHardcoded"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        AppContainer appContainer = ((MyApplication) getApplication()).appContainer;
        dataRepository = appContainer.dataRepository;

        binding.adsView.setVisibility(View.GONE);
        binding.shimmerLayout.setVisibility(View.VISIBLE);
        binding.shimmerLayout.startShimmerAnimation();

        AdRequest adRequest = new AdRequest.Builder().build();
        binding.adsView.loadAd(adRequest);

        binding.adsView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                binding.shimmerLayout.stopShimmerAnimation();
                binding.shimmerLayout.setVisibility(View.GONE);
                binding.adsView.setVisibility(View.VISIBLE);
                binding.closeAbs.setVisibility(View.VISIBLE);
            }
        });

        binding.closeAbs.setOnClickListener(view14 -> {
            binding.adsView.setVisibility(View.GONE);
            binding.closeAbs.setVisibility(View.GONE);
        });

        boolean isOn = SharedPrefs.isNotificationOn(HomeActivity.this);
        binding.switchNotification.setChecked(isOn);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.homeLayout, HomeFragment.newInstance())
                .commit();
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            Fragment selectedFragment = null;

            switch (item.getItemId()) {

                case R.id.home:
                    selectedFragment = HomeFragment.newInstance();
                {
                    binding.imageRight.setVisibility(View.VISIBLE);
                }
                break;
                case R.id.latest:
                    selectedFragment = LatestFragment.newInstance();
                {
                    binding.imageRight.setVisibility(View.GONE);
                }
                break;
                case R.id.video:
                    selectedFragment = VideoFragment.newInstance();
                {
                    binding.imageRight.setVisibility(View.GONE);
                }
                break;
                case R.id.search:
                    selectedFragment = SearchFragment.newInstance();
                {
                    binding.imageRight.setVisibility(View.GONE);
                }
                break;
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.homeLayout, selectedFragment)
                        .commit();
            }
            return true;
        });
        //Lock drawer slide.
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        //Click menu.
        binding.imageRight.setOnClickListener(view19 -> {
            binding.logo.setImageResource(R.drawable.ic_komentar_logo);

            dataRepository.loadCategoriesData(new DataRepository.CategoriesResponseListener() {
                @Override
                public void onResponse(ArrayList<Category> response) {
                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    binding.recyclerView.setAdapter(new CategoryAdapter(response, (categoryId, subcategoryId) -> {
                        Intent i = new Intent(getApplicationContext(), SubcategoryActivity.class);
                        i.putExtra("category_id", categoryId);
                        i.putExtra("subcategory_id", subcategoryId);
                        startActivity(i);
                    }));
                }

                @Override
                public void onFailure(Throwable t) {

                }
            });

            //Za ovo sam se najvise namucio :D
            binding.drawerLayout.openDrawer(Gravity.RIGHT);
        });
        //Close menu.
        binding.imageClose.setOnClickListener(view18 -> binding.drawerLayout.closeDrawer(Gravity.RIGHT));

        binding.sacuvaneVesti.setOnClickListener(view110 -> {
            Intent i = new Intent(getApplicationContext(), SaveNewsActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplicationContext().startActivity(i);
        });
        binding.vremenskaPrognoza.setOnClickListener(view17 -> {
            //Novi activity.
            WeatherActivity.start(HomeActivity.this);
        });
        binding.kursnaLista.setOnClickListener(view16 -> {
            //Novi activity.
            CourseListActivity.start(HomeActivity.this);
        });
        binding.horoskop.setOnClickListener(view15 -> {
            //Novi activity.
            HoroscopeActivity.start(HomeActivity.this);
        });
        binding.switchNotification.setOnCheckedChangeListener((compoundButton, b) -> {
            SharedPrefs.setNotificationStatus(HomeActivity.this, b);

            if (b) {
                FirebaseMessaging.getInstance().subscribeToTopic("main");
            } else {
                FirebaseMessaging.getInstance().unsubscribeFromTopic("main");
            }

        });
        binding.marketing.setOnClickListener(view13 -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "https://komentar.rs/wp-json/");
            sendIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sendIntent, null);
            HomeActivity.this.startActivity(shareIntent);
        });
        binding.usloviKoriscenja.setOnClickListener(view12 -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "https://komentar.rs/wp-json/");
            sendIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sendIntent, null);
            HomeActivity.this.startActivity(shareIntent);
        });
        binding.kontakt.setOnClickListener(view1 -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "https://komentar.rs/wp-json/");
            sendIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sendIntent, null);
            HomeActivity.this.startActivity(shareIntent);
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.scrollView.setOnScrollChangeListener((View.OnScrollChangeListener) (view1, i, i1, i2, i3) -> {
                int length = binding.scrollView.getChildAt(0).getHeight() - binding.scrollView.getHeight();

                binding.progressIndicator.setMax(length);
                binding.progressIndicator.setProgress(i1);
            });
        }

        binding.logo.setImageResource(R.drawable.ic_komentar_logo);

        loadHomeData();
    }

    @Override
    public void onBackPressed() {
        if (this.binding.drawerLayout.isDrawerOpen(GravityCompat.END)) {
            this.binding.drawerLayout.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

    public void loadHomeData() {

        dataRepository.loadCategoriesData(new DataRepository.CategoriesResponseListener() {
            @Override
            public void onResponse(ArrayList<Category> response) {
                binding.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                binding.recyclerView.setAdapter(new CategoryAdapter(response, (categoryId, subcategoryId) -> {
                    Intent i = new Intent(getApplicationContext(), SubcategoryActivity.class);
                    i.putExtra("category_id", categoryId);
                    i.putExtra("subcategory_id", subcategoryId);
                    startActivity(i);
                }));
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

    }
}