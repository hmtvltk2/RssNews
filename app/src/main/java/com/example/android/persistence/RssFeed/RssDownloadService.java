package com.example.android.persistence.RssFeed;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.example.android.persistence.RssApplication;

import java.util.ArrayList;


public class RssDownloadService extends IntentService {

    private int result = Activity.RESULT_CANCELED;
    public static String RESULT = "result";
    public static String RSS_ITEMS = "rss_items";
    public static String NOTIFICATION = "rss_feed_updated";

    public RssDownloadService() {
        super("RssDownloadService");
    }
    private ArrayList<RssItem> rssItems = new ArrayList<>();

    @Override
    protected void onHandleIntent(Intent intent) {
        rssItems.clear();

        if(RssApplication.rssSources != null){
            for (String url: RssApplication.rssSources) {
                rssItems.addAll(RssFeedProvider.parse(url));
            }
        }
        Log.d("RssDownloadService", rssItems.size() + "");
        if (rssItems.size() > 0){
            result = Activity.RESULT_OK;
        }
        RssApplication.rssItems = rssItems;

        publishResult(rssItems, result);

        Log.d("RssDownloadService", "RssApplication.rssItems" +rssItems.size());

    }

    private void publishResult(ArrayList<RssItem> rssItems, int result){
        Intent intent = new Intent(NOTIFICATION);
        intent.putExtra(RESULT, result);
        //intent.putExtra(RSS_ITEMS, rssItems);
        sendBroadcast(intent);

    }
}