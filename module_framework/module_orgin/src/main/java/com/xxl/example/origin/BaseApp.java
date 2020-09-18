package com.xxl.example.origin;

import com.alibaba.android.arouter.launcher.ARouter;

import dagger.android.DaggerApplication;

/**
 * Created by xxl on 19/5/22.
 * <p>
 * Description
 **/
public abstract class BaseApp extends DaggerApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            ARouter.openLog();
        }
        ARouter.init(this);

    }
}
