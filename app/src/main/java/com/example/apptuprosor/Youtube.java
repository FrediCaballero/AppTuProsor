package com.example.apptuprosor;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;

public class Youtube extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);
        WebView myWebView = (WebView) findViewById(R.id.WebYoutube);
        myWebView.loadUrl("https://www.youtube.com/channel/UCacHiR8-bwhDfF-tTrnrITA/videos");
    }
}