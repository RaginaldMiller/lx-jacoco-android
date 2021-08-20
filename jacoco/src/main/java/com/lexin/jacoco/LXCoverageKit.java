package com.lexin.jacoco;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.io.File;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * SDK初始化
 * @auther ken
 * @date 2021/8/18
 */
public class LXCoverageKit {

    static class MyTimerTask extends TimerTask {
        Context context;
        MyTimerTask(Context context){
            this.context = context;
        }
        public void run() {
            JacocoGenUtil.genJacocoData(context);
        }
    }

    // 初始化
    public static void init(Context context){
//        Thread thread = new Thread(new JacocoThread(context));
//        thread.start();
//        context.getApplicationInfo()
        //动态申请SD卡读取权限
        //PermissionUtils.verifyStoragePermissions(context);

        Timer timer = new Timer();
        timer.schedule(new MyTimerTask(context), 0, 5000);
    }
}
