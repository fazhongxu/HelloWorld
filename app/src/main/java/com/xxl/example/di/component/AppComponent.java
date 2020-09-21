package com.xxl.example.di.component;

import android.app.Application;

import com.xxl.example.App;
import com.xxl.example.di.module.AppModule;
import com.xxl.example.di.builder.ActivityBuilderModule;
import com.xxl.example.di.module.DataStoreModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

/**
 * @author xxl.
 * @date 2020/9/18.
 */
@Singleton
@Component(modules = {
        AppModule.class,
        AndroidInjectionModule.class,
        ActivityBuilderModule.class,
        DataStoreModule.class
})
public interface AppComponent extends AndroidInjector<App> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        Builder appModule(AppModule appModule);

        AppComponent build();
    }
}