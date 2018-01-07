package com.example.android.persistence.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.android.persistence.R;
import com.example.android.persistence.databinding.FragmentTopicBinding;
import com.example.android.persistence.model.Topic;
import com.example.android.persistence.ui.TopicClickCallback;

import java.util.List;
import java.util.Objects;


public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.ViewHolder> {
    private List<? extends Topic> mTopicList;
    private final TopicClickCallback mTopicClickCallback;
    private Context context;
    public TopicAdapter(TopicClickCallback topicClickCallback, Context context) {
        mTopicClickCallback = topicClickCallback;
        this.context =context;
    }

    public void setTopics(List<? extends Topic> topics){
        if(mTopicList == null){
            mTopicList = topics;
            notifyItemRangeInserted(0, topics.size());
        }
        else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mTopicList.size();
                }

                @Override
                public int getNewListSize() {
                    return topics.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mTopicList.get(oldItemPosition).getId() == topics.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    return mTopicList.get(oldItemPosition).getId() == topics.get(newItemPosition).getId()
                            && Objects.equals(mTopicList.get(oldItemPosition).getName(), topics.get(newItemPosition).getName());

                }
            });

            mTopicList = topics;
            result.dispatchUpdatesTo(this);
        }
    }
    
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FragmentTopicBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.fragment_topic, parent, false);
        binding.setCallback(mTopicClickCallback);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.binding.setTopic(mTopicList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mTopicList == null? 0: mTopicList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final FragmentTopicBinding binding;

        public ViewHolder(FragmentTopicBinding binding) {
            super(binding.getRoot());
            this.binding  = binding;
        }

    }
}
