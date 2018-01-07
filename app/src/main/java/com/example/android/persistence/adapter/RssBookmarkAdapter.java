package com.example.android.persistence.adapter;

import android.app.Application;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.util.Log;

import com.example.android.persistence.model.RssItem;
import com.example.android.persistence.ui.RssItemClickCallback;

import java.util.List;
import java.util.Objects;

/**
 * Author: HMT
 * Date: 05/01/2018.
 */

public class RssBookmarkAdapter extends RssListAdapter {
    public RssBookmarkAdapter(@Nullable RssItemClickCallback mRssItemClickCallback, Application application, Context context) {
        super(mRssItemClickCallback, application, context);
    }



    @Override
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
        Log.d("RssBookmarkAdapter", "Current RssList: "+ mRssList.size());
    }
}