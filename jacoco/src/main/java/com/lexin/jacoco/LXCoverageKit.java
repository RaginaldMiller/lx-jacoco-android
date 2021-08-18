package com.lexin.jacoco;

import android.content.Context;

import java.util.Timer;
import java.util.TimerTask;

/**
 * SDK初始化
 * @auther ken
 * @date 2021/8/18
 */
public class LXCoverageKit {

    static class MyTimerTask extends TimerTask {
        public void run() {
            JacocoGenUtil.genJacocoData();
        }
    }

    // 初始化
    public static void init(Context context){
//        Thread thread = new Thread(new JacocoThread(context));
//        thread.start();
        Timer timer = new Timer();
        timer.schedule(new MyTimerTask(), 0, 5000);
    }
}
