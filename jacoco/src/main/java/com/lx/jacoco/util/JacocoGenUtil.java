package com.lx.jacoco.util;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class JacocoGenUtil {
    public static String TAG = "JacocoGenUtil";

    static String currentEcFilePath;

    public static String createEcFile(Context context, Map<String,String> map){
        try {
            String filePath = getFilePath(context, map);
            File file = new File(filePath);
            if(!file.exists()){
                file.createNewFile();
            }
            currentEcFilePath = filePath;
            return filePath;
        }catch (Exception e){
            Log.d(TAG,"覆盖率文件创建失败！");
            return "";
        }
    }

    public static String getFilePath(Context context, Map<String,String> map){
        String deviceId = map.get("deviceId");
        deviceId = deviceId.replaceAll("-","");
        String today = DateUtil.today();
        String versionName = map.get("versionName");
        String versionCode = map.get("versionCode");
        String commitSha = map.get("commitSha");
        String commitCnt = map.get("commitCnt");

//        PackageManager packageManager = context.getPackageManager();
//        String versionName = "";
//        try {
//            PackageInfo packageInfo = packageManager.getPackageInfo("com.fenqile.fenqile", 0);
//             versionName = packageInfo.versionName;
//        }catch (Exception e){
//            //do nothing
//        }

        // File filesDir = context.getFilesDir();

        File cacheDir = context.getCacheDir();
        String path = cacheDir.getAbsolutePath();
        // 是否存在当前版本未上传的文件，上传，删除
        String versionPrefix = deviceId + "-" + versionName + "-" + commitSha;
        File[] files = cacheDir.listFiles();
        for (File file : files) {
            if(file.isFile()){
                String fileName = file.getName();
                if(fileName.startsWith(versionPrefix) && fileName.endsWith(".ec")){
                    UploadUtil.upload(file);
                    try {
                        file.deleteOnExit();
                    }catch (Exception e){

                    }
                }
            }
        }

        String ecFilePath = path + File.separator + deviceId + "-" + versionName + "-" + commitSha + "-" + today + "-jacoco.ec";
        return ecFilePath;
    }
    public static void genJacocoData(String ecFilePath){
        OutputStream out = null;
        File mCoverageFilePath = new File(ecFilePath);
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

            Log.d(TAG,"写入"+ ecFilePath + "完成!");
        } catch (Exception e) {
            Log.e(TAG, "generateEcFile: " + e.getMessage());
            Log.e(TAG, e.toString());
        } finally {
            if (out == null)
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
