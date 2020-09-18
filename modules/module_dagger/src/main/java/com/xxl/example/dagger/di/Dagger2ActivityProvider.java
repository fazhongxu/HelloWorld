package com.xxl.example.dagger.di;

import androidx.annotation.NonNull;

import com.xxl.example.dagger.data.repository.DaggerRepository;
import com.xxl.example.dagger.ui.Dagger2ActivityViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * @author xxl.
 * @date 2020/9/18.
 */
@Module
public class Dagger2ActivityProvider {

    @Provides
    Dagger2ActivityViewModel provideDagger2ActivityViewModel(@NonNull DaggerRepository repository) {
        return new Dagger2ActivityViewModel(repository);
    }
}