package com.xxl.example.dagger.di;

import com.xxl.example.dagger.ui.Dagger2Activity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class DaggerActivityBindModule {

    @ContributesAndroidInjector(modules = Dagger2ActivityProvider.class)
    abstract Dagger2Activity bindDagger2Activity();
}
