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
import com.example.android.persistence.db.entity.RssSourceEntity;
import com.example.android.persistence.db.entity.TopicEntity;

import java.util.List;

/**
 * Author: HMT
 * Date: 06/01/2018.
 */

public class TopicViewModel extends AndroidViewModel {
    private final LiveData<TopicEntity> mObservableTopic;

    public ObservableField<TopicEntity> topic = new ObservableField<>();

    private final int mTopicId;

    private final LiveData<List<RssSourceEntity>> mObservableRssSources;

    public TopicViewModel(@NonNull Application application, DataRepository repository,
                            final int topicId) {
        super(application);
        mTopicId = topicId;

        mObservableRssSources = repository.loadRssSourceByTopicId(mTopicId);
        mObservableTopic = repository.loadTopic(mTopicId);
    }

    /**
     * Expose the LiveData RssSources query so the UI can observe it.
     */
    public LiveData<List<RssSourceEntity>> getRssSources() {
        return mObservableRssSources;
    }

    public LiveData<TopicEntity> getObservableTopic() {
        return mObservableTopic;
    }

    public void setTopic(TopicEntity topic) {
        this.topic.set(topic);
    }

    /**
     * A creator is used to inject the topic ID into the ViewModel
     * <p>
     * This creator is to showcase how to inject dependencies into ViewModels. It's not
     * actually necessary in this case, as the topic ID can be passed in a public method.
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;

        private final int mTopicId;

        private final DataRepository mRepository;

        public Factory(@NonNull Application application, int topicId) {
            mApplication = application;
            mTopicId = topicId;
            mRepository = ((BasicApp) application).getRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new TopicViewModel(mApplication, mRepository, mTopicId);
        }
    }
}
