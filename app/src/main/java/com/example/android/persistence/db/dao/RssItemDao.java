
package com.example.android.persistence.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.android.persistence.db.entity.RssItemEntity;

import java.util.List;

@Dao
public interface RssItemDao {
    @Query("SELECT * FROM RssItemEntity")
    LiveData<List<RssItemEntity>> loadAllRssItems();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<RssItemEntity> rssItems);

    @Query("select * from RssItemEntity where id = :rssItemId")
    LiveData<RssItemEntity> loadRssItem(int rssItemId);

    @Query("select * from RssItemEntity where id = :rssItemId")
    RssItemEntity loadRssItemSync(int rssItemId);
}
