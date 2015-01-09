package com.ads.cm.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Author: cyc
 * Date: 12-3-19
 * Time: 上午10:35
 * Description: 组件注解，用来标识拥有@OnEvent事件的类
 */
@Target(TYPE)
@Retention(RUNTIME)
@Documented
public @interface Component {
}
