package com.example.roberto.alarmbroadcastreceiver;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

public class Details extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_news_details);
        WebView webView = (WebView) findViewById(R.id.wv);
        String url = getIntent().getStringExtra("url");
        Toast.makeText(this,url,Toast.LENGTH_LONG).show();
        webView.loadUrl(url);
    }
}
