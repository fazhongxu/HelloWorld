package com.xxl.example;

import android.app.Application;

import androidx.annotation.NonNull;

import com.xxl.example.origin.anotation.ForApplication;

import dagger.Module;
import dagger.Provides;

/**
 * @author xxl.
 * @date 2020/9/18.
 */
@Module
public class MainActivityProvider {

    private Application mApplication;

    @Provides
    User provideUser(@ForApplication Application application) {
        return new User(application);
    }


}