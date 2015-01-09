package com.ads.cm.container;

import com.ads.cm.domain.consumer.ConsumerMethodHolder;

import java.util.Collection;

/**
 * Author: cyc
 * Date: 12-3-15
 * Time: 上午10:49
 * Description: to write something
 */
public interface ContainerWrapper {
    Collection lookupConsumer(String topic);

    Collection lookupOnEventConsumer(String topic);

    void registerConsumer(String topic, String className);

    void registerOnEventConsumer(String topic, ConsumerMethodHolder o);

    Object lookupOriginal(String className);

    void registerOriginal(String className, Object o);
}
