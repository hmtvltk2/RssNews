package com.example.android.persistence.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.persistence.R;
import com.example.android.persistence.adapter.SourceLibraryAdapter;
import com.example.android.persistence.databinding.FragmentSourceLibraryListBinding;
import com.example.android.persistence.viewmodel.RssSourceListViewModel;


public class SourceLibraryFragment extends Fragment {
    private static final String TAG = "SourceLibraryFragment";
    private FragmentSourceLibraryListBinding mBinding;
    private SourceLibraryAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_source_library_list, container, false);

        int mColumnCount = 4;
        mBinding.list.setLayoutManager(new GridLayoutManager(getContext(), mColumnCount));
        mAdapter = new SourceLibraryAdapter(getActivity().getApplication(), getContext(), getActivity());
        mBinding.list.setAdapter(mAdapter);

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final RssSourceListViewModel viewModel = ViewModelProviders.of(this).get(RssSourceListViewModel.class);
        subscribeUi(viewModel);
    }

    private void subscribeUi(RssSourceListViewModel viewModel) {
        viewModel.getRssSourceInLibrary().observe(this, rssSources -> {
            if (rssSources != null){
                mAdapter.setRssSourceList(rssSources);
            }

            mBinding.executePendingBindings();
        });
    }
}
