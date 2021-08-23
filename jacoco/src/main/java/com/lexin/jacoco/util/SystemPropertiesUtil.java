package com.lexin.jacoco.util;

import java.lang.reflect.Method;

/**
 * @auther ken
 * @date 2021/8/23
 */
public class SystemPropertiesUtil {

    public static String get(String key){
        String value = "";
        Class<?> cls = null;
        try {
            cls = Class.forName("android.os.SystemProperties");
            Method hideMethod = cls.getMethod("get", String.class);
            Object object = cls.newInstance();
            value = (String)hideMethod.invoke(object, key);

        }catch (Exception e){
            // do nothing
        }
        return  key;
    }

    public static String getDeviceSN(){
        String serial = null;
        try {
            Class<?> c =Class.forName("android.os.SystemProperties");
            Method get =c.getMethod("get", String.class);
            serial = (String)get.invoke(c, "ro.serialno");
        } catch (Exception e) {
        }
        return serial;
    }

}
