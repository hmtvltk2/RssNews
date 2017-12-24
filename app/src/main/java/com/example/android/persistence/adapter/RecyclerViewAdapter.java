package com.example.android.persistence.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.android.persistence.ImageLoader;
import com.example.android.persistence.R;
import com.example.android.persistence.RssFeed.RssItem;
import com.example.android.persistence.activity.NewsItemActivity;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    private Context context;
    private List<RssItem> rssItems;
    private static String TAG = "RecyclerViewAdapter";
    public RecyclerViewAdapter(Context context) {
        this.context = context;
        rssItems = new ArrayList<>();
    }

    public void setItems(List<RssItem> data) {
        this.rssItems = data;
        Log.d(TAG, rssItems.size() + rssItems.toString());
        notifyDataSetChanged();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
        RssItem rssItem =rssItems.get(position);
        holder.textViewTitle.setText(rssItem.getTitle());
        holder.textViewDetail.setText(rssItem.getDescription());
        ImageLoader.loadAll(context, rssItem.getImageLink(), holder.imageViewNews);
        final String url = rssItems.get(position).getLink();

        holder.mView.setOnClickListener(view -> {
            Intent intent = new Intent(context, NewsItemActivity.class);
            intent.putExtra("URL", url);
            context.startActivity(intent);
        });

    }


    @Override
    public int getItemCount() {
        return rssItems.size();
    }


    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewWebsite;
        ImageView imageViewNews;
        TextView textViewTitle;
        TextView textViewDetail;
        TextView textViewWebsiteName;
        TextView textViewTime;
        View mView;

        private RecyclerViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            textViewTitle = itemView.findViewById(R.id.tv_news_title);
            textViewDetail = itemView.findViewById(R.id.tv_news_detail);
            textViewTime = itemView.findViewById(R.id.tv_time);
            textViewWebsiteName = itemView.findViewById(R.id.tv_website_name);
            imageViewNews = itemView.findViewById(R.id.img_news);
            imageViewWebsite = itemView.findViewById(R.id.img_website);
        }
    }


}
