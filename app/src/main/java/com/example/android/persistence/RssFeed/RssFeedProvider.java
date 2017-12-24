package com.example.android.persistence.RssFeed;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RssFeedProvider {

    private static class Patterns
    {
        static final Pattern SRC = Pattern.compile("\\s*(?i)src\\s*=\\s*(\"([^\"]*\")|'[^']*'|([^'\">\\s]+))");
        static final Pattern DATA_ORIGINAL = Pattern.compile("\\s*(?i)data-original\\s*=\\s*(\"([^\"]*\")|'[^']*'|([^'\">\\s]+))");
        static final Pattern DESCRIPTION = Pattern.compile("\\s*(?i)br>\\s*(([^\"]*)|'[^']*'|([^'\">\\s]+))");
    }

    private static final String PUB_DATE = "pubDate";
    private static final String DESCRIPTION = "description";
    private static final String CHANNEL = "channel";
    private static final String LINK = "link";
    private static final String TITLE = "title";
    private static final String ITEM = "item";

    public static ArrayList<RssItem> parse(String rssFeed){
        ArrayList<RssItem> list = new ArrayList<>();
        XmlPullParser parser = Xml.newPullParser();
        InputStream stream = null;
        try {
            stream = new URL(rssFeed).openConnection().getInputStream();
            parser.setInput(stream, null);
            int eventType = parser.getEventType();
            boolean done = false;
            RssItem item = null;
            while (eventType != XmlPullParser.END_DOCUMENT && !done) {
                String name;
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        name = parser.getName();
                        if (name.equalsIgnoreCase(ITEM)) {
                            Log.i("new item", "Create new item");
                            item = new RssItem();
                        } else if (item != null) {
                            switch (name){
                                case LINK:
                                    item.setLink(parser.nextText());
                                    Log.i("Link", item.getLink());
                                    break;
                                case DESCRIPTION:
                                    String nextText = parser.nextText().trim();
                                    Log.i("NextText", nextText);

                                    Matcher matcherHref = Patterns.SRC.matcher(nextText);

                                    if(matcherHref.find())
                                    {
                                        // If we get here, we have an image to download and save.
                                        String imgLink = matcherHref.group(1);
                                        imgLink = imgLink.replaceAll("\"","");

                                        if (imgLink.isEmpty()){
                                            Log.e("ImgDataOri", "Img is empty");

                                            matcherHref = Patterns.DATA_ORIGINAL.matcher(nextText);
                                            if(matcherHref.find()){
                                                imgLink = matcherHref.group(1);
                                                Log.i("ImgDataOri", imgLink);
                                            }
                                        }
                                        // Get rid of any apostrophes and quotation marks in the link.
                                        imgLink = imgLink.replaceAll("\"","");

                                        if(!imgLink.isEmpty()){
                                            item.setImageLink(imgLink);
                                        }
                                        Log.i("ImgLink2", imgLink);
                                    }

                                    Matcher matcherDescription = Patterns.DESCRIPTION.matcher(nextText);
                                    if (matcherDescription.find()){
                                        String description = matcherDescription.group(1).replaceAll("]]>", "");
                                        Log.i("Description", description);
                                        item.setDescription(description);
                                    }
                                    break;
                                case PUB_DATE:
                                    item.setPubDate(parser.nextText());
                                    Log.i("PubDate", item.getPubDate());
                                    break;
                                case TITLE:
                                    item.setTitle(parser.nextText().trim());
                                    Log.i("Title", item.getTitle());
                                    break;
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        name = parser.getName();
                        Log.i("End tag", name);
                        if (name.equalsIgnoreCase(ITEM) && item != null) {
                            list.add(item);
                        } else if (name.equalsIgnoreCase(CHANNEL)) {
                            done = true;
                        }
                        break;
                }
                eventType = parser.next();
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }


}