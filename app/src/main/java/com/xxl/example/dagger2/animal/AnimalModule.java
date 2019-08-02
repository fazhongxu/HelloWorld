package com.xxl.example.dagger2.animal;

import dagger.Module;
import dagger.Provides;

/**
 * @author xxl.
 * @date 2019-07-17.
 */
@Module
public class AnimalModule {
    @Provides
    @ForDog
    Animal provideDog() {
        return new Dog();
    }

    @Provides
    @ForCat
    Animal provideCat() {
        return new Cat();
    }

    @Provides
    Test provideTest(@ForCat Animal animal) {
        return new Test(animal);
    }
}
