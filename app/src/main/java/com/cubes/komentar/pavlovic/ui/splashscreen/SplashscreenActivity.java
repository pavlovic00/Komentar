package com.cubes.komentar.pavlovic.ui.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.cubes.komentar.databinding.ActivitySplashscreenBinding;
import com.cubes.komentar.pavlovic.ui.main.menu.HomeActivity;

public class SplashscreenActivity extends AppCompatActivity {

    private ActivitySplashscreenBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySplashscreenBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finish();
            }
        }, 800);
    }
}
