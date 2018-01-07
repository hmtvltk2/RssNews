package com.example.android.persistence.adapter;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;

import com.example.android.persistence.BasicApp;
import com.example.android.persistence.R;
import com.example.android.persistence.databinding.NewsItemBinding;
import com.example.android.persistence.db.entity.RssItemEntity;
import com.example.android.persistence.model.RssItem;
import com.example.android.persistence.ui.RssItemClickCallback;

import java.util.List;
import java.util.Objects;

/**
 * Author: HMT
 * Date: 30/12/2017.
 */

public class RssListAdapter extends RecyclerView.Adapter<RssListAdapter.RssListViewHolder> {
    List<? extends RssItem> mRssList;
    private BasicApp mApplication;
    @Nullable
    private final RssItemClickCallback mRssItemClickCallback;
    private final Context context;

    public RssListAdapter(@Nullable RssItemClickCallback mRssItemClickCallback, Application application, Context context) {
        this.mRssItemClickCallback = mRssItemClickCallback;
        this.mApplication = (BasicApp)application;
        this.context = context;
    }

    public void setRssList(final List<? extends RssItem> rssList){
        if(mRssList == null){
            mRssList = rssList;
            notifyItemRangeInserted(0, rssList.size());
        }
        else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mRssList.size();
                }

                @Override
                public int getNewListSize() {
                    return rssList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return Objects.equals(mRssList.get(oldItemPosition).getId(),rssList.get(newItemPosition).getId());
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    return Objects.equals(mRssList.get(oldItemPosition).getId(),rssList.get(newItemPosition).getId())
                            && mRssList.get(oldItemPosition).getBookmark() == rssList.get(newItemPosition).getBookmark();

                }
            });

            mRssList = rssList;
            result.dispatchUpdatesTo(this);
        }
        Log.d("RssListAdapter", "Current RssList: "+ mRssList.size());
    }


    @Override
    public RssListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        NewsItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.news_item, parent, false);
        binding.setCallback(mRssItemClickCallback);
        return new RssListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RssListViewHolder holder, int position) {
        holder.binding.setRssItem(mRssList.get(position));
        int id = mRssList.get(position).getRssSourceId();
        mApplication.getRepository().loadRssSource(id).observeForever(holder.binding::setRssSource);

        holder.binding.setHandler(new RssHandler(mRssList.get(position).getBookmark() == 1, holder.binding));

        if(mRssList.get(position).getBookmark() == 1){
            holder.binding.imgBookmark.setImageResource(R.drawable.ic_bookmark_black_24dp);
        }
        else {
            holder.binding.imgBookmark.setImageResource(R.drawable.ic_bookmark_border_black_24dp);
        }
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mRssList == null ? 0: mRssList.size();
    }

    static class RssListViewHolder extends RecyclerView.ViewHolder{
        final NewsItemBinding binding;

        public RssListViewHolder(NewsItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }

    public class RssHandler{
        private final AlphaAnimation alphaAnimationShowIcon;
        private boolean isBookmark;
        private NewsItemBinding mBinding;
        public RssHandler(boolean isBookmark, NewsItemBinding binding){
            this.isBookmark = isBookmark;
            this.mBinding = binding;
            alphaAnimationShowIcon = new AlphaAnimation(0.2f, 1.0f);
            alphaAnimationShowIcon.setDuration(500);
        }
        public void onBookmarkClick(RssItem rssItem){
            RssItemEntity rssItemEntity = new RssItemEntity(rssItem);
            if (!isBookmark) {
                mBinding.imgBookmark.setImageResource(R.drawable.ic_bookmark_black_24dp);
                isBookmark = true;
                rssItemEntity.setBookmark(1);
                Log.d("RssHandler", "Bookmark is clicked");
            } else {
                mBinding.imgBookmark.setImageResource(R.drawable.ic_bookmark_border_black_24dp);
                isBookmark = false;
                rssItemEntity.setBookmark(0);
                Log.d("RssHandler", "Bookmark is unclicked");
            }
            mBinding.imgBookmark.startAnimation(alphaAnimationShowIcon);
            mBinding.executePendingBindings();
            mApplication.getRepository().updateRssItem(rssItemEntity);
        }

        public void onShareClick(RssItem rssItem){
            Intent intent = new Intent(android.content.Intent.ACTION_SEND);
            intent.setType("text/plain");

            intent.putExtra(android.content.Intent.EXTRA_TEXT, rssItem.getLink());
            context.startActivity(Intent.createChooser(intent, "Choose sharing method"));
        }
    }
}
