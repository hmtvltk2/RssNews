package com.example.android.persistence.db;

import com.example.android.persistence.db.entity.RssSourceEntity;
import com.example.android.persistence.db.entity.TopicEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Generates data to pre-populate the database
 */
public class DataGenerator {

    private static final String[] TOPICS = new String[]{
          "Thế giới",
            "Thời sự",
            "Sức khỏe",
            "Gia đình",
            "Du lịch",
            "Kinh doanh"
    };
    
    public static List<RssSourceEntity> generateRssSources() {
        List<RssSourceEntity> rssSources = new ArrayList<>();

        rssSources.add(new RssSourceEntity(
                1,
                "https://vnexpress.net/rss/thoi-su.rss",
                "Vnexpress",
                "https://s.vnecdn.net/vnexpress/restruct/i/v48/logos/57x57.png",
                2,
                0
                ));

        rssSources.add(new RssSourceEntity(
                2,
                "https://vnexpress.net/rss/the-gioi.rss",
                "Vnexpress",
                "https://s.vnecdn.net/vnexpress/restruct/i/v48/logos/57x57.png",
                1,
                1));

        rssSources.add(new RssSourceEntity(
                3,
                "https://vnexpress.net/rss/kinh-doanh.rss",
                "Vnexpress",
                "https://s.vnecdn.net/vnexpress/restruct/i/v48/logos/57x57.png",
                6,
                1));
        rssSources.add(new RssSourceEntity(
                4,
                "http://www.24h.com.vn/upload/rss/tintuctrongngay.rss",
                "24h",
                "http://static.24h.com.vn/images/m2014/images/logo-24h_bookmarks.png",
                2,
                1));

        return rssSources;
    }

    public static List<TopicEntity> generateTopics(){
        List<TopicEntity> topics = new ArrayList<>();

        topics.add(new TopicEntity(1, "Thế giới", "@drawable/earth_120x120"));
        topics.add(new TopicEntity(2, "Thời sự", "@drawable/earth_120x120"));
        topics.add(new TopicEntity(3, "Sức khỏe", "@drawable/earth_120x120"));
        topics.add(new TopicEntity(4, "Gia đình", "@drawable/earth_120x120"));
        topics.add(new TopicEntity(5, "Du lịch", "@drawable/earth_120x120"));
        topics.add(new TopicEntity(6, "Kinh doanh", "@drawable/earth_120x120"));

        return topics;
    }

}
