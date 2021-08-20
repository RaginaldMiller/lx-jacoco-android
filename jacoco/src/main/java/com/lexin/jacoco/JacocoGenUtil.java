package com.lexin.jacoco;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class JacocoGenUtil {
    //public static String DEFAULT_COVERAGE_FILE_PATH = "/sdcard/testjacoco.ec";
    public static String TAG = "JacocoGenUtil";

    public static String getFilePath(Context context){
        File filesDir = context.getFilesDir();
        String path = filesDir.getAbsolutePath();
        String ecFilePath = path + File.separator + "testjacoco.ec";
        return ecFilePath;
    }
    public static void genJacocoData(Context context){
        OutputStream out = null;
        File mCoverageFilePath = new File(getFilePath(context));
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

            Log.d(TAG,"写入"+ getFilePath(context) + "完成!");
        } catch (Exception e) {
            Log.e(TAG, "generateEcFile: " + e.getMessage());
            Log.e(TAG, e.toString());
        } finally {
            if (out == null)
                return;
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
