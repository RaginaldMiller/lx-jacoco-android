package com.lexin.jacoco;

import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class JacocoGenUtil {
    public static String DEFAULT_COVERAGE_FILE_PATH = "/sdcard/testjacoco.ec";
    public static String TAG = "JacocoGenUtil";
    public static void genJacocoData(){
        OutputStream out = null;
        File mCoverageFilePath = new File(DEFAULT_COVERAGE_FILE_PATH);
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

            Log.d(TAG,"写入"+ DEFAULT_COVERAGE_FILE_PATH + "完成!");
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
