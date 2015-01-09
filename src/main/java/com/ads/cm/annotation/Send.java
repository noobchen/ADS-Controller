package com.ads.cm.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Author: cyc
 * Date: 12-3-14
 * Time: 上午10:42
 * Description: 用来标识发送主题事件
 */
@Target(METHOD)
@Retention(RUNTIME)
@Documented
public @interface Send {
    /**
     * topic/queue name
     *
     * @return topic/queue name
     * @Send(topicName) ==> @Consume(topicName);
     */
    String value();
}
