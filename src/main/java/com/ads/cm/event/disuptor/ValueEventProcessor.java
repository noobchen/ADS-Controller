package com.ads.cm.event.disuptor;

import com.lmax.disruptor.AlertException;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.Sequence;
import com.lmax.disruptor.SequenceBarrier;

import java.util.concurrent.TimeUnit;

/**
 * Author: cyc
 * Date: 12-3-13
 * Time: 下午8:39
 * Description: to write something
 */
public class ValueEventProcessor {
    protected RingBuffer<ValueEvent> ringBuffer;

    private long waitAtSequence = 0;

    public ValueEventProcessor(RingBuffer<ValueEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void send(Object result) {
        ringBuffer.setGatingSequences(new Sequence(-1));

        waitAtSequence = ringBuffer.next();
        ValueEvent ve = ringBuffer.get(waitAtSequence);
        ve.setValue(result);
        ringBuffer.publish(waitAtSequence);
    }

    public ValueEvent waitFor(long timeoutForReturnResult) {
        try {
            SequenceBarrier barrier = ringBuffer.newBarrier();
            long a = barrier.waitFor(waitAtSequence, timeoutForReturnResult, TimeUnit.MILLISECONDS);
            return ringBuffer.get(a);
        } catch (AlertException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ValueEvent waitForBlocking() {
        try {
            SequenceBarrier barrier = ringBuffer.newBarrier();
            long a = barrier.waitFor(waitAtSequence);
            ValueEvent ve = ringBuffer.get(a);
            return ve;
        } catch (AlertException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public long getWaitAtSequence() {
        return waitAtSequence;
    }

    public void setWaitAtSequence(long waitAtSequence) {
        this.waitAtSequence = waitAtSequence;
    }
}
