package com.example.android.persistence.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import com.example.android.persistence.BasicApp;
import com.example.android.persistence.R;
import com.example.android.persistence.db.entity.RssItemEntity;
import com.example.android.persistence.viewmodel.RssItemViewModel;

public class RssItemActivity extends AppCompatActivity {
    public static final String KEY_RSS_LINK = "rss_link";
    private String mUrl;
    private MenuItem mMenuBookmark;
    private RssItemEntity rssItem;
    private int bookmark;
    private ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_item);

        Toolbar toolbar = findViewById(R.id.toolbar_news);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        mUrl = getIntent().getStringExtra(KEY_RSS_LINK);
        WebView webView = findViewById(R.id.web_view);
        webView.loadUrl(mUrl);

        RssItemViewModel.Factory factory = new RssItemViewModel.Factory(getApplication(), mUrl);

        final RssItemViewModel model = ViewModelProviders.of(this, factory).get(RssItemViewModel.class);
        
        subscribeToModel(model);
    }

    private void subscribeToModel(RssItemViewModel model) {
        model.getmObservableRssItem().observe(this, rssItemEntity -> {
            if(rssItemEntity!= null){
                rssItem = rssItemEntity;
                bookmark = rssItemEntity.getBookmark();
                if(mMenuBookmark == null) return;

                if(bookmark == 1){
                    mMenuBookmark.setIcon(R.drawable.ic_bookmark_black_24dp);
                }
                else {
                    mMenuBookmark.setIcon(R.drawable.ic_bookmark_border_black_24dp);
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        mMenuBookmark = menu.findItem(R.id.action_menu_share);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(mMenuBookmark);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_menu_share:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, mUrl);
                mShareActionProvider.setShareIntent(intent);
                break;
            case R.id.action_menu_bookmark:
                if(bookmark == 1){
                    rssItem.setBookmark(0);
                }
                else {
                    rssItem.setBookmark(1);
                }
                ((BasicApp)getApplication()).getRepository().updateRssItem(rssItem);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
