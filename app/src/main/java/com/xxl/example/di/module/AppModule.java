package com.xxl.example.di.module;

import android.app.Application;

import androidx.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author xxl.
 * @date 2020/9/18.
 */
@Module
public class AppModule {

    @Singleton
    @Provides
    Application provideApplication(@NonNull final Application application) {
        return application;
    }
}