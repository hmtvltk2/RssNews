package com.example.android.persistence.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.example.android.persistence.AppExecutors;
import com.example.android.persistence.db.converter.DateConverter;
import com.example.android.persistence.db.dao.RssItemDao;
import com.example.android.persistence.db.dao.RssSourceDao;
import com.example.android.persistence.db.dao.TopicDao;
import com.example.android.persistence.db.entity.RssItemEntity;
import com.example.android.persistence.db.entity.RssSourceEntity;
import com.example.android.persistence.db.entity.TopicEntity;

import java.util.List;

@Database(entities = {RssSourceEntity.class, RssItemEntity.class, TopicEntity.class}, version = 10)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    private static final String TAG = "AppDatabase";
    private static AppDatabase sInstance;

    @VisibleForTesting
    public static final String DATABASE_NAME = "rss-news-db";

    public abstract RssItemDao rssItemDao();

    public abstract RssSourceDao rssSourceDao();

    public abstract TopicDao topicDao();

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public static AppDatabase getInstance(final Context context, final AppExecutors executors) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = buildDatabase(context.getApplicationContext(), executors);
                    sInstance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }

        return sInstance;
    }

    /**
     * Build the database. {@link Builder#build()} only sets up the database configuration and
     * creates a new instance of the database.
     * The SQLite database is only created when it's accessed for the first time.
     */
    private static AppDatabase buildDatabase(final Context appContext,
            final AppExecutors executors) {
        return Room.databaseBuilder(appContext, AppDatabase.class, DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        executors.diskIO().execute(() -> {
                            // Add a delay to simulate a long-running operation
                            addDelay();
                            // Generate the data for pre-population
                            AppDatabase database = AppDatabase.getInstance(appContext, executors);
                            List<RssSourceEntity> rssSources = DataGenerator.generateRssSources();
                            List<TopicEntity> topics = DataGenerator.generateTopics();
                            database.runInTransaction(()-> database.rssSourceDao().insertAll(rssSources));
                            database.runInTransaction(()-> database.topicDao().insertAll(topics));
                            // notify that the database was created and it's ready to be used
                            database.setDatabaseCreated();
                        });
                    }
                }).build();
    }

    /**
     * Check whether the database already exists and expose it via {@link #getDatabaseCreated()}
     */
    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated(){
        mIsDatabaseCreated.postValue(true);
    }

    private static void addDelay() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException ignored) {
        }
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }
}
