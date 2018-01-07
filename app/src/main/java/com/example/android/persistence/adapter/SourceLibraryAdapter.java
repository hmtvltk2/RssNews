package com.example.android.persistence.adapter;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.android.persistence.BasicApp;
import com.example.android.persistence.R;
import com.example.android.persistence.databinding.FragmentSourceLibraryBinding;
import com.example.android.persistence.databinding.NewRssResourceBinding;
import com.example.android.persistence.db.entity.RssSourceEntity;
import com.example.android.persistence.model.RssSource;
import com.example.android.persistence.ui.MainActivity;

import java.util.List;
import java.util.Objects;


public class SourceLibraryAdapter extends RecyclerView.Adapter<SourceLibraryAdapter.ViewHolder> {
    private static final String TAG = "SourceLibraryAdapter";
    private static final int VIEW_TYPE_FOOTER = 1;
    private static final int VIEW_TYPE_CELL = 2;
    private List<? extends RssSource> mRssSource;
    private Context context;
    private MainActivity activity;
    private BasicApp basicApp;
    public SourceLibraryAdapter(Application application, Context context, Activity activity) {
        this.context = context;
        this.basicApp = (BasicApp) application;
        this.activity = (MainActivity) activity;
    }

    public void setRssSourceList(List<? extends RssSource> rssSources){
        if(mRssSource == null){
            mRssSource = rssSources;
            notifyItemRangeInserted(0, rssSources.size());
        }
        else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mRssSource.size();
                }

                @Override
                public int getNewListSize() {
                    return rssSources.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mRssSource.get(oldItemPosition).getId() == rssSources.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    RssSource oldItem = mRssSource.get(oldItemPosition);
                    RssSource newItem = rssSources.get(newItemPosition);
                    return mRssSource.get(oldItemPosition).getId() == rssSources.get(newItemPosition).getId()
                            && Objects.equals(oldItem.getName(), newItem.getName())
                            && Objects.equals(oldItem.getInLibrary(), newItem.getInLibrary())
                            && Objects.equals(oldItem.getImageUrl(), newItem.getImageUrl())
                            && Objects.equals(oldItem.getLink(), newItem.getLink());

                }
            });

            mRssSource = rssSources;
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_CELL){
            FragmentSourceLibraryBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.fragment_source_library, parent, false);
            return new ViewHolder(binding);
        }
        else {
            NewRssResourceBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.new_rss_resource, parent, false);
            return new ViewHolder(binding);
        }

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if(position == getItemCount() - 1){
            holder.mNewRssResourceBinding.addRssSource.setOnClickListener(view -> activity.viewTopicFragment());
        }
        else {
            if (holder.mSourceLibraryBinding == null) {
                return;
            }
            Log.d(TAG, "Position: " + position);
            holder.mSourceLibraryBinding.setRssSource(mRssSource.get(position));
            holder.mSourceLibraryBinding.textViewOptions.setOnClickListener(view -> {
                PopupMenu popup = new PopupMenu(context, view);
                //inflating menu from xml resource
                popup.inflate(R.menu.library_menu);
                //adding click listener
                popup.setOnMenuItemClickListener(item -> {
                    switch (item.getItemId()) {
                        case R.id.remove_source:
                            Log.d(TAG, "Remove source");
                            RssSourceEntity entity = new RssSourceEntity(mRssSource.get(position));
                            entity.setInLibrary(0);
                            basicApp.getRepository().updateRssSource(entity);
                            break;

                    }
                    return false;
                });
                //displaying the popup
                popup.show();
            });
            holder.mSourceLibraryBinding.executePendingBindings();
        }
    }

    @Override
    public int getItemCount() {
        return mRssSource == null? 1: mRssSource.size()+1 ;
    }

    @Override
    public int getItemViewType(int position) {
        return position == (getItemCount() - 1)? VIEW_TYPE_FOOTER: VIEW_TYPE_CELL;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        FragmentSourceLibraryBinding mSourceLibraryBinding;
        NewRssResourceBinding mNewRssResourceBinding;

        ViewHolder(FragmentSourceLibraryBinding binding) {
            super(binding.getRoot());
            mSourceLibraryBinding = binding;
        }

        ViewHolder(NewRssResourceBinding binding){
            super(binding.getRoot());
            mNewRssResourceBinding = binding;
        }
    }

}
