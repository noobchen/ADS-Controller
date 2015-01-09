package com.ads.cm.event;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.ads.cm.annotation.Send;
import com.ads.cm.domain.message.DomainMessage;
import com.ads.cm.event.disuptor.DisruptorFactory;
import com.ads.cm.event.disuptor.EventDisruptor;
import com.ads.cm.event.disuptor.EventResultDisruptor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Author: cyc
 * Date: 12-3-19
 * Time: 上午12:23
 * Description: to write something
 */
public class EventFirer {
    private final Logger logger = LoggerFactory.getLogger(EventFirer.class);
    private DisruptorFactory disruptorFactory;
    protected final Map<String, Disruptor> topicDisruptors;

    public EventFirer(DisruptorFactory disruptorFactory) {
        super();
        this.disruptorFactory = disruptorFactory;
        this.topicDisruptors = new ConcurrentHashMap<String, Disruptor>();
    }


    public void fire(DomainMessage domainMessage, String topic) {
        try {
            Disruptor disruptor = topicDisruptors.get(topic);

            if (disruptor == null) {
//                logger.debug("disruptor is null !!!");
                disruptor = disruptorFactory.getDisruptor(topic);
                if (null == disruptor) {
                    logger.warn("not create disruptor for topic:{}.", topic);
                    return;
                }
                topicDisruptors.put(topic, disruptor);
            }

            domainMessage.setResultEvent(new EventResultDisruptor(topic, domainMessage));

            RingBuffer ringBuffer = disruptor.getRingBuffer();
            long sequence = ringBuffer.next();
//            logger.debug("sequence:{}",sequence);
            EventDisruptor eventDisruptor = (EventDisruptor) ringBuffer.get(sequence);
            if (eventDisruptor == null){
//                logger.debug("eventDisruptor is null");
                return;
            }
            eventDisruptor.setTopic(topic);
            eventDisruptor.setDomainMessage(domainMessage);
            ringBuffer.publish(sequence);
        } catch (Exception e) {
            logger.error("fire event:{} error:{} ", domainMessage.getEventSource(), ExceptionUtils.getStackTrace(e));
        }
    }


    public void fire(DomainMessage domainMessage, Send send) {
        String topic = send.value();
        fire(domainMessage, topic);
    }
}
