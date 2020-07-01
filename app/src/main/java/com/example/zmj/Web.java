package com.example.zmj;
/**create in 2020-6-28 by hans*/
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

//用来响应主页banner的点击事件，弹出一个webview

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
