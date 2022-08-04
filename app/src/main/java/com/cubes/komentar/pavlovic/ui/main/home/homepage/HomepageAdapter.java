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


    public interface Listener {
        void onNewsItemClick(News news);
    }

    private Listener listener;


    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public ResponseHomepage.ResponseHomepageData data;
    public ArrayList<ResponseHomepage.ResponseHomePageDataCategoryBox> dataBox;
    private ArrayList<RecyclerViewItemHomepage> items;
    private ArrayList<News> news;

    public HomepageAdapter(ResponseHomepage.ResponseHomepageData data) {
        this.data = data;
        this.items = new ArrayList<>();
        this.dataBox = data.category;

        //0
        this.items.add(new RvItemSlider(data.slider));
        //1
        for (int i = 0; i < data.top.size(); i++) {
            News news = data.top.get(i);

            this.items.add(new RvItemTopNews(news));
        }
        //2
        this.items.add(new RvItemButtonsNews(data.latest, data.most_comented, data.most_read));
        //3-4
        if (data.editors_choice.size() > 0) {
            this.items.add(new RvItemTitle("Izbor urednika", "#FF0000"));
            this.items.add(new RvItemEditorChoise(data.editors_choice));
        }
        //5-6
        if (data.videos.size() > 0) {
            this.items.add(new RvItemTitle("Video", "#FF0000"));
            this.items.add(new RvItemVideo(data.videos));
        }
        //7-8
        for (ResponseHomepage.ResponseHomePageDataCategoryBox categoryBox : dataBox) {
            this.items.add(new RvItemTitle(categoryBox.title, categoryBox.color));
            this.items.add(new RvItemSportBox(categoryBox.title, categoryBox.news));
        }

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

    public class HomepageViewHolder extends RecyclerView.ViewHolder {

        public ViewBinding binding;

        public HomepageViewHolder(ViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
