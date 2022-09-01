package com.cubes.komentar.pavlovic.ui.splashscreen;

import android.content.Intent;
import android.net.Uri;
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

        if (getIntent().getExtras() == null || getIntent().getExtras().getString("url") == null) {
            new Handler().postDelayed(() -> {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finish();
            }, 800);
        } else {
            Bundle bundle = getIntent().getExtras();
            String url = bundle.getString("url");

            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
            finish();
        }
    }
}
