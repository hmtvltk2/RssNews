package com.example.android.persistence;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.example.android.persistence.db.AppDatabase;
import com.example.android.persistence.db.entity.RssItemEntity;
import com.example.android.persistence.db.entity.RssSourceEntity;
import com.example.android.persistence.db.entity.TopicEntity;

import java.util.List;


public class DataRepository {

    private static DataRepository sInstance;

    private final AppDatabase mDatabase;
    private MediatorLiveData<List<RssItemEntity>> mObservableRssItems;
    private MediatorLiveData<List<RssItemEntity>> mObservableRssBookmarks;

    private DataRepository(final AppDatabase database) {
        mDatabase = database;
        mObservableRssItems = new MediatorLiveData<>();

        mObservableRssItems.addSource(mDatabase.rssItemDao().loadAllRssItems(),
                RssItemEntities -> {
                    if (mDatabase.getDatabaseCreated().getValue() != null) {
                        mObservableRssItems.postValue(RssItemEntities);
                        Log.d("DataRepository", "mObservableRssItems changed: " + RssItemEntities.toString());
                    }
                });

        mObservableRssBookmarks = new MediatorLiveData<>();

        mObservableRssBookmarks.addSource(mDatabase.rssItemDao().loadAllBookmarkRss(),
                RssItemEntities -> {
                    if (mDatabase.getDatabaseCreated().getValue() != null) {
                        mObservableRssBookmarks.postValue(RssItemEntities);
                        Log.d("DataRepository", "mObservableRssBookmarks changed: " + RssItemEntities.size());
                    }
                });
    }

    public static DataRepository getInstance(final AppDatabase database) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository(database);
                }
            }
        }
        return sInstance;
    }

    /**
     * Get the list of rssItems from the database and get notified when the data changes.
     */
    public LiveData<List<RssItemEntity>> getRssItems() {
        return mObservableRssItems;
    }

    public LiveData<List<RssItemEntity>> getRssBookmarks(){
        return mObservableRssBookmarks;
    }

    public LiveData<RssItemEntity> loadRssItem(final String rssItemId) {
        return mDatabase.rssItemDao().loadRssItem(rssItemId);
    }

    public LiveData<List<RssSourceEntity>> loadRssSources(){
        return mDatabase.rssSourceDao().loadAllRssSources();
    }

    public LiveData<RssSourceEntity> loadRssSource(int rssSourceId){
        return mDatabase.rssSourceDao().loadRssSource(rssSourceId);
    }

    public LiveData<List<RssSourceEntity>> loadRssSourceInLibrary() {
        return mDatabase.rssSourceDao().loadRssSourceInLibrary();
    }

    /*public LiveData<BookmarkEntity> loadBookmark(String rssId){
        return mDatabase.bookmarkDao().getBookmark(rssId);
    }

    public LiveData<List<BookmarkEntity>> loadBookmarks(){
        return mDatabase.bookmarkDao().getBookmarks();
    }*/

    public void insertRssItems(List<RssItemEntity> rssItemEntities)
    {
        new AsyncTask<Void, Void, Void>(){

            @Override
            protected Void doInBackground(Void... voids) {
                mDatabase.rssItemDao().insertAll(rssItemEntities);
                return null;
            }
        }.execute();

    }

    public void updateRssItem(RssItemEntity rssItem){
        new AsyncTask<Void, Void, Void>(){

            @Override
            protected Void doInBackground(Void... voids) {
                mDatabase.rssItemDao().update(rssItem);
                return null;
            }
        }.execute();
    }

    public void updateRssSource(RssSourceEntity rssSourceEntity) {
        new AsyncTask<Void, Void, Void>(){

            @Override
            protected Void doInBackground(Void... voids) {
                mDatabase.rssSourceDao().update(rssSourceEntity);
                return null;
            }
        }.execute();
    }

    public LiveData<List<TopicEntity>> getTopics() {
        return mDatabase.topicDao().loadAllTopics();
    }

    public LiveData<List<RssSourceEntity>> loadRssSourceByTopicId(int mTopicId) {
        return mDatabase.rssSourceDao().loadRssSourceByTopicId(mTopicId);
    }

    public LiveData<TopicEntity> loadTopic(int topicId) {
        return mDatabase.topicDao().loadTopic(topicId);
    }



  /*  public void updateBookmark(BookmarkEntity bookmarkEntity){
        new AsyncTask<Void, Void, Void>(){

            @Override
            protected Void doInBackground(Void... voids) {
                if(mDatabase.bookmarkDao().getBookmarkSync(bookmarkEntity.getRssId()) != null){
                    mDatabase.bookmarkDao().delete(bookmarkEntity);
                }
                else {
                    mDatabase.bookmarkDao().insert(bookmarkEntity);
                }
                return null;
            }
        }.execute();
    }
*/

}
