package com.example.android.persistence.ui;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.persistence.R;
import com.example.android.persistence.adapter.TopicAdapter;
import com.example.android.persistence.databinding.FragmentTopicListBinding;
import com.example.android.persistence.viewmodel.TopicListViewModel;


public class TopicFragment extends Fragment {
    private static final String TAG = "TopicFragment";
    public static final String KEY_TOPIC_ID = "TopicId";
    private FragmentTopicListBinding mBinding;
    private TopicAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_topic_list, container, false);
        
        int mColumnCount = 2;
        mBinding.list.setLayoutManager(new GridLayoutManager(getContext(), mColumnCount));
        adapter = new TopicAdapter(mTopicClickCallback, getContext());
        mBinding.list.setAdapter(adapter);
        Log.d(TAG, "RssListFragment is created");
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final TopicListViewModel viewModel = ViewModelProviders.of(this).get(TopicListViewModel.class);
        subscribeUi(viewModel);
    }

    private void subscribeUi(TopicListViewModel viewModel){
        viewModel.getTopics().observe(this, topics -> {
            if (topics != null){
                adapter.setTopics(topics);
            }

            mBinding.executePendingBindings();
        });
    }


    private final TopicClickCallback mTopicClickCallback = topic -> {
        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            Intent intent = new Intent(getContext(), TopicDetailFragment.class);
            intent.putExtra(KEY_TOPIC_ID, topic.getId());
            getContext().startActivity(intent);
        }

    };
    

}
