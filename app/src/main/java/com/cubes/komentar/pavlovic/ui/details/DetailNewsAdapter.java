package com.cubes.komentar.pavlovic.ui.details;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.cubes.komentar.databinding.RvItemButtonAllCommentBinding;
import com.cubes.komentar.databinding.RvItemButtonCommentBinding;
import com.cubes.komentar.databinding.RvItemCommentBinding;
import com.cubes.komentar.databinding.RvItemFirstItemDetailBinding;
import com.cubes.komentar.databinding.RvItemGridRvBinding;
import com.cubes.komentar.databinding.RvItemHorizontalTextViewBinding;
import com.cubes.komentar.databinding.RvItemHorizontalTextViewCommentBinding;
import com.cubes.komentar.databinding.RvItemHorizontalTextViewLongBinding;
import com.cubes.komentar.databinding.RvItemSmallBinding;
import com.cubes.komentar.databinding.RvItemWebviewBinding;
import com.cubes.komentar.pavlovic.data.DataContainer;
import com.cubes.komentar.pavlovic.data.model.News;
import com.cubes.komentar.pavlovic.data.response.responsecomment.ResponseCommentData;
import com.cubes.komentar.pavlovic.data.response.responsedetail.ResponseDetailData;
import com.cubes.komentar.pavlovic.ui.details.rvitems.RecyclerViewItemDetail;
import com.cubes.komentar.pavlovic.ui.details.rvitems.RvItemButtonAllComment;
import com.cubes.komentar.pavlovic.ui.details.rvitems.RvItemButtonPutComment;
import com.cubes.komentar.pavlovic.ui.details.rvitems.RvItemComment;
import com.cubes.komentar.pavlovic.ui.details.rvitems.RvItemFirstItemDetail;
import com.cubes.komentar.pavlovic.ui.details.rvitems.RvItemRelatedNews;
import com.cubes.komentar.pavlovic.ui.details.rvitems.RvItemTagsDetail;
import com.cubes.komentar.pavlovic.ui.details.rvitems.RvItemTitleComment;
import com.cubes.komentar.pavlovic.ui.details.rvitems.RvItemTitleDetail;
import com.cubes.komentar.pavlovic.ui.details.rvitems.RvItemTitleRelatedNews;
import com.cubes.komentar.pavlovic.ui.details.rvitems.RvItemWebviewDetail;

import java.util.ArrayList;

public class DetailNewsAdapter extends RecyclerView.Adapter<DetailNewsAdapter.DetailNewsViewHolder> {

    public ResponseDetailData data;
    public News news;
    public Context context;
    private ArrayList<RecyclerViewItemDetail> items;

    public DetailNewsAdapter(ResponseDetailData data, ArrayList<ResponseCommentData> commentList,Context context) {
        this.data = data;
        this.context = context;

        this.items = new ArrayList<>();
        //0
        this.items.add(new RvItemFirstItemDetail(data));
        //1
        this.items.add(new RvItemWebviewDetail(data));
        //2-3
        if (data.tags.size()>0) {
            this.items.add(new RvItemTitleDetail("Tagovi:"));
            this.items.add(new RvItemTagsDetail(data.tags,context));
        }
        //4-5-6-7
        this.items.add(new RvItemButtonPutComment());

        if (data.comments_count>0){
            this.items.add(new RvItemTitleComment("Komentari",data));

            for (int i=0; i< DataContainer.commentList.size(); i++){
                ResponseCommentData commentData = DataContainer.commentList.get(i);
                this.items.add(new RvItemComment(commentData));
            }

            this.items.add(new RvItemButtonAllComment(data));
        }

        //8-9
        if (data.related_news.size()>0){
            this.items.add(new RvItemTitleRelatedNews("Povezane vesti"));

                for (int i=0;i<data.related_news.size();i++){
                    News news = data.related_news.get(i);

                    this.items.add(new RvItemRelatedNews(news));
                }
        }
    }

    @NonNull
    @Override
    public DetailNewsAdapter.DetailNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ViewBinding binding = null;

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType){
            case 0:
            binding = RvItemFirstItemDetailBinding.inflate(inflater,parent,false);
                break;
            case 1:
            binding = RvItemWebviewBinding.inflate(inflater,parent,false);
                break;
            case 2:
            binding = RvItemHorizontalTextViewLongBinding.inflate(inflater,parent,false);
                break;
            case 3:
            binding = RvItemGridRvBinding.inflate(inflater,parent,false);
                break;
            case 4:
                binding = RvItemHorizontalTextViewCommentBinding.inflate(inflater,parent,false);
                break;
            case 5:
                binding = RvItemButtonCommentBinding.inflate(inflater,parent,false);
                break;
            case 6:
                binding = RvItemCommentBinding.inflate(inflater,parent,false);
                break;
            case 7:
                binding = RvItemButtonAllCommentBinding.inflate(inflater,parent,false);
                break;
            case 8:
            binding = RvItemHorizontalTextViewBinding.inflate(inflater,parent,false);
                break;
            case 9:
            binding = RvItemSmallBinding.inflate(inflater,parent,false);
                break;
        }

        return new DetailNewsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailNewsAdapter.DetailNewsViewHolder holder, int position) {

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


    public class DetailNewsViewHolder extends RecyclerView.ViewHolder{

        public ViewBinding binding;

        public DetailNewsViewHolder(ViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

