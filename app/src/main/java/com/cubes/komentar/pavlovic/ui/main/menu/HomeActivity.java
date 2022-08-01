package com.cubes.komentar.pavlovic.ui.main.menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.cubes.komentar.R;
import com.cubes.komentar.databinding.ActivityHomeBinding;
import com.cubes.komentar.pavlovic.data.repository.DataRepository;
import com.cubes.komentar.pavlovic.data.response.ResponseCategories;
import com.cubes.komentar.pavlovic.ui.main.home.HomeFragment;
import com.cubes.komentar.pavlovic.ui.main.home.category.CategoryAdapter;
import com.cubes.komentar.pavlovic.ui.main.latest.LatestFragment;
import com.cubes.komentar.pavlovic.ui.main.search.SearchFragment;
import com.cubes.komentar.pavlovic.ui.main.video.VideoFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private boolean click = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.homeLayout, HomeFragment.newInstance())
                .commit();

        binding.bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

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

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.homeLayout, selectedFragment)
                        .commit();

                return true;
            }
        });
        //Lock drawer slide.
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        //Click menu.
        binding.imageRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.logo.setImageResource(R.drawable.ic_komentar_logo);

                DataRepository.getInstance().loadCategoriesData(new DataRepository.CategoriesResponseListener() {
                    @Override
                    public void onResponse(ResponseCategories response) {
                        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        binding.recyclerView.setAdapter(new CategoryAdapter(HomeActivity.this, response.data));
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });

                //Za ovo sam se najvise namucio :D
                binding.drawerLayout.openDrawer(Gravity.RIGHT);

            }
        });
        //Close menu.
        binding.imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.drawerLayout.closeDrawer(Gravity.RIGHT);
            }
        });

        //Vremenska prognoza
        binding.vremenskaPrognoza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Novi activity.
                WeatherActivity.start(HomeActivity.this);
            }
        });
        //Kursna lista
        binding.kursnaLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Novi activity.
                Intent i = new Intent(HomeActivity.this, CourseListActivity.class);
                HomeActivity.this.startActivity(i);
            }
        });
        //Horoskop
        binding.horoskop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Novi activity.
                Intent i = new Intent(HomeActivity.this, HoroscopeActivity.class);
                HomeActivity.this.startActivity(i);
            }
        });
        //Push notifikacije
        binding.pushNotifikacije.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (click) {
                    Toast.makeText(view.getContext().getApplicationContext(), "Push notifikacije su uključene!", Toast.LENGTH_SHORT).show();
                    click = false;
                } else {
                    Toast.makeText(view.getContext().getApplicationContext(), "Push notifikacije su isključene!", Toast.LENGTH_SHORT).show();
                    click = true;
                }

            }
        });
        //Marketing
        binding.marketing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "https://komentar.rs/wp-json/");
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                HomeActivity.this.startActivity(shareIntent);
            }
        });
        //Uslovi koriscenja
        binding.usloviKoriscenja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "https://komentar.rs/wp-json/");
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                HomeActivity.this.startActivity(shareIntent);
            }
        });
        //Kontakt
        binding.kontakt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "https://komentar.rs/wp-json/");
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                HomeActivity.this.startActivity(shareIntent);
            }
        });

        binding.logo.setImageResource(R.drawable.ic_komentar_logo);


        DataRepository.getInstance().loadCategoriesData(new DataRepository.CategoriesResponseListener() {
            @Override
            public void onResponse(ResponseCategories response) {
                binding.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                binding.recyclerView.setAdapter(new CategoryAdapter(HomeActivity.this, response.data));
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

}