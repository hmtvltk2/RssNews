package com.example.android.persistence.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.android.persistence.db.entity.RssSourceEntity;

import java.util.List;

/**
 *  Created by HMT on 29/12/2017.
 */
@Dao
public interface RssSourceDao {
    @Query("SELECT * FROM rss_source")
    LiveData<List<RssSourceEntity>> loadAllRssSources();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<RssSourceEntity> rssSources);

    @Query("SELECT * FROM rss_source WHERE id = :rssSourceId")
    LiveData<RssSourceEntity> loadRssSource(int rssSourceId);

    @Query("SELECT * FROM rss_source WHERE id = :rssSourceId")
    RssSourceEntity loadRssSourceSync(int rssSourceId);

    @Update
    void update(RssSourceEntity rssSourceEntity);

    @Query("SELECT * FROM rss_source WHERE inLibrary = 1")
    LiveData<List<RssSourceEntity>> loadRssSourceInLibrary();

    @Query("SELECT * FROM rss_source WHERE topicId =:topicId")
    LiveData<List<RssSourceEntity>> loadRssSourceByTopicId(int topicId);
}
