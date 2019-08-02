package com.xxl.example.dagger2;

import android.app.Application;

import dagger.Component;

/**
 * @author xxl.
 * @date 2019-07-17.
 */
@Component(modules = AppModule.class)
public interface AppComponnent {
    void inject(Application application);
}
