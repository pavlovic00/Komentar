package com.cubes.komentar.pavlovic.ui.main.home.homepage.rvitems;

import android.graphics.Color;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cubes.komentar.databinding.RvItemButtonsHomepageBinding;
import com.cubes.komentar.pavlovic.data.model.News;
import com.cubes.komentar.pavlovic.ui.main.home.homepage.ButtonsAdapter;
import com.cubes.komentar.pavlovic.ui.main.home.homepage.HomepageAdapter;

import java.util.ArrayList;

public class RvItemButtonsNews implements RecyclerViewItemHomepage{

    private ArrayList<News> latest;
    private ArrayList<News> most_comented;
    private ArrayList<News> most_read;

    public RvItemButtonsNews(ArrayList<News> latest, ArrayList<News> most_comented, ArrayList<News> most_read) {
        this.latest = latest;
        this.most_comented = most_comented;
        this.most_read = most_read;
    }

    @Override
    public int getType() {
        return 2;
    }

    @Override
    public void bind(HomepageAdapter.HomepageViewHolder holder) {

        RvItemButtonsHomepageBinding binding = (RvItemButtonsHomepageBinding) holder.binding;

        //Default na najnovije.
        binding.recyclerViewButtons.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext(),
                RecyclerView.VERTICAL,false));
        binding.recyclerViewButtons.setAdapter(new ButtonsAdapter(latest));

        binding.buttonKomentari.setTextColor(Color.parseColor("#88909D"));//Gray
        binding.buttonNajnovije.setTextColor(Color.parseColor("#FF000000"));//Black
        binding.buttonNajcitanije.setTextColor(Color.parseColor("#88909D"));//Gray

        //Klik na najnovije.
        binding.buttonNajnovije.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.recyclerViewButtons.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext(),
                        RecyclerView.VERTICAL,false));
                binding.recyclerViewButtons.setAdapter(new ButtonsAdapter(latest));

                binding.buttonKomentari.setTextColor(Color.parseColor("#88909D"));//Gray
                binding.buttonNajnovije.setTextColor(Color.parseColor("#FF000000"));//Black
                binding.buttonNajcitanije.setTextColor(Color.parseColor("#88909D"));//Gray

                binding.view3.setBackgroundColor(Color.parseColor("#88909D"));//Gray
                binding.view2.setBackgroundColor(Color.parseColor("#88909D"));//Gray
                binding.view1.setBackgroundColor(Color.parseColor("#FF0000"));//Red
            }
        });

        //Klik na najcitanije.
        binding.buttonNajcitanije.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.recyclerViewButtons.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext(),
                        RecyclerView.VERTICAL,false));
                binding.recyclerViewButtons.setAdapter(new ButtonsAdapter(most_read));

                binding.buttonKomentari.setTextColor(Color.parseColor("#88909D"));//Gray
                binding.buttonNajnovije.setTextColor(Color.parseColor("#88909D"));//Gray
                binding.buttonNajcitanije.setTextColor(Color.parseColor("#FF000000"));//Black

                binding.view3.setBackgroundColor(Color.parseColor("#88909D"));//Gray
                binding.view2.setBackgroundColor(Color.parseColor("#FF0000"));//Red
                binding.view1.setBackgroundColor(Color.parseColor("#88909D"));//Gray
            }
        });

        //Klik na najkomentarisanije.
        binding.buttonKomentari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.recyclerViewButtons.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext(),
                        RecyclerView.VERTICAL,false));
                binding.recyclerViewButtons.setAdapter(new ButtonsAdapter(most_comented));

                binding.buttonKomentari.setTextColor(Color.parseColor("#FF000000"));//Black
                binding.buttonNajnovije.setTextColor(Color.parseColor("#88909D"));//Gray
                binding.buttonNajcitanije.setTextColor(Color.parseColor("#88909D"));//Gray

                binding.view3.setBackgroundColor(Color.parseColor("#FF0000"));//Red
                binding.view2.setBackgroundColor(Color.parseColor("#88909D"));//Gray
                binding.view1.setBackgroundColor(Color.parseColor("#88909D"));//Gray
            }
        });
    }
}
