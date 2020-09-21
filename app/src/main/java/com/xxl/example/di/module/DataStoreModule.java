package com.xxl.example.di.module;

import com.xxl.example.dagger.di.DaggerDataStoreModule;
import com.xxl.example.origin.anotation.ForHttpClient;
import com.xxl.example.origin.anotation.ForQiNiuRetorfit;
import com.xxl.example.origin.anotation.ForRetorfit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * @author xxl.
 * @date 2020/9/18.
 */
@Module(includes = DaggerDataStoreModule.class)
public class DataStoreModule {

    /**
     * 生成OkHttpClient
     *
     * @return
     */
    @ForHttpClient
    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder().build();
    }


    /**
     * 构建项目网络请求
     *
     * @param okHttpClient
     * @return
     */
    @ForRetorfit
    @Singleton
    @Provides
    Retrofit provideRetrofit(@ForHttpClient OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl("http://github.com/fazhongxu/")
                .client(okHttpClient)
                .build();
    }

    /**
     * 构建七牛云网络请求
     *
     * @param okHttpClient
     * @return
     */
    @ForQiNiuRetorfit
    @Singleton
    @Provides
    Retrofit provideQiNiuRetrofit(@ForHttpClient OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl("http://github.com/fazhongxu/")
                .client(okHttpClient)
                .build();
    }
}