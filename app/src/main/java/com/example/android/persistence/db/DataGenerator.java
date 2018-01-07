package com.example.android.persistence.db;

import com.example.android.persistence.R;
import com.example.android.persistence.db.entity.RssSourceEntity;
import com.example.android.persistence.db.entity.TopicEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Generates data to pre-populate the database
 */
public class DataGenerator {
    
    public static List<RssSourceEntity> generateRssSources() {
        List<RssSourceEntity> rssSources = new ArrayList<>();

        rssSources.add(new RssSourceEntity(
                1,
                "https://vnexpress.net/rss/thoi-su.rss",
                "VnExpress",
                "https://s.vnecdn.net/vnexpress/restruct/i/v48/logos/57x57.png",
                2,
                0
                ));

        rssSources.add(new RssSourceEntity(
                2,
                "https://vnexpress.net/rss/the-gioi.rss",
                "VnExpress",
                "https://s.vnecdn.net/vnexpress/restruct/i/v48/logos/57x57.png",
                1,
                1));

        rssSources.add(new RssSourceEntity(
                3,
                "https://vnexpress.net/rss/kinh-doanh.rss",
                "VnExpress",
                "https://s.vnecdn.net/vnexpress/restruct/i/v48/logos/57x57.png",
                5,
                1));
        rssSources.add(new RssSourceEntity(
                4,
                "http://www.24h.com.vn/upload/rss/tintuctrongngay.rss",
                "24h",
                "http://static.24h.com.vn/images/m2014/images/logo-24h_bookmarks.png",
                2,
                1));
        rssSources.add(new RssSourceEntity(
                5,
                "http://www.24h.com.vn/upload/rss/bongda.rss",
                "24h",
                "http://static.24h.com.vn/images/m2014/images/logo-24h_bookmarks.png",
                6,
                0));

        return rssSources;
    }

    public static List<TopicEntity> generateTopics(){
        List<TopicEntity> topics = new ArrayList<>();

        topics.add(new TopicEntity(1, "Thế giới", R.drawable.earth_120x120));
        topics.add(new TopicEntity(2, "Thời sự", R.drawable.news));
        topics.add(new TopicEntity(3, "Sức khỏe", R.drawable.heath));
        topics.add(new TopicEntity(4, "Du lịch", R.drawable.travel));
        topics.add(new TopicEntity(5, "Kinh doanh", R.drawable.business));
        topics.add(new TopicEntity(6, "Thể thao", R.drawable.sport));

        return topics;
    }

}
