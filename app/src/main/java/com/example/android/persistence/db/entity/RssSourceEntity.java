package com.example.android.persistence.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.example.android.persistence.model.RssSource;

@Entity(tableName = "rss_source")
public class RssSourceEntity implements RssSource {
    @PrimaryKey
    private int id;
    private String link;
    private String name;
    private String imageUrl;
    private int topicId;
    private int inLibrary;


    public void setId(int id) {
        this.id = id;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getLink() {
        return this.link;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getImageUrl() {
        return this.imageUrl;
    }

    @Override
    public int getTopicId() {
        return topicId;
    }

    @Override
    public int getInLibrary() {
        return inLibrary;
    }

    public void setInLibrary(int inLibrary) {
        this.inLibrary = inLibrary;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public RssSourceEntity(){}

    public RssSourceEntity(RssSource rssSource) {
        this.id = rssSource.getId();
        this.link = rssSource.getLink();
        this.name = rssSource.getName();
        this.imageUrl = rssSource.getImageUrl();
        this.topicId = rssSource.getTopicId();
        this.inLibrary = rssSource.getInLibrary();
    }

    public RssSourceEntity(int id, String link, String name, String imageUrl, int topicId, int inLibrary) {
        this.id = id;
        this.link = link;
        this.name = name;
        this.imageUrl = imageUrl;
        this.topicId = topicId;
        this.inLibrary = inLibrary;
    }
}
