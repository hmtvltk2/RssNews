package com.example.android.persistence.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import com.example.android.persistence.BasicApp;
import com.example.android.persistence.db.entity.TopicEntity;

import java.util.List;

/**
 * Author: HMT
 * Date: 06/01/2018.
 */

public class TopicListViewModel extends AndroidViewModel {
    private final MediatorLiveData<List<TopicEntity>> mObservableTopics;

    public TopicListViewModel(Application application) {
        super(application);
        mObservableTopics = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        mObservableTopics.setValue(null);

        LiveData<List<TopicEntity>> Topics = ((BasicApp) application).getRepository()
                .getTopics();

        // observe the changes of the Topics from the database and forward them
        mObservableTopics.addSource(Topics, mObservableTopics::setValue);
    }

    public LiveData<List<TopicEntity>> getTopics() {
        return mObservableTopics;
    }
}
