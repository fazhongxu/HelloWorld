package com.example.xxl.helloworld.dagger2;

import com.example.xxl.helloworld.MainActivity;

import dagger.Module;
import dagger.Provides;

/**
 * @author xxl.
 * @date 2020/9/17.
 */
@Module
public class AnimalModule {

    private MainActivity mMainActivity;

    public AnimalModule(MainActivity activity) {
        mMainActivity = activity;
    }

    @ForCat
    @Provides
    Skill provideCat() {
        return new Cat();
    }

    @ForDog
    @Provides
    Skill provideDog() {
        return new Dog();
    }
}
