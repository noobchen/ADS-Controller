package com.ads.cm.util.datetime;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Author: cyc
 * Date: 12-3-21
 * Time: 下午3:23
 * Description: to write something
 */
public class DateTimeUtils {
    public static int getCurrentHour() {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    }

    public static String getCurrentTime(String format) {
        return new SimpleDateFormat(format).format(Calendar.getInstance().getTime());
    }

    public static String getCurrentDay() {
        return new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
    }

    public static String getCurrentMonth() {
        return new SimpleDateFormat("yyyyMM").format(Calendar.getInstance().getTime());
    }

    public static String getOrderTime() {
        return new SimpleDateFormat("yyMMddHHmmss").format(Calendar.getInstance().getTime());
    }

    public static String getXJPayOrderRequestTime() {
        return new SimpleDateFormat("yyMMddHHmmss").format(Calendar.getInstance().getTime());
    }

    public static String getNetGamePayPayOrderRequestTime() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
    }
}
