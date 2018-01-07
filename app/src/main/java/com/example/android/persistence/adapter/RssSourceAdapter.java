package com.example.android.persistence.adapter;

import android.app.Application;
import android.databinding.DataBindingUtil;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;

import com.example.android.persistence.BasicApp;
import com.example.android.persistence.R;
import com.example.android.persistence.databinding.RssSourceItemBinding;
import com.example.android.persistence.db.entity.RssSourceEntity;
import com.example.android.persistence.model.RssSource;
import com.example.android.persistence.ui.RssSourceClickCallback;

import java.util.List;
import java.util.Objects;

public class RssSourceAdapter extends RecyclerView.Adapter<RssSourceAdapter.RssSourceViewHolder> {
    private static final String TAG = "RssSourceAdapter";
    private List<? extends RssSource> mRssSourceList;
    private BasicApp basicApp;


    public RssSourceAdapter(Application application){
        basicApp = (BasicApp) application;
    }

    public void setRssSourceList(final List<? extends RssSource> rssSources){
        if (mRssSourceList == null) {
            mRssSourceList = rssSources;
            notifyItemRangeInserted(0, rssSources.size());
        } else {
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mRssSourceList.size();
                }

                @Override
                public int getNewListSize() {
                    return rssSources.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    RssSource oldRssSource = mRssSourceList.get(oldItemPosition);
                    RssSource newRssSource = rssSources.get(newItemPosition);
                    return oldRssSource.getId() == newRssSource.getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    RssSource oldRssSource = mRssSourceList.get(oldItemPosition);
                    RssSource newRssSource = rssSources.get(newItemPosition);
                    return oldRssSource.getId() == newRssSource.getId()
                            && Objects.equals(oldRssSource.getName(), newRssSource.getName())
                            && oldRssSource.getInLibrary() == newRssSource.getInLibrary()
                            && Objects.equals(oldRssSource.getTopicId(), newRssSource.getTopicId())
                            && Objects.equals(oldRssSource.getImageUrl(), newRssSource.getImageUrl());

                }
            });
            mRssSourceList = rssSources;
            diffResult.dispatchUpdatesTo(this);
        }
    }

    @Override
    public RssSourceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RssSourceItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.rss_source_item, parent, false);
        return new RssSourceViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RssSourceViewHolder holder, int position) {
        RssSource rssSource = mRssSourceList.get(position);
        holder.binding.setRssSource(rssSource);

        int resourceId = rssSource.getInLibrary() == 1? R.drawable.minus_icon: R.drawable.plus_icon;
        holder.binding.addRssSource.setImageResource(resourceId);

        holder.binding.setCallback(new RssSourceHandler(rssSource.getInLibrary(), holder.binding));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mRssSourceList == null? 0: mRssSourceList.size();
    }

    class RssSourceViewHolder extends RecyclerView.ViewHolder {
        final RssSourceItemBinding binding;
        RssSourceViewHolder(RssSourceItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public class RssSourceHandler implements RssSourceClickCallback{
        private final AlphaAnimation alphaAnimationShowIcon;
        private boolean isAdd;
        private RssSourceItemBinding mBinding;

        public RssSourceHandler(int inLibrary, RssSourceItemBinding binding){
            this.isAdd = inLibrary == 1;
            this.mBinding = binding;
            alphaAnimationShowIcon = new AlphaAnimation(0.2f, 1.0f);
            alphaAnimationShowIcon.setDuration(500);
        }

        @Override
        public void onClick(RssSource rssSource) {
            RssSourceEntity rssSourceEntity = new RssSourceEntity(rssSource);
            if(isAdd){
                mBinding.addRssSource.setImageResource(R.drawable.plus_icon);
                isAdd = false;
                rssSourceEntity.setInLibrary(0);
            }
            else {
                mBinding.addRssSource.setImageResource(R.drawable.minus_icon);
                isAdd = true;
                rssSourceEntity.setInLibrary(1);
            }

            mBinding.addRssSource.setAnimation(alphaAnimationShowIcon);
            mBinding.executePendingBindings();
            basicApp.getRepository().updateRssSource(rssSourceEntity);
        }
    }

}
