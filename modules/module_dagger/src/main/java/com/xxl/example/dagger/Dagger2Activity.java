package com.xxl.example.dagger;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xxl.example.mediator.dagger.IConstantDagger;

@Route(path = IConstantDagger.DAGGER_DAGGER)
public class Dagger2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger2);
    }
}
