package com.example.android.persistence.model;

import java.util.Date;

public interface RssItem {
    int getId();
    String getTitle();
    String getDescription();
    String getImageLink();
    String getRssSource();
    Date getPubDate();

}
