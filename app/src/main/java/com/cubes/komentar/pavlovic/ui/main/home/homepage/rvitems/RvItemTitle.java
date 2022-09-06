package com.cubes.komentar.pavlovic.ui.main.home.homepage.rvitems;

import android.graphics.Color;

import com.cubes.komentar.R;
import com.cubes.komentar.databinding.RvItemHorizontalTextViewBinding;
import com.cubes.komentar.pavlovic.ui.main.home.homepage.HomepageAdapter;

public class RvItemTitle implements RecyclerViewItemHomepage {

    private final String title;
    private final String color;


    public RvItemTitle(String title, String color) {
        this.title = title;
        this.color = color;
    }

    @Override
    public int getType() {
        return 3;
    }

    @Override
    public void bind(HomepageAdapter.ViewHolder holder) {

        RvItemHorizontalTextViewBinding binding = (RvItemHorizontalTextViewBinding) holder.binding;

        if (title.equalsIgnoreCase("Video")) {
            binding.rootLayout.setBackgroundColor(binding.getRoot().getContext().getResources().getColor(R.color.purple_dark));
            binding.textViewTitle.setTextColor(binding.getRoot().getContext().getResources().getColor(R.color.white));
        } else {
            binding.rootLayout.setBackgroundColor(binding.getRoot().getContext().getResources().getColor(R.color.white));
            binding.textViewTitle.setTextColor(binding.getRoot().getContext().getResources().getColor(R.color.black));
        }

        binding.textViewTitle.setText(title);
        binding.viewColor.setBackgroundColor(Color.parseColor(color));
    }
}
