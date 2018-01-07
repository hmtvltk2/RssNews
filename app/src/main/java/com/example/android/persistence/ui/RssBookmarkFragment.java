package com.example.android.persistence.ui;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.persistence.R;
import com.example.android.persistence.adapter.RssBookmarkAdapter;
import com.example.android.persistence.adapter.RssListAdapter;
import com.example.android.persistence.databinding.FragmentBookmarkListBinding;
import com.example.android.persistence.viewmodel.RssBookmarkViewModel;

public class RssBookmarkFragment extends Fragment {
    private static final String TAG = "RssBookmarkFragment";

    private RssListAdapter adapter;
    private FragmentBookmarkListBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_bookmark_list, container, false);

        adapter = new RssBookmarkAdapter(mRssItemClickCallback, getActivity().getApplication(), getContext());
        mBinding.rssList.setAdapter(adapter);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final RssBookmarkViewModel viewModel = ViewModelProviders.of(this).get(RssBookmarkViewModel.class);
        subscribeUi(viewModel);
    }

    private void subscribeUi(RssBookmarkViewModel viewModel){
        viewModel.getRssBookmarks().observe(this, rssBookmarks -> {
            if (rssBookmarks != null){
                adapter.setRssList(rssBookmarks);
                Log.d(TAG, "Update rssItems: "+ rssBookmarks.size());
            }

            mBinding.executePendingBindings();
        });
    }


    private final RssItemClickCallback mRssItemClickCallback = rssItem -> {
        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            Intent intent = new Intent(getContext(), RssItemActivity.class);
            intent.putExtra(RssItemActivity.KEY_RSS_LINK, rssItem.getLink());
            getContext().startActivity(intent);
        }
    };
}
