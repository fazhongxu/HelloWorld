package com.xxl.example.di.module;

import com.xxl.example.dagger.di.DaggerDataStoreModule;
import com.xxl.example.origin.anotation.ForHttpClient;
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


    @ForRetorfit
    @Singleton
    @Provides
    Retrofit provideRetrofit(@ForHttpClient OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl("http://github.com/fazhongxu/")
                .client(okHttpClient)
                .build();
    }
}