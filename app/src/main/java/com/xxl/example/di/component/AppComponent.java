package com.xxl.example.di.component;

import android.app.Application;

import com.xxl.example.App;
import com.xxl.example.di.module.AppModule;
import com.xxl.example.di.builder.ActivityBuilderModule;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

/**
 * @author xxl.
 * @date 2020/9/18.
 */
@Component(modules = {
        AppModule.class,
        AndroidInjectionModule.class,
        ActivityBuilderModule.class
})
public interface AppComponent extends AndroidInjector<App> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}