package com.example.apptuprosor;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;

public class Github extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github);
        WebView myWebView = (WebView) findViewById(R.id.WebGithub);
        myWebView.loadUrl("https://github.com/CarlosAdrianAV/APPTUPROSOR");
    }
}