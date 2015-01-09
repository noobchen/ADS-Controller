package com.ads.cm.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Author: cyc
 * Date: 12-4-12
 * Time: 下午3:31
 * Description: to write something
 */
@Target(TYPE)
@Retention(RUNTIME)
@Documented
public @interface Service {
}
