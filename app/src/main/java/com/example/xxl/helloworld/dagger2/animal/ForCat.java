package com.example.xxl.helloworld.dagger2.animal;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * @author xxl.
 * @date 2019-07-17.
 */
@Qualifier
@Retention(RetentionPolicy.SOURCE)
public @interface ForCat {
}
