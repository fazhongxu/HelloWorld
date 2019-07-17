package com.example.xxl.helloworld.dagger2.animal;

import com.example.xxl.helloworld.MainActivity;

import dagger.Component;

/**
 * @author xxl.
 * @date 2019-07-17.
 */
@Component(modules = AnimalModule.class)
public interface AnimalComponent {
    void inject(MainActivity mainActivity);
}
