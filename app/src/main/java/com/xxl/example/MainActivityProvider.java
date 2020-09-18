package com.xxl.example;

import android.app.Application;

import androidx.annotation.NonNull;

import dagger.Module;
import dagger.Provides;

/**
 * @author xxl.
 * @date 2020/9/18.
 */
@Module
public class MainActivityProvider {

    @Provides
    User provideUser() {
        return new User();
    }


}