package com.ads.cm.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Author: cyc
 * Date: 12-3-13
 * Time: 下午10:44
 * Description: Consumer of the producer annotated with @sender(topic) of the method;
 * <p/>
 * * Topic/queue(1:N or 1:1):
 *
 * @Sender(topicName) ==> @Consume(topicName);
 * <p/>
 * if there are many consumers, execution order will be
 * alphabetical list by Name of @Consume method.
 * <p/>
 * Domain Model producer /Consumer:
 * <p/>
 * 1. annotate the method with @Send("mytopic") of the producer classes;
 * * @Send("mytopic")
 * public DomainMessage myMethod() {
 * DomainMessage em = new DomainMessage(this.name);
 * return em;
 * }
 * <p/>
 * 2. the "mytopic" value in @Send("mytopic") is equals to the "mytopic" value
 * in @Consumer("mytopic");
 * <p/>
 * 3. annotate the consumer classes with @Consumer("mytopic");
 * @Consumer("mychannel")
 */

@Target(TYPE)
@Retention(RUNTIME)
@Documented
public @interface Consumer {
    /**
     * topic name
     *
     * @return topic name
     * @Send(topicName) ==> @Consumer(topicName);
     */
    String value();
}
