package com.cubes.komentar.pavlovic.ui.main.home.homepage.rvitems;

import android.graphics.Color;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cubes.komentar.R;
import com.cubes.komentar.databinding.RvItemButtonsHomepageBinding;
import com.cubes.komentar.pavlovic.data.domain.News;
import com.cubes.komentar.pavlovic.ui.main.home.homepage.ButtonsAdapter;
import com.cubes.komentar.pavlovic.ui.main.home.homepage.HomepageAdapter;
import com.cubes.komentar.pavlovic.ui.tools.listener.NewsListener;

import java.util.ArrayList;

public class RvItemButtonsNews implements RecyclerViewItemHomepage {

    private final ArrayList<News> latest;
    private final ArrayList<News> most_commented;
    private final ArrayList<News> most_read;
    private final NewsListener newsListener;


    public RvItemButtonsNews(ArrayList<News> latest, ArrayList<News> most_commented, ArrayList<News> most_read, NewsListener newsListener) {
        this.latest = latest;
        this.most_commented = most_commented;
        this.most_read = most_read;
        this.newsListener = newsListener;
    }

    @Override
    public int getType() {
        return R.layout.rv_item_buttons_homepage;
    }

    @Override
    public void bind(HomepageAdapter.ViewHolder holder) {

        RvItemButtonsHomepageBinding binding = (RvItemButtonsHomepageBinding) holder.binding;

        //Default na najnovije.
        binding.recyclerViewButtons.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext(),
                RecyclerView.VERTICAL, false));
        binding.recyclerViewButtons.setAdapter(new ButtonsAdapter(latest, newsListener));

        binding.buttonKomentari.setTextColor(Color.parseColor("#88909D"));//Gray
        binding.buttonNajnovije.setTextColor(Color.parseColor("#FF000000"));//Black
        binding.buttonNajcitanije.setTextColor(Color.parseColor("#88909D"));//Gray

        //Klik na najnovije.
        binding.buttonNajnovije.setOnClickListener(view -> {
            binding.recyclerViewButtons.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext(),
                    RecyclerView.VERTICAL, false));
            binding.recyclerViewButtons.setAdapter(new ButtonsAdapter(latest, newsListener));

            binding.buttonKomentari.setTextColor(Color.parseColor("#88909D"));//Gray
            binding.buttonNajnovije.setTextColor(Color.parseColor("#FF000000"));//Black
            binding.buttonNajcitanije.setTextColor(Color.parseColor("#88909D"));//Gray

            binding.view3.setBackgroundColor(Color.parseColor("#88909D"));//Gray
            binding.view2.setBackgroundColor(Color.parseColor("#88909D"));//Gray
            binding.view1.setBackgroundColor(Color.parseColor("#FF0000"));//Red
        });

        //Klik na najcitanije.
        binding.buttonNajcitanije.setOnClickListener(view -> {
            binding.recyclerViewButtons.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext(),
                    RecyclerView.VERTICAL, false));
            binding.recyclerViewButtons.setAdapter(new ButtonsAdapter(most_read, newsListener));

            binding.buttonKomentari.setTextColor(Color.parseColor("#88909D"));//Gray
            binding.buttonNajnovije.setTextColor(Color.parseColor("#88909D"));//Gray
            binding.buttonNajcitanije.setTextColor(Color.parseColor("#FF000000"));//Black

            binding.view3.setBackgroundColor(Color.parseColor("#88909D"));//Gray
            binding.view2.setBackgroundColor(Color.parseColor("#FF0000"));//Red
            binding.view1.setBackgroundColor(Color.parseColor("#88909D"));//Gray
        });

        //Klik na najkomentarisanije.
        binding.buttonKomentari.setOnClickListener(view -> {
            binding.recyclerViewButtons.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext(),
                    RecyclerView.VERTICAL, false));
            binding.recyclerViewButtons.setAdapter(new ButtonsAdapter(most_commented, newsListener));

            binding.buttonKomentari.setTextColor(Color.parseColor("#FF000000"));//Black
            binding.buttonNajnovije.setTextColor(Color.parseColor("#88909D"));//Gray
            binding.buttonNajcitanije.setTextColor(Color.parseColor("#88909D"));//Gray

            binding.view3.setBackgroundColor(Color.parseColor("#FF0000"));//Red
            binding.view2.setBackgroundColor(Color.parseColor("#88909D"));//Gray
            binding.view1.setBackgroundColor(Color.parseColor("#88909D"));//Gray
        });
    }
}
