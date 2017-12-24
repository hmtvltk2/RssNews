package com.example.android.persistence.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.android.persistence.R;

public class NewsItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_item);

        String url = getIntent().getStringExtra("URL");
        WebView webView = findViewById(R.id.web_view);
        webView.loadUrl(url);
    }
}
