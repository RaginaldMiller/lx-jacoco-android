package com.lx.jacoco;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.ProcessLifecycleOwner;

import com.lx.jacoco.util.JacocoGenUtil;
import com.lx.jacoco.util.MyLifecycleObserver;
import com.lx.jacoco.util.UploadUtil;

import java.io.File;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * LXCoverageKit
 * @auther ken
 * @date 2021/8/18
 */
public class LXCoverageKit {
    public static final String TAG = "LXCoverageKit";
    static class MyTimerTask extends TimerTask {
        String ecFilePath;
        MyTimerTask(String ecFilePath){
            this.ecFilePath = ecFilePath;
        }
        public void run() {

            JacocoGenUtil.genJacocoData(ecFilePath);
            boolean upload = UploadUtil.upload(new File(ecFilePath));
            if(upload){
                Log.d(TAG,"覆盖率文件上传成功！");
            }else{
                Log.d(TAG,"覆盖率文件上传失败！");
            }
        }
    }
    /**
     * 初始化
     * @param context
     * @param map  一些特殊信息context无法获取的或者需要额外权限的
     */
    public static void init(Context context,Map<String,String> map){
        // 每次启动生成一个唯一的文件
//        String ecFilePath = JacocoGenUtil.createEcFile(context, map);
//        if(ecFilePath==""){
//            return;
//        }
//        Timer timer = new Timer();
//        timer.schedule(new MyTimerTask(ecFilePath), 0, 5000);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(new MyLifecycleObserver());

    }
}
