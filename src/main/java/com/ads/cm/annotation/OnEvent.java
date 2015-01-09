package com.ads.cm.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Author: cyc
 * Date: 12-3-18
 * Time: 下午9:55
 * Description: 用来标识消费某个主题的事件
 */
@Target(METHOD)
@Retention(RUNTIME)
@Documented
public @interface OnEvent {
    String value();
}
