package com.cubes.komentar.pavlovic.ui.main.home.homepage.rvitems;

import android.graphics.Color;

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
            binding.rootLayout.setBackgroundColor(Color.parseColor("#0F2039"));
            binding.textViewTitle.setTextColor(Color.parseColor("#FFFFFFFF"));
        } else {
            binding.rootLayout.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
            binding.textViewTitle.setTextColor(Color.parseColor("#000000"));
        }

        binding.textViewTitle.setText(title);
        binding.viewColor.setBackgroundColor(Color.parseColor(color));
    }
}
