package com.example.xxl.helloworld.dagger2;

import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * @author xxl.
 * @date 2019-07-17.
 */
@Module
public class AppModule {
    private Context mContext;

    public AppModule(Application application) {
        this.mContext = application;
    }

    @Provides
    Context provideContext() {
        return mContext;
    }
}
