package com.xxl.example.di.builder;

import com.xxl.example.MainActivity;
import com.xxl.example.MainActivityProvider;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * @author xxl.
 * @date 2020/9/18.
 */
@Module
public abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = MainActivityProvider.class)
    abstract MainActivity bindMainActivity();
}