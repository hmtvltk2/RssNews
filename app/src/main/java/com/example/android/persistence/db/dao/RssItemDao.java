
package com.example.android.persistence.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.android.persistence.db.entity.RssItemEntity;

import java.util.List;

@Dao
public interface RssItemDao {
    @Query("SELECT * FROM rss_item ORDER BY pubDate DESC LIMIT 40")
    LiveData<List<RssItemEntity>> loadAllRssItems();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<RssItemEntity> rssItems);

    @Query("SELECT * FROM rss_item WHERE id = :rssItemId")
    LiveData<RssItemEntity> loadRssItem(String rssItemId);

    @Query("SELECT * FROM rss_item WHERE id = :rssItemId")
    RssItemEntity loadRssItemSync(String rssItemId);

    @Query("SELECT * FROM rss_item WHERE bookmark = 1 ORDER BY id DESC LIMIT 40")
    LiveData<List<RssItemEntity>> loadAllBookmarkRss();

    @Update
    void update(RssItemEntity rssItem);
}
