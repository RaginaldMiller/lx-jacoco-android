package com.lexin.jacoco.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @auther ken
 * @date 2021/8/20
 */
public class DateUtil {
    public static String today() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String format = sdf.format(new Date());
        System.out.println(format);
        return format;
    }
}
