package com.xxl.example;

import com.xxl.example.di.component .DaggerAppComponent;
import com.xxl.example.origin.BaseApp;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

/**
 * @author xxl.
 * @date 2019-07-17.
 */
public class App extends BaseApp {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * Implementations should return an {@link AndroidInjector} for the concrete {@link
     * DaggerApplication}. Typically, that injector is a {@link Component}.
     */
    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }

}
