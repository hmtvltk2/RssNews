
package com.example.android.persistence.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.example.android.persistence.model.RssItem;
import java.util.Date;

@Entity(tableName = "rss_item")
public class RssItemEntity implements RssItem {
    @PrimaryKey
    private int id;
    private String title;
    private String description;
    private String imageLink;
    private String rssSource;
    private Date pubDate;

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getImageLink() {
        return imageLink;
    }

    @Override
    public String getRssSource() {
        return rssSource;
    }

    @Override
    public Date getPubDate() {
        return pubDate;
    }


    public RssItemEntity() {
    }

    public RssItemEntity(int id, String title, String description, String imageLink, String rssSource, Date pubDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageLink = imageLink;
        this.rssSource = rssSource;
        this.pubDate = pubDate;
    }

    public RssItemEntity(RssItem rssItem) {
        this.id = rssItem.getId();
        this.title = rssItem.getTitle();
        this.description = rssItem.getDescription();
        this.imageLink = rssItem.getImageLink();
        this.rssSource = rssItem.getRssSource();
        this.pubDate = rssItem.getPubDate();
    }
}
