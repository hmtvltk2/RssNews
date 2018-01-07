package com.example.android.persistence.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.android.persistence.R;
import com.example.android.persistence.adapter.RssSourceAdapter;
import com.example.android.persistence.databinding.ActivityTopicDetailBinding;
import com.example.android.persistence.viewmodel.TopicViewModel;


public class TopicDetailActivity extends AppCompatActivity {
    private static final String TAG = "TopicDetailActivity";
    public static final String KEY_TOPIC_ID = "topic_id";
    private ActivityTopicDetailBinding mBinding;
    private RssSourceAdapter mRssSourceAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_topic_detail);
        setSupportActionBar(mBinding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        mRssSourceAdapter = new RssSourceAdapter(getApplication());
        mBinding.rssSourceList.setAdapter(mRssSourceAdapter);

        TopicViewModel.Factory factory = new TopicViewModel.Factory(
                getApplication(), getIntent().getIntExtra(KEY_TOPIC_ID, 0));
        Log.d(TAG, "Topic id: " + getIntent().getIntExtra(KEY_TOPIC_ID, 0));
        final TopicViewModel model = ViewModelProviders.of(this, factory)
                .get(TopicViewModel.class);
        subscribeToModel(model);
    }

    private void subscribeToModel(TopicViewModel model) {
        model.getObservableTopic().observe(this, topicEntity -> {
            if(topicEntity != null) {
                mBinding.setTopic(topicEntity);
                mBinding.executePendingBindings();
            }
        });
        model.getRssSources().observe(this, rssSources -> {
            if(rssSources != null){
                mRssSourceAdapter.setRssSourceList(rssSources);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
