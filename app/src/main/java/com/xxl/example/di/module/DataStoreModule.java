package com.xxl.example.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

/**
 * @author xxl.
 * @date 2020/9/18.
 */
@Module
public class DataStoreModule {

   /**
     * 生成OkHttpClient
     *
     * @return
     */
    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient();
    }
}