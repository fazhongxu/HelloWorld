package com.xxl.example.web;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.xxl.example.mediator.web.IConstantWeb;

@Route(path = IConstantWeb.WEB_WEBVIEW)
public class WebViewActivity extends AppCompatActivity {
    @Autowired(name = IConstantWeb.KEY_URL)
    String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        // inject 之后 通过  @Autowired 注解获取值 代替 getIntent 方式获取的值
        ARouter.getInstance().inject(this);
        //String url = getIntent().getStringExtra(IConstantWeb.KEY_URL);
        TextView webUrlTv = findViewById(R.id.web_url_tv);
        webUrlTv.setText(mUrl);

        Log.e("aaa", "onCreate: "+mUrl );
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fl_container, WebViewFragment.newInstance(getIntent().getExtras()))
        .commitAllowingStateLoss();
    }
}