
package com.example.android.persistence.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.example.android.persistence.model.RssItem;
import java.util.Date;

@Entity(tableName = "rss_item")
public class RssItemEntity implements RssItem {
    @PrimaryKey
    @NonNull
    private String id;
    private String link;
    private String title;
    private String description;
    private String imageLink;
    private int rssSourceId;
    private Date pubDate;
    private int bookmark;

    @Override
    public String getLink() {
        return this.link;
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
    public int getRssSourceId() {
        return rssSourceId;
    }

    @Override
    public Date getPubDate() {
        return pubDate;
    }

    @Override
    public int getBookmark() {
        return bookmark;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public void setRssSourceId(int rssSourceId) {
        this.rssSourceId = rssSourceId;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setBookmark(int bookmark) {
        this.bookmark = bookmark;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public RssItemEntity() {
        id = "";
    }

    public RssItemEntity(RssItem rssItem){
        this.id = rssItem.getId();
        this.link = rssItem.getLink();
        this.pubDate = rssItem.getPubDate();
        this.imageLink = rssItem.getImageLink();
        this.bookmark = rssItem.getBookmark();
        this.description = rssItem.getDescription();
        this.title = rssItem.getTitle();
        this.rssSourceId = rssItem.getRssSourceId();
    }
}
