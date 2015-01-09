package com.ads.cm.util.string;

import com.ads.cm.util.datetime.DateTimeUtils;
import com.ads.cm.util.order.OrderUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * User: wy
 * Date: 12-7-20
 * Time: 下午4:47
 * To change this template use File | Settings | File Templates.
 */
public class VariableUtils {
    public static String replaceAllVariable(String source, String payOrderId) {
        return replaceAllSequence(replaceAllTime(replaceAllPayOrderId(source, payOrderId)));
    }

    public static String replaceAllPayOrderId(String source, String payOrderId) {
        if (StringUtils.contains(source, "{pay_order_id}")) {
            return StringUtils.replace(source, "{pay_order_id}", payOrderId);
        } else {
            return source;
        }
    }

    public static String replaceAllTime(String source) {
        if (StringUtils.contains(source, "{time:")) {
            int index = StringUtils.indexOf(source, "{time:");
            String tem = StringUtils.substring(source, index + 6);
            int endIndex = StringUtils.indexOf(tem, "}");
            String data = StringUtils.substring(tem, 0, endIndex);
            return replaceAllTime(StringUtils.replace(source, "{time:" + data + "}", DateTimeUtils.getCurrentTime(data)));
        } else {
            return source;
        }
    }

    public static String replaceAllSequence(String source) {
        if (StringUtils.contains(source, "{seq:")) {
            int index = StringUtils.indexOf(source, "{seq:");
            String tem = StringUtils.substring(source, index + 5);
            int endIndex = StringUtils.indexOf(tem, "}");
            String data = StringUtils.substring(tem, 0, endIndex);
            return replaceAllSequence(StringUtils.replace(source, "{seq:" + data + "}", OrderUtils.getOrderId(Integer.parseInt(data))));
        } else {
            return source;
        }
    }

    public static String replaceAllRandom(String source) {
        if (StringUtils.contains(source, "{random:")) {
            int index = StringUtils.indexOf(source, "{random:");
            String tem = StringUtils.substring(source, index + 8);
            int endIndex = StringUtils.indexOf(tem, "}");
            String data = StringUtils.substring(tem, 0, endIndex);
            return replaceAllRandom(StringUtils.replace(source, "{random:" + data + "}", OrderUtils.getRandom(Integer.parseInt(data))));
        } else {
            return source;
        }
    }
}
