package com.ads.cm.util.http;

import com.ads.cm.constant.SystemConstants;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Author: cyc
 * Date: 12-4-6
 * Time: 上午2:31
 * Description: to write something
 */
public class UrlUtils {
    private static final String URL_PARAM_CONNECT_FLAG = "&";

    public static String getUrl(Map<?, ?> map) {
        if (null == map || map.keySet().size() == 0) {
            return ("");
        }
        StringBuffer url = new StringBuffer();
        Set<?> keys = map.keySet();
        for (Iterator<?> i = keys.iterator(); i.hasNext(); ) {
            String key = String.valueOf(i.next());
            if (map.containsKey(key)) {
                Object val = map.get(key);
                String str = val != null ? val.toString() : "";
                try {
                    str = URLEncoder.encode(str, SystemConstants.DEFAULT_ENCODE);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                url.append(key).append("=").append(str).append(
                        URL_PARAM_CONNECT_FLAG);
            }
        }
        String strURL = "";
        strURL = url.toString();
        if (URL_PARAM_CONNECT_FLAG.equals(""
                + strURL.charAt(strURL.length() - 1))) {
            strURL = strURL.substring(0, strURL.length() - 1);
        }
        return (strURL);
    }
}
