package com.example.android.persistence.ui;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.persistence.R;
import com.example.android.persistence.RssFeed.RssDownloadService;
import com.example.android.persistence.adapter.RssListAdapter;
import com.example.android.persistence.databinding.FragmentNewsListBinding;
import com.example.android.persistence.viewmodel.RssListViewModel;

public class RssListFragment extends Fragment {
    private static final String TAG = "RssListFragment";
    private SwipeRefreshLayout swipeRefreshLayout;

    private RssListAdapter adapter;
    private FragmentNewsListBinding mBinding;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_news_list, container, false);

        swipeRefreshLayout = mBinding.getRoot().findViewById(R.id.swipe_refresh_layout_recycler_view);
        swipeRefreshLayout.setColorSchemeResources(R.color.google_blue, R.color.google_green, R.color.google_red, R.color.google_yellow);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            updateRssNews();
            swipeRefreshLayout.setRefreshing(false);

        });

        adapter = new RssListAdapter(mRssItemClickCallback, getActivity().getApplication(), getContext());
        mBinding.rssList.setAdapter(adapter);
        Log.d(TAG, "RssListFragment is created");
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final RssListViewModel viewModel = ViewModelProviders.of(this).get(RssListViewModel.class);
        subscribeUi(viewModel);
        //updateRssNews();
    }

    private void subscribeUi(RssListViewModel viewModel){
        viewModel.getRssItems().observe(this, rssItems -> {
            if (rssItems != null){
                adapter.setRssList(rssItems);
                Log.d(TAG, "Update rssItems: "+ rssItems.size());
            }

            mBinding.executePendingBindings();
        });
    }

    private void updateRssNews() {
        Intent intent = new Intent(getActivity(), RssDownloadService.class);
        getActivity().startService(intent);
    }

    private final RssItemClickCallback mRssItemClickCallback = rssItem -> {
        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            Intent intent = new Intent(getContext(), RssItemActivity.class);
            intent.putExtra(RssItemActivity.KEY_RSS_LINK, rssItem.getLink());
            getContext().startActivity(intent);
        }
    };

}
