package com.cubes.komentar.pavlovic.ui.main.home.homepage.rvitems;

import android.graphics.Color;

import com.cubes.komentar.R;
import com.cubes.komentar.databinding.RvItemVideoBinding;
import com.cubes.komentar.pavlovic.data.domain.News;
import com.cubes.komentar.pavlovic.ui.main.home.homepage.HomepageAdapter;
import com.cubes.komentar.pavlovic.ui.tools.VideoListener;
import com.squareup.picasso.Picasso;

public class RvItemVideo implements RecyclerViewItemHomepage {

    private final News video;
    private final VideoListener videoListener;


    public RvItemVideo(News video, VideoListener videoListener) {
        this.video = video;
        this.videoListener = videoListener;
    }

    @Override
    public int getType() {
        return R.layout.rv_item_video;
    }

    @Override
    public void bind(HomepageAdapter.ViewHolder holder) {

        RvItemVideoBinding bindingVideo = (RvItemVideoBinding) holder.binding;

        bindingVideo.textViewTitle.setText(video.title);
        bindingVideo.date.setText(video.created_at);
        bindingVideo.textViewCategory.setText(video.category.name);
        bindingVideo.textViewCategory.setTextColor(Color.parseColor(video.category.color));
        Picasso.get().load(video.image).into(bindingVideo.imageView);

        bindingVideo.imageViewPlay.setOnClickListener(view -> videoListener.onVideoClicked(video));
    }
}
