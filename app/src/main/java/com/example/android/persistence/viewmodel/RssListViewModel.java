package com.example.android.persistence.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import com.example.android.persistence.BasicApp;
import com.example.android.persistence.db.entity.RssItemEntity;

import java.util.List;

/**
 *  Created by --HMT-- on 29/12/2017.
 */

public class RssListViewModel extends AndroidViewModel {
    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<RssItemEntity>> mObservableRssItems;

    public RssListViewModel(Application application) {
        super(application);
        mObservableRssItems = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        mObservableRssItems.setValue(null);

        LiveData<List<RssItemEntity>> rssItems = ((BasicApp) application).getRepository()
                .getRssItems();

        // observe the changes of the rssItems from the database and forward them
        mObservableRssItems.addSource(rssItems, mObservableRssItems::setValue);
    }

    public LiveData<List<RssItemEntity>> getRssItems() {
        return mObservableRssItems;
    }
}
