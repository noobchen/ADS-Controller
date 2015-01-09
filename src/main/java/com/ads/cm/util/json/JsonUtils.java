package com.ads.cm.util.json;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Author: cyc
 * Date: 12-3-18
 * Time: 下午3:24
 * Description: to write something
 */
public class JsonUtils {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.getDeserializationConfig().set(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * object transform json string
     *
     * @param o object
     * @return json string
     */
    public static String objectToJson(Object o) {
        try {
            return objectMapper.writeValueAsString(o);
        } catch (IOException e) {
            logger.error("object:{} to json error:{}.", o, ExceptionUtils.getStackTrace(e));
            return null;
        }
    }

    /**
     * json string transform object
     *
     * @param json
     * @param className
     * @param defaultInstance when json string is empty,whether build classes instance,true:build;false:not build
     * @param <T>
     * @return
     */
    public static <T> T jsonToObject(String json, Class<T> className, boolean defaultInstance) {
        if (StringUtils.isEmpty(json)) {
            if (defaultInstance) {
                try {
                    return className.newInstance();
                } catch (Exception e) {
                    logger.error("instance class:{} error:{}.", className, ExceptionUtils.getStackTrace(e));
                    return null;
                }
            } else {
                return null;
            }
        } else {
            try {
                return objectMapper.readValue(json, className);
            } catch (IOException e) {
                logger.error("json:{} to object error:{}.", json, ExceptionUtils.getStackTrace(e));
                return null;
            }
        }
    }

    public static <T> T jsonToObject(String json, TypeReference<T> typeReference) {
        if (StringUtils.isEmpty(json)) {
            return null;
        } else {
            try {
                return objectMapper.readValue(json, typeReference);
            } catch (IOException e) {
                logger.error("json:{} to object error:{}.", json, ExceptionUtils.getStackTrace(e));
                return null;
            }
        }
    }
}
