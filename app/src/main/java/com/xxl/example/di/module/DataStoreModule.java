package com.xxl.example.di.module;

import androidx.annotation.NonNull;

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
@Module
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
        return new OkHttpClient();
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(@NonNull final OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl("http://github.com/fazhongxu")
                .client(okHttpClient)
                .build();
    }
}