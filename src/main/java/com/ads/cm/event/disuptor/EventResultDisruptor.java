package com.ads.cm.event.disuptor;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SingleThreadedClaimStrategy;
import com.ads.cm.domain.message.DomainMessage;
import com.ads.cm.event.EventResult;

/**
 * Author: cyc
 * Date: 12-3-13
 * Time: 下午8:41
 * Description: to write something
 */
public class EventResultDisruptor implements EventResult {
    protected String topic;
    protected DomainMessage domainMessage;
    protected boolean over;
    protected Object result;
    protected ValueEventProcessor valueEventProcessor;

    public EventResultDisruptor(String topic, DomainMessage domainMessage) {
        super();
        this.topic = topic;
        this.domainMessage = domainMessage;
        RingBuffer ringBuffer = new RingBuffer<ValueEvent>(ValueEvent.EVENT_FACTORY, new SingleThreadedClaimStrategy(8), new BlockingWaitStrategy());
        this.valueEventProcessor = new ValueEventProcessor(ringBuffer);
    }

    /**
     * send event result
     */
    public void send(Object result) {
        valueEventProcessor.send(result);
    }

    public Object get(int timeoutForReturnResult) {
        if (over)
            return result;
        ValueEvent ve = valueEventProcessor.waitFor(timeoutForReturnResult);
        if (ve != null)
            result = ve.getValue();
        return result;
    }

    public Object getBlockedValue() {
        if (over)
            return result;
        ValueEvent ve = valueEventProcessor.waitForBlocking();
        if (ve != null)
            result = ve.getValue();
        return result;
    }

    public String getTopic() {
        return topic;
    }

    public void setOver(boolean over) {
        this.over = over;
    }
}
