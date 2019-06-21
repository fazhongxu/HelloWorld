package com.example.xxl.module_web;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.xxl.mediator_web.IConstantWeb;

/**
 * @author xxl.
 * @date 2019-06-21.
 */
public class TestFragment extends Fragment {

    public static TestFragment newInstance(Bundle extras) {
        TestFragment testFragment = new TestFragment();
        testFragment.setArguments(extras);
        return testFragment;
    }

    @Autowired(name = IConstantWeb.KEY_URL)
    String url;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        Log.e("aaa", "onCreate: "+url);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
