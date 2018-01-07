package com.example.android.persistence.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.android.persistence.BasicApp;
import com.example.android.persistence.R;
import com.example.android.persistence.databinding.ActivityNewsItemBinding;
import com.example.android.persistence.db.entity.RssItemEntity;
import com.example.android.persistence.viewmodel.RssItemViewModel;

public class RssItemActivity extends AppCompatActivity {
    public static final String KEY_RSS_LINK = "rss_link";
    private String mUrl;
    private MenuItem mMenuBookmark;
    private RssItemEntity rssItem;
    private int bookmark;
    private ShareActionProvider mShareActionProvider;
    private ActivityNewsItemBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_news_item);

        setSupportActionBar(mBinding.toolbarNews);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        mUrl = getIntent().getStringExtra(KEY_RSS_LINK);
  
        initWebView();
        mBinding.webView.loadUrl(mUrl);

        RssItemViewModel.Factory factory = new RssItemViewModel.Factory(getApplication(), mUrl);

        final RssItemViewModel model = ViewModelProviders.of(this, factory).get(RssItemViewModel.class);
        
        subscribeToModel(model);
    }

    private void initWebView() {
        mBinding.webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mBinding.progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                mBinding.webView.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mBinding.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                mBinding.progressBar.setVisibility(View.GONE);
            }
        });
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
