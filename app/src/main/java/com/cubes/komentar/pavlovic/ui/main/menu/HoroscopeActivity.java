package com.cubes.komentar.pavlovic.ui.main.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import androidx.appcompat.app.AppCompatActivity;

import com.cubes.komentar.databinding.ActivityHoroscopeBinding;
import com.cubes.komentar.pavlovic.data.domain.Horoscope;
import com.cubes.komentar.pavlovic.data.source.repository.DataRepository;
import com.cubes.komentar.pavlovic.di.AppContainer;
import com.cubes.komentar.pavlovic.di.MyApplication;
import com.squareup.picasso.Picasso;

public class HoroscopeActivity extends AppCompatActivity {

    public static void start(Activity activity) {
        Intent i = new Intent(activity, HoroscopeActivity.class);
        activity.startActivity(i);
    }

    private DataRepository dataRepository;
    private ActivityHoroscopeBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHoroscopeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        AppContainer appContainer = ((MyApplication) getApplication()).appContainer;
        dataRepository = appContainer.dataRepository;

        binding.imageClose.setOnClickListener(view1 -> finish());

        loadHoroscopeData();
        refresh();
    }

    private void loadHoroscopeData() {

        dataRepository.loadHoroscopeData(new DataRepository.HoroscopeResponseListener() {
            @Override
            public void onResponse(Horoscope horoscope) {

                binding.title.setText(horoscope.name);
                binding.date.setText(horoscope.date);
                binding.horoscope.setText(horoscope.horoscope);
                Picasso.get().load(horoscope.imageUrl).into(binding.image);

                binding.refresh.setVisibility(View.GONE);
                binding.rootLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Throwable t) {
                binding.rootLayout.setVisibility(View.GONE);
                binding.refresh.setVisibility(View.VISIBLE);
            }
        });

    }

    private void refresh() {

        binding.refresh.setOnClickListener(view -> {
            RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotate.setDuration(300);
            binding.refresh.startAnimation(rotate);
            loadHoroscopeData();
        });
    }
}