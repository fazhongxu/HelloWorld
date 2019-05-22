package com.example.xxl.module_orgin;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * Created by xxl on 19/5/22.
 * <p>
 * Description
 **/
public class BaseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            ARouter.openLog();
        }
        ARouter.init(this);

    }
}
