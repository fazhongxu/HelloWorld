package com.example.xxl.module_web;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.xxl.mediator_web.IConstantWeb;

@Route(path = IConstantWeb.WEB_WEBVIEW)
public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        String url = getIntent().getStringExtra(IConstantWeb.KEY_URL);
        TextView webUrlTv = findViewById(R.id.web_url_tv);
        webUrlTv.setText(url);
    }
}
