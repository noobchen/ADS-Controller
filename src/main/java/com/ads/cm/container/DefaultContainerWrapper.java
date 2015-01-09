package com.ads.cm.container;

import com.ads.cm.domain.consumer.ConsumerMethodHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: cyc
 * Date: 12-3-18
 * Time: 下午11:11
 * Description: to write something
 */
public class DefaultContainerWrapper implements ContainerWrapper {
    private final Logger logger = LoggerFactory.getLogger(DefaultContainerWrapper.class);
    private Map<String, Object> objects = new HashMap<String, Object>();
    private Map<String, ArrayList<String>> allConsumers = new HashMap<String, ArrayList<String>>();
    private Map<String, ArrayList<ConsumerMethodHolder>> allOnEventConsumers = new HashMap<String, ArrayList<ConsumerMethodHolder>>();


    @Override
    public Collection lookupConsumer(String topic) {
        return allConsumers.get(topic);
    }

    @Override
    public Collection lookupOnEventConsumer(String topic) {
        return allOnEventConsumers.get(topic);
    }

    @Override
    public void registerConsumer(String topic, String className) {
        ArrayList topicConsumers = allConsumers.get(topic);
        if (null == topicConsumers) {
            topicConsumers = new ArrayList();
        } else {
            if (topicConsumers.contains(className)) {
                topicConsumers.remove(className);
            }
        }
        topicConsumers.add(className);

        allConsumers.put(topic, topicConsumers);
        logger.debug("allConsumers put consumers:{},topic:{}", topic, topicConsumers);
    }

    @Override
    public void registerOnEventConsumer(String topic, ConsumerMethodHolder o) {
        ArrayList<ConsumerMethodHolder> topicOnEventConsumers = allOnEventConsumers.get(topic);
        if (null == topicOnEventConsumers) {
            topicOnEventConsumers = new ArrayList<ConsumerMethodHolder>();
        } else {
            if (topicOnEventConsumers.contains(o)) {
                topicOnEventConsumers.remove(o);
            }
        }
        topicOnEventConsumers.add(o);

        allOnEventConsumers.put(topic, topicOnEventConsumers);
        logger.debug("allOnEventConsumers put topic:{} onEventConsumers:{}.", topic, topicOnEventConsumers);
    }

    @Override
    public Object lookupOriginal(String className) {
        return objects.get(className);
    }

    @Override
    public void registerOriginal(String className, Object o) {
        objects.put(className, o);
    }


}
