package com.example.android.persistence.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.android.persistence.R;
import com.example.android.persistence.databinding.RssSourceItemBinding;
import com.example.android.persistence.model.RssSource;
import com.example.android.persistence.ui.RssSourceClickCallback;

import java.util.List;
import java.util.Objects;

/**
 * Author: HMT
 * Date: 06/01/2018.
 */

public class RssSourceAdapter extends RecyclerView.Adapter<RssSourceAdapter.RssSourceViewHolder> {
    private List<? extends RssSource> mRssSourceList;

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
                            && Objects.equals(oldRssSource.getName(), newRssSource.getName());

                }
            });
            mRssSourceList = rssSources;
            diffResult.dispatchUpdatesTo(this);
        }
    }

    @Override
    public RssSourceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RssSourceItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.rss_source_item, parent, false);
        binding.setCallback(mRssSourceClickCallback);
        return new RssSourceViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RssSourceViewHolder holder, int position) {
        holder.binding.setRssSource(mRssSourceList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mRssSourceList == null? 0: mRssSourceList.size();
    }

    public class RssSourceViewHolder extends RecyclerView.ViewHolder {
        final RssSourceItemBinding binding;
        public RssSourceViewHolder(RssSourceItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private RssSourceClickCallback mRssSourceClickCallback = rssSource -> {
        //TODO
    };
}
