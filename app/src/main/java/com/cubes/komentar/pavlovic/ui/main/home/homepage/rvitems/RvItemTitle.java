package com.cubes.komentar.pavlovic.ui.main.home.homepage.rvitems;

import android.graphics.Color;

import com.cubes.komentar.databinding.RvItemHorizontalTextViewBinding;
import com.cubes.komentar.pavlovic.ui.main.home.homepage.HomepageAdapter;

public class RvItemTitle implements RecyclerViewItemHomepage{

    private String title;
    private String color;

    public RvItemTitle(String title,String color) {
        this.title = title;
        this.color = color;
    }

    @Override
    public int getType() {
        return 3;
    }

    @Override
    public void bind(HomepageAdapter.HomepageViewHolder holder) {

        RvItemHorizontalTextViewBinding binding = (RvItemHorizontalTextViewBinding) holder.binding;

        binding.textViewTitle.setText(title);
        binding.viewColor.setBackgroundColor(Color.parseColor(color));

//        if (title.equalsIgnoreCase("Video")){
//            binding.textViewTitle.setText(title);
//            binding.rootLayout.setBackgroundColor(Color.parseColor("#0F2039"));
//            binding.textViewTitle.setTextColor(Color.parseColor("#FFFFFFFF"));
//            binding.viewColor.setBackgroundColor(Color.parseColor(color));
//        }
    }
}
