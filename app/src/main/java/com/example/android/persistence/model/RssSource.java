package com.example.android.persistence.model;

/**
 *  Created by -HMT- on 29/12/2017.
 */

public interface RssSource {
    int getId();
    String getLink();
    String getName();
    String getImageUrl();
    int getTopicId();
    int getInLibrary();
}
