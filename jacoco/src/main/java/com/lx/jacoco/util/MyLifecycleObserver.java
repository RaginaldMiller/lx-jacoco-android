package com.lx.jacoco.util;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import java.io.File;

/**
 * @auther ken
 * @date 2021/12/9
 */
public class MyLifecycleObserver implements LifecycleObserver {
    public static final String TAG = "MyLifecycleObserver";
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void onBackground() {
        String ecFilePath = JacocoGenUtil.currentEcFilePath;
        JacocoGenUtil.genJacocoData(ecFilePath);
        boolean upload = UploadUtil.upload(new File(ecFilePath));
        if(upload){
            Log.d(TAG,"覆盖率文件上传成功！");
        }else{
            Log.d(TAG,"覆盖率文件上传失败！");
        }
    }


}
