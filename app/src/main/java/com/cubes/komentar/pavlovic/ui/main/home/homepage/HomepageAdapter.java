package com.cubes.komentar.pavlovic.ui.main.home.homepage;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.cubes.komentar.databinding.RvItemButtonsHomepageBinding;
import com.cubes.komentar.databinding.RvItemHorizontalRv2Binding;
import com.cubes.komentar.databinding.RvItemHorizontalRvBinding;
import com.cubes.komentar.databinding.RvItemHorizontalTextViewBinding;
import com.cubes.komentar.databinding.RvItemRvBinding;
import com.cubes.komentar.databinding.RvItemSmallBinding;
import com.cubes.komentar.databinding.RvRecyclerviewVideoBinding;
import com.cubes.komentar.pavlovic.data.model.News;
import com.cubes.komentar.pavlovic.data.response.ResponseHomepage;
import com.cubes.komentar.pavlovic.ui.main.home.homepage.rvitems.RecyclerViewItemHomepage;
import com.cubes.komentar.pavlovic.ui.main.home.homepage.rvitems.RvItemButtonsNews;
import com.cubes.komentar.pavlovic.ui.main.home.homepage.rvitems.RvItemEditorChoise;
import com.cubes.komentar.pavlovic.ui.main.home.homepage.rvitems.RvItemSlider;
import com.cubes.komentar.pavlovic.ui.main.home.homepage.rvitems.RvItemSportBox;
import com.cubes.komentar.pavlovic.ui.main.home.homepage.rvitems.RvItemTitle;
import com.cubes.komentar.pavlovic.ui.main.home.homepage.rvitems.RvItemTopNews;
import com.cubes.komentar.pavlovic.ui.main.home.homepage.rvitems.RvItemVideo;

import java.util.ArrayList;

public class HomepageAdapter extends RecyclerView.Adapter<HomepageAdapter.HomepageViewHolder> {

    private ArrayList<RecyclerViewItemHomepage> items = new ArrayList<>();


    public HomepageAdapter() {
    }

    @NonNull
    @Override
    public HomepageAdapter.HomepageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ViewBinding binding;

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case 0:
                binding = RvItemHorizontalRvBinding.inflate(inflater, parent, false);
                break;
            case 1:
                binding = RvItemSmallBinding.inflate(inflater, parent, false);
                break;
            case 2:
                binding = RvItemButtonsHomepageBinding.inflate(inflater, parent, false);
                break;
            case 4:
                binding = RvItemHorizontalRv2Binding.inflate(inflater, parent, false);
                break;
            case 6:
                binding = RvRecyclerviewVideoBinding.inflate(inflater, parent, false);
                break;
            case 8:
                binding = RvItemRvBinding.inflate(inflater, parent, false);
                break;

            default:
                binding = RvItemHorizontalTextViewBinding.inflate(inflater, parent, false);
        }


        return new HomepageViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HomepageAdapter.HomepageViewHolder holder, int position) {

        this.items.get(position).bind(holder);

    }

    @Override
    public int getItemViewType(int position) {

        return this.items.get(position).getType();
    }

    @Override
    public int getItemCount() {

        return this.items.size();
    }

    public void setDataItems(ResponseHomepage.ResponseHomepageData response) {
        //0
        items.add(new RvItemSlider(response.slider));
        //1
        for (int i = 0; i < response.top.size(); i++) {
            News news = response.top.get(i);
            items.add(new RvItemTopNews(news));
        }
        //2
        items.add(new RvItemButtonsNews(response.latest, response.most_comented, response.most_read));
        //3-4
        if (response.editors_choice.size() > 0) {
            items.add(new RvItemTitle("Izbor urednika", "#FF0000"));
            items.add(new RvItemEditorChoise(response.editors_choice));
        }
        //5-6
        if (response.videos.size() > 0) {
            items.add(new RvItemTitle("Video", "#FF0000"));
            items.add(new RvItemVideo(response.videos));
        }
        //7-8
        for (ResponseHomepage.ResponseHomePageDataCategoryBox categoryBox : response.category) {
            items.add(new RvItemTitle(categoryBox.title, categoryBox.color));
            items.add(new RvItemSportBox(categoryBox.title, categoryBox.news));
        }
        notifyDataSetChanged();
    }

    public class HomepageViewHolder extends RecyclerView.ViewHolder {

        public ViewBinding binding;

        public HomepageViewHolder(ViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
