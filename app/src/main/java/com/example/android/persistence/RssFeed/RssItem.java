package com.example.android.persistence.RssFeed;

import java.io.Serializable;

public class RssItem implements Serializable {
    private String pubDate;
    private String description;
    private String imageLink;
    private String link;
    private String title;
    private String summarize;
    private static final long serialVersionUID = 1L;

    public RssItem(){}
    public RssItem(String title, String link) {
        this.title = title;
        this.link = link;
    }
    public String getPubDate() {
        return pubDate;
    }
    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getLink() {
        return link;
    }
    public void setLink(String link) {
        this.link = link;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getSummarize() {
        return summarize;
    }

    public void setSummarize(String summarize) {
        this.summarize = summarize;
    }

    @Override
    public String toString() {
        return "RssItem [title=" + title + "]";
    }
}