package com.example.android.persistence.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.example.android.persistence.BasicApp;
import com.example.android.persistence.DataRepository;
import com.example.android.persistence.db.entity.RssItemEntity;

/**
 * Author: -HMT-
 * Date: 29/12/2017.
 */

public class RssItemViewModel extends AndroidViewModel {
    private final LiveData<RssItemEntity> mObservableRssItem;

    public ObservableField<RssItemEntity> rssItem = new ObservableField<>();

    private final String mRssItemId;

    public RssItemViewModel(@NonNull Application application, DataRepository repository, final String rssItemId) {
        super(application);
        mRssItemId = rssItemId;
        mObservableRssItem = repository.loadRssItem(rssItemId);
    }

    public LiveData<RssItemEntity> getmObservableRssItem(){
        return mObservableRssItem;
    }

    public void setRssItem(RssItemEntity rssItem){
        this.rssItem.set(rssItem);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory{
        @NonNull
        private final Application mApplication;
        private final String mRssItemId;
        private final DataRepository mRepository;

        public Factory(@NonNull Application application, String rssItemId) {
            this.mApplication = application;
            this.mRssItemId = rssItemId;
            this.mRepository = ((BasicApp) application).getRepository();
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            //noinspection unchecked
            return (T) new RssItemViewModel(mApplication, mRepository, mRssItemId);
        }
    }
}
