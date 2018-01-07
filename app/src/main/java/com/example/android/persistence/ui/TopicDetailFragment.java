package com.example.android.persistence.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.persistence.R;
import com.example.android.persistence.adapter.RssSourceAdapter;
import com.example.android.persistence.databinding.FragmentTopicDetailBinding;
import com.example.android.persistence.viewmodel.TopicViewModel;


public class TopicDetailFragment extends Fragment {
    private static final String KEY_TOPIC_ID = "topic_id";
    private FragmentTopicDetailBinding mBinding;
    private RssSourceAdapter mRssSourceAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_topic_detail, container, false);
        mRssSourceAdapter = new RssSourceAdapter();
        mBinding.rssSourceList.setAdapter(mRssSourceAdapter);

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TopicViewModel.Factory factory = new TopicViewModel.Factory(
                getActivity().getApplication(), getArguments().getInt(KEY_TOPIC_ID));

        final TopicViewModel model = ViewModelProviders.of(this, factory)
                .get(TopicViewModel.class);
        subscribeToModel(model);
    }

    private void subscribeToModel(TopicViewModel model) {
        model.getObservableTopic().observe(this, model::setTopic);
        model.getRssSources().observe(this, rssSources -> {
            if(rssSources != null){
                mRssSourceAdapter.setRssSourceList(rssSources);
            }
        });
    }

    public static TopicDetailFragment forTopic(int topicId) {
        TopicDetailFragment fragment = new TopicDetailFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_TOPIC_ID, topicId);
        fragment.setArguments(args);
        return fragment;
    }
}
