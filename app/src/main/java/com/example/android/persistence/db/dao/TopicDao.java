package com.example.android.persistence.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.android.persistence.db.entity.TopicEntity;

import java.util.List;

/**
 * Author: HMT
 * Date: 06/01/2018.
 */
@Dao
public interface TopicDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<TopicEntity> topicEntities);

    @Query("SELECT * FROM topic")
    LiveData<List<TopicEntity>> loadAllTopics();

    @Query("SELECT * FROM topic WHERE id =:topicId")
    LiveData<TopicEntity> loadTopic(int topicId);
}
