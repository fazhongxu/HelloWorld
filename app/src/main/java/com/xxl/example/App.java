package com.xxl.example;

import com.xxl.example.dagger2.AppModule;
import com.xxl.example.dagger2.DaggerAppComponnent;
import com.xxl.example.origin.BaseApp;

/**
 * @author xxl.
 * @date 2019-07-17.
 */
public class App extends BaseApp {

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerAppComponnent.builder()
                .appModule(new AppModule(this))
                .build()
                .inject(this);

    }

}
