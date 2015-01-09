package com.ads.cm.util.string;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: cyc
 * Date: 11-10-21
 * Time: 下午11:22
 * Description: to write something
 */
public class DefaultStringConverterFactory implements Transformer<Class<?>, StringConverter> {
    @Override
    public StringConverter transform(Class<?> aClass) {
        return converters.get(aClass);
    }


    private Map<Class<?>, StringConverter> converters =
            new HashMap<Class<?>, StringConverter>();


    public void setConverters(Map<Class<?>, StringConverter> converters) {
        this.converters.clear();

        for (Map.Entry<Class<?>, StringConverter> entry : converters.entrySet()) {
            if (null != entry.getValue()) {
                this.converters.put(entry.getKey(), entry.getValue());
            }
        }
    }

    public DefaultStringConverterFactory setConverter(Class<?> cls, StringConverter converter) {
        this.converters.put(cls, converter);
        return this;
    }
}
