package com.lexin.jacoco;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.lexin.jacoco.util.JacocoGenUtil;
import com.lexin.jacoco.util.UploadUtil;

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
        Context context;
        Map<String,String> map;
        MyTimerTask(Context context, Map<String,String> map){
            this.context = context;
            this.map = map;
        }
        public void run() {
            String filePath = JacocoGenUtil.genJacocoData(context, map);
            boolean upload = UploadUtil.upload(new File(filePath));
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
        Timer timer = new Timer();
        timer.schedule(new MyTimerTask(context,map), 0, 5000);
    }
}
