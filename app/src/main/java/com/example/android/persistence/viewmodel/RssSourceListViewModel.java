package com.example.android.persistence.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import com.example.android.persistence.BasicApp;
import com.example.android.persistence.db.entity.RssSourceEntity;

import java.util.List;

public class RssSourceListViewModel extends AndroidViewModel {
    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<RssSourceEntity>> mObservableRssSources;
    private final MediatorLiveData<List<RssSourceEntity>> mObservableRssSourceInLibrary;

    public RssSourceListViewModel(Application application) {
        super(application);
        mObservableRssSources = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        mObservableRssSources.setValue(null);

        LiveData<List<RssSourceEntity>> rssSources = ((BasicApp) application).getRepository()
                .loadRssSources();

        // observe the changes of the rssSources from the database and forward them
        mObservableRssSources.addSource(rssSources, mObservableRssSources::setValue);

        mObservableRssSourceInLibrary = new MediatorLiveData<>();
        mObservableRssSourceInLibrary.setValue(null);

        LiveData<List<RssSourceEntity>> rssSourceInLibrary = ((BasicApp) application).getRepository()
                .loadRssSourceInLibrary();

        mObservableRssSourceInLibrary.addSource(rssSourceInLibrary, mObservableRssSourceInLibrary::setValue);
    }

    public LiveData<List<RssSourceEntity>> getRssSources() {
        return mObservableRssSources;
    }

    public LiveData<List<RssSourceEntity>> getRssSourceInLibrary() {
        return mObservableRssSourceInLibrary;
    }
}
