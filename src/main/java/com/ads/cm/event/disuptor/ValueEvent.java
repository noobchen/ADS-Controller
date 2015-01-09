package com.ads.cm.event.disuptor;

import com.lmax.disruptor.EventFactory;

/**
 * Author: cyc
 * Date: 12-3-13
 * Time: 下午8:40
 * Description: to write something
 */
public class ValueEvent {
    private Object value;

    public Object getValue() {
        return value;
    }

    public void setValue(final Object value) {
        this.value = value;
    }

    public final static EventFactory<ValueEvent> EVENT_FACTORY = new EventFactory<ValueEvent>() {
        public ValueEvent newInstance() {
            return new ValueEvent();
        }
    };
}
