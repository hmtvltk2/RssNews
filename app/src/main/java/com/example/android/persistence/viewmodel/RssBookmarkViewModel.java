package com.example.android.persistence.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;

import com.example.android.persistence.BasicApp;
import com.example.android.persistence.db.entity.RssItemEntity;

import java.util.List;

/**
 * Author: HMT
 * Date: 05/01/2018.
 */

public class RssBookmarkViewModel extends AndroidViewModel {
    private final MediatorLiveData<List<RssItemEntity>> mObservableRssBookmarks;

    public RssBookmarkViewModel(@NonNull Application application) {
        super(application);
        mObservableRssBookmarks = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        mObservableRssBookmarks.setValue(null);

        LiveData<List<RssItemEntity>> rssItems = ((BasicApp) application).getRepository()
                .getRssBookmarks();

        // observe the changes of the rssItems from the database and forward them
        mObservableRssBookmarks.addSource(rssItems, mObservableRssBookmarks::setValue);

    }

    public LiveData<List<RssItemEntity>> getRssBookmarks() {
        return mObservableRssBookmarks;
    }
}
