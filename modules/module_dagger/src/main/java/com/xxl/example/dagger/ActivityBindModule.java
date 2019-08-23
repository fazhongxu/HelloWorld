package com.xxl.example.dagger;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindModule {
    @ContributesAndroidInjector
    abstract Dagger2Activity contributeDagger2ActivityInjector();
}
