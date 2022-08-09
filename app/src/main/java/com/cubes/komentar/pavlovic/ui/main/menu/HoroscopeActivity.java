package com.cubes.komentar.pavlovic.ui.main.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.cubes.komentar.databinding.ActivityHoroscopeBinding;

public class HoroscopeActivity extends AppCompatActivity {

    public static void start(Activity activity) {
        Intent i = new Intent(activity, HoroscopeActivity.class);
        activity.startActivity(i);
    }

    private ActivityHoroscopeBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHoroscopeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.imageClose.setOnClickListener(view1 -> finish());
    }
}