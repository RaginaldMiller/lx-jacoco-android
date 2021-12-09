package com.lx.jacoco.util;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * @auther ken
 * @date 2021/12/9
 */
public class MyLifecycleObserver implements LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void onBackground() {
        Log.i("LifecycleObserver", "应用退到后台");
    }
}
