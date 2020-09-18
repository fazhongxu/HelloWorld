package com.xxl.example.di.builder;

import com.xxl.example.MainActivity;
import com.xxl.example.MainActivityProvider;
import com.xxl.example.dagger.di.DaggerActivityBindModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * @author xxl.
 * @date 2020/9/18.
 */
@Module(includes = DaggerActivityBindModule.class)
public abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = MainActivityProvider.class)
    abstract MainActivity bindMainActivity();
}