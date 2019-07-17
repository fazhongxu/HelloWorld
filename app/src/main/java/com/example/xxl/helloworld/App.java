package com.example.xxl.helloworld;

import com.example.xxl.helloworld.dagger2.AppModule;
import com.example.xxl.helloworld.dagger2.DaggerAppComponnent;
import com.example.xxl.module_orgin.BaseApp;

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
