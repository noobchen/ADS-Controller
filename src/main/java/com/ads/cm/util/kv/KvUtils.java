package com.ads.cm.util.kv;

import com.ads.cm.util.annotation.AnnotationUtils;
import com.ads.cm.util.http.UrlUtils;
import com.ads.cm.util.string.StringConverters;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * Author: cyc
 * Date: 12-4-6
 * Time: 下午2:10
 * Description: to write something
 */
public class KvUtils {
    private static final Logger logger = LoggerFactory.getLogger(KvUtils.class);

    public static String objectToString(Map<String, List<String>> params) {
        return UrlUtils.getUrl(params);
    }

    public static <T> T instanceObject(Map<String, List<String>> params, Class<T> className, boolean defaultInstance) {
        if (null == params) {
            if (defaultInstance) {
                try {
                    return className.newInstance();
                } catch (InstantiationException e) {
                    return null;
                } catch (IllegalAccessException e) {
                    return null;
                }
            } else {
                return null;
            }
        }

        try {
            T t = className.newInstance();

            Field[] fileds = className.getDeclaredFields();

            if (null == fileds || fileds.length == 0) {
                return t;
            }

            if (!params.isEmpty()) {
                for (Map.Entry<String, List<String>> p : params.entrySet()) {
                    String key = p.getKey();
                    List<String> vals = p.getValue();
                    for (String val : vals) {
                        for (Field field : fileds) {
                            String annotationKey = AnnotationUtils.getKey(field);
                            field.setAccessible(true);
                            if (null == annotationKey) {
                                continue;
                            } else {
                                if (StringUtils.equals(key, annotationKey)) {
                                    Object value = StringConverters.getCommonFactory().transform(field.getType()).transform(val);
                                    field.set(t, value);
                                }
                            }
                        }
                    }
                }
            }

            return t;
        } catch (InstantiationException e) {
            logger.error("kv to object error.exception:", e.fillInStackTrace());
        } catch (IllegalAccessException e) {
            logger.error("kv to object error.exception:", e.fillInStackTrace());
        }

        return null;
    }
}
