package com.xxl.example.dagger2.animal;

/**
 * @author xxl.
 * @date 2019-07-17.
 */
public class Test {
    private Animal mAnimal;

    public Test(Animal animal) {
        this.mAnimal = animal;
    }

    public void eat() {
        mAnimal.eat();
    }
}
