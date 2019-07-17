package com.example.xxl.helloworld.dagger2.animal;

import android.util.Log;

/**
 * @author xxl.
 * @date 2019-07-17.
 */
public class Dog implements Animal{

    @Override
    public void eat() {
        Log.e("aaa", "test: dog eat " );
    }
}
