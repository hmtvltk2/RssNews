package com.example.android.persistence;
import android.app.Application;

import com.example.android.persistence.RssFeed.RssItem;

import java.util.ArrayList;
import java.util.List;

public class RssApplication extends Application {
    public final static String URL ="url";
    public final static String RSS_UPDATE ="neuedaten";
    public final static String RSS_FILE ="rssitems.json";
    public static ArrayList<RssItem> rssItems = new ArrayList<>();
    public static List<String> rssSources = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        rssSources.add("https://vnexpress.net/rss/thoi-su.rss");
        rssSources.add("http://ictnews.vn/rss/thoi-su");

    }


}