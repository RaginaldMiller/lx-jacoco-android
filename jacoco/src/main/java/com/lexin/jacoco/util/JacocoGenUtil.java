package com.lexin.jacoco.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class JacocoGenUtil {
    public static String TAG = "JacocoGenUtil";

    public static String getFilePath(Context context,Map<String,String> map){

        String deviceId = map.get("deviceId");

        String today = DateUtil.today();
        PackageManager packageManager = context.getPackageManager();
        String versionName = "";
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo("com.fenqile.fenqile", 0);
             versionName = packageInfo.versionName;
        }catch (Exception e){
            //do nothing
        }
        File filesDir = context.getFilesDir();
        String path = filesDir.getAbsolutePath();
        String ecFilePath = path + File.separator + versionName + "-" + deviceId + "-" + today + "-testjacoco.ec";
        return ecFilePath;
    }
    public static String genJacocoData(Context context, Map<String,String>  map){
        OutputStream out = null;
        File mCoverageFilePath = new File(getFilePath(context,map));
        try {
            if (mCoverageFilePath.exists()) {
                Log.d(TAG, "JacocoUtils_generateEcFile: 清除旧的ec文件");
                mCoverageFilePath.delete();
            }
            if (!mCoverageFilePath.exists()) {
                mCoverageFilePath.createNewFile();
            }
            out = new FileOutputStream(mCoverageFilePath.getPath(), true);
            Object agent = Class.forName("org.jacoco.agent.rt.RT")
                    .getMethod("getAgent")
                    .invoke(null);
            // api doc https://www.jacoco.org/jacoco/trunk/doc/api/org/jacoco/agent/rt/IAgent.html
            out.write((byte[]) agent.getClass().getMethod("getExecutionData", boolean.class)
                    .invoke(agent, false));

            Log.d(TAG,"写入"+ getFilePath(context,map) + "完成!");
        } catch (Exception e) {
            Log.e(TAG, "generateEcFile: " + e.getMessage());
            Log.e(TAG, e.toString());
        } finally {
            if (out == null)
                return getFilePath(context,map);
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return getFilePath(context,map);
    }

}
