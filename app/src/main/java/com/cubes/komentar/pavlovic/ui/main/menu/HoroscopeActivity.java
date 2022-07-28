package com.cubes.komentar.pavlovic.ui.main.menu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.cubes.komentar.databinding.ActivityHoroscopeBinding;

public class HoroscopeActivity extends AppCompatActivity {

    private ActivityHoroscopeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHoroscopeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}