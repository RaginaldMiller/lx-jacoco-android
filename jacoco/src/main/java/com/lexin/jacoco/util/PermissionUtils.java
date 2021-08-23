package com.lexin.jacoco.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;

public class PermissionUtils {
    // Storage Permissions 存储权限
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    /**
     * Checks if the app has permission to write to device storage
     * If the app does not has permission then the user will be prompted to
     * grant permissions
     *
     * 检查App是否有SD卡的写入权限
     * 如果没有，让系统提醒授予
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Context context) {
        // Check if we have write permission
        try {
            int permission = ContextCompat.checkSelfPermission(context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if (permission != PackageManager.PERMISSION_GRANTED) {
                // We don't have permission so prompt the user
//                ContextCompat.requestPermissions(context, PERMISSIONS_STORAGE,
//                        REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
