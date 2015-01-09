package com.ads.cm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author: cyc
 * Date: 12-4-6
 * Time: 下午2:30
 * Description: to write something
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Kv {
    String key();

    String description() default "";
}
