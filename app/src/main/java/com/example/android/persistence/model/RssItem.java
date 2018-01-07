package com.example.android.persistence.model;

import java.util.Date;

public interface RssItem {
    String getId();
    String getLink();
    String getTitle();
    String getDescription();
    String getImageLink();
    int getRssSourceId();
    Date getPubDate();
    int getBookmark();

}
