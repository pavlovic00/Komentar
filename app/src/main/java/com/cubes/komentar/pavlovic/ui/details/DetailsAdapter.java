package com.cubes.komentar.pavlovic.ui.details;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.cubes.komentar.R;
import com.cubes.komentar.databinding.RvItemAdsViewBinding;
import com.cubes.komentar.databinding.RvItemButtonAllCommentBinding;
import com.cubes.komentar.databinding.RvItemButtonCommentBinding;
import com.cubes.komentar.databinding.RvItemCommentParentBinding;
import com.cubes.komentar.databinding.RvItemGridRvBinding;
import com.cubes.komentar.databinding.RvItemHorizontalTextViewBinding;
import com.cubes.komentar.databinding.RvItemHorizontalTextViewCommentBinding;
import com.cubes.komentar.databinding.RvItemHorizontalTextViewLongBinding;
import com.cubes.komentar.databinding.RvItemSmallBinding;
import com.cubes.komentar.databinding.RvItemWebviewBinding;
import com.cubes.komentar.pavlovic.data.domain.Comment;
import com.cubes.komentar.pavlovic.data.domain.News;
import com.cubes.komentar.pavlovic.data.domain.NewsDetail;
import com.cubes.komentar.pavlovic.ui.details.rvitems.RecyclerViewItemDetail;
import com.cubes.komentar.pavlovic.ui.details.rvitems.RvItemAdsDetail;
import com.cubes.komentar.pavlovic.ui.details.rvitems.RvItemButtonAllComment;
import com.cubes.komentar.pavlovic.ui.details.rvitems.RvItemButtonPutComment;
import com.cubes.komentar.pavlovic.ui.details.rvitems.RvItemComment;
import com.cubes.komentar.pavlovic.ui.details.rvitems.RvItemRelatedNews;
import com.cubes.komentar.pavlovic.ui.details.rvitems.RvItemTagsDetail;
import com.cubes.komentar.pavlovic.ui.details.rvitems.RvItemTitleComment;
import com.cubes.komentar.pavlovic.ui.details.rvitems.RvItemTitleDetail;
import com.cubes.komentar.pavlovic.ui.details.rvitems.RvItemTitleRelatedNews;
import com.cubes.komentar.pavlovic.ui.details.rvitems.RvItemWebViewDetail;
import com.cubes.komentar.pavlovic.ui.tools.listener.DetailsListener;
import com.cubes.komentar.pavlovic.ui.tools.listener.WebViewListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;

import java.util.ArrayList;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.ViewHolder> {

    private final ArrayList<RecyclerViewItemDetail> items = new ArrayList<>();
    private final DetailsListener detailsListener;


    public DetailsAdapter(DetailsListener detailsListener) {
        this.detailsListener = detailsListener;
    }

    @SuppressLint("NonConstantResourceId")
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ViewBinding binding = null;

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case R.layout.rv_item_webview:
                binding = RvItemWebviewBinding.inflate(inflater, parent, false);
                break;
            case R.layout.rv_item_horizontal_text_view_long:
                binding = RvItemHorizontalTextViewLongBinding.inflate(inflater, parent, false);
                break;
            case R.layout.rv_item_grid_rv:
                binding = RvItemGridRvBinding.inflate(inflater, parent, false);
                break;
            case R.layout.rv_item_horizontal_text_view_comment:
                binding = RvItemHorizontalTextViewCommentBinding.inflate(inflater, parent, false);
                break;
            case R.layout.rv_item_button_comment:
                binding = RvItemButtonCommentBinding.inflate(inflater, parent, false);
                break;
            case R.layout.rv_item_comment_parent:
                binding = RvItemCommentParentBinding.inflate(inflater, parent, false);
                break;
            case R.layout.rv_item_button_all_comment:
                binding = RvItemButtonAllCommentBinding.inflate(inflater, parent, false);
                break;
            case R.layout.rv_item_horizontal_text_view:
                binding = RvItemHorizontalTextViewBinding.inflate(inflater, parent, false);
                break;
            case R.layout.rv_item_small:
                binding = RvItemSmallBinding.inflate(inflater, parent, false);
                break;
            case R.layout.rv_item_ads_view:
                binding = RvItemAdsViewBinding.inflate(inflater, parent, false);
                ((RvItemAdsViewBinding) binding).adsView.setVisibility(View.GONE);
                ((RvItemAdsViewBinding) binding).shimmerLayout.setVisibility(View.VISIBLE);
                ((RvItemAdsViewBinding) binding).shimmerLayout.startShimmerAnimation();

                AdRequest adRequest = new AdRequest.Builder().build();
                ((RvItemAdsViewBinding) binding).adsView.loadAd(adRequest);

                ViewBinding finalBinding = binding;
                ((RvItemAdsViewBinding) binding).adsView.setAdListener(new AdListener() {
                    @Override
                    public void onAdLoaded() {
                        super.onAdLoaded();
                        ((RvItemAdsViewBinding) finalBinding).shimmerLayout.stopShimmerAnimation();
                        ((RvItemAdsViewBinding) finalBinding).shimmerLayout.setVisibility(View.GONE);
                        ((RvItemAdsViewBinding) finalBinding).adsView.setVisibility(View.VISIBLE);
                    }
                });
                break;
        }

        assert binding != null;
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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

    public void setDataItems(NewsDetail response, WebViewListener webViewListener) {
        //Reklama
        this.items.add(new RvItemAdsDetail());
        //0
        this.items.add(new RvItemWebViewDetail(response, webViewListener));
        //1-2
        //Reklama
        this.items.add(new RvItemAdsDetail());
        if (response.tags.size() > 0) {
            this.items.add(new RvItemTitleDetail("Tagovi:"));
            this.items.add(new RvItemTagsDetail(response.tags, detailsListener));
        }
        //3
        this.items.add(new RvItemButtonPutComment(response, detailsListener));
        //4
        this.items.add(new RvItemTitleComment("Komentari", response));
        //5
        for (int i = 0; i < response.topComments.size(); i++) {
            Comment commentData = response.topComments.get(i);
            items.add(new RvItemComment(commentData, detailsListener));
        }
        //6
        this.items.add(new RvItemButtonAllComment(response, detailsListener));
        //7-8
        if (response.relatedNews.size() > 0) {
            //Reklama
            this.items.add(new RvItemAdsDetail());
            this.items.add(new RvItemTitleRelatedNews("Povezane vesti"));

            for (int i = 0; i < response.relatedNews.size(); i++) {
                News news = response.relatedNews.get(i);
                this.items.add(new RvItemRelatedNews(news, detailsListener, response.relatedNews));
            }
        }

        notifyDataSetChanged();
    }

    public void updateCommentsList(ArrayList<Comment> comments) {
        for (Comment comment : comments) {
            items.add(new RvItemComment(comment, detailsListener));
        }

        notifyDataSetChanged();
    }

    public void setupLike(String commentId) {
        for (RecyclerViewItemDetail comment : items) {
            if (comment.getCommentsId().equals(commentId)) {
                comment.updateLike();
                break;
            }
        }
    }

    public void setupDislike(String commentId) {
        for (RecyclerViewItemDetail comment : items) {
            if (comment.getCommentsId().equals(commentId)) {
                comment.updateDislike();
                break;
            }
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewBinding binding;

        public ViewHolder(ViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

