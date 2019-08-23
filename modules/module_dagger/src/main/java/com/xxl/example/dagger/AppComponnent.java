package com.xxl.example.dagger;

import android.app.Application;

import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Component(modules = {AndroidInjectionModule.class, ActivityBindModule.class})
public interface AppComponnent {

    void inject(Application application);
}
