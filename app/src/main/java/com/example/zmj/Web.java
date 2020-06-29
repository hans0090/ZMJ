package com.example.zmj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

public class Web extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        Intent intent = getIntent();
String url = intent.getStringExtra("url");
        WebView webView = (WebView)findViewById(R.id.web);
        webView.loadUrl(url);
    }
}
