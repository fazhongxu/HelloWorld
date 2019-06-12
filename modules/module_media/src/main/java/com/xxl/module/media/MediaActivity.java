package com.xxl.module.media;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xxl.mediator.R;
import com.xxl.mediator.media.IConstantMedia;

@Route(path = IConstantMedia.MEDIA_PATH)
public class MediaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);
    }
}
