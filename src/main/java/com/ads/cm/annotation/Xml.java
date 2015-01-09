package com.ads.cm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author: cyc
 * Date: 12-4-27
 * Time: 下午2:03
 * Description: to write something
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Xml {
    String key();

    String description() default "";
}
