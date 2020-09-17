package com.example.xxl.helloworld.dagger2;

import android.util.Log;

/**
 * @author xxl.
 * @date 2020/9/17.
 */
public class Dog implements Skill {

    /**
     * 吃东西
     */
    @Override
    public void eat() {
        Log.e("aaa", "eat: 骨头");
    }
}
