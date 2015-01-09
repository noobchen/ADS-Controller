package com.ads.cm.event.disuptor;

import com.lmax.disruptor.EventHandler;
import com.ads.cm.domain.message.DomainEventHandler;

/**
 * Author: cyc
 * Date: 12-3-13
 * Time: 下午8:46
 * Description: to write something
 */
public class DomainEventHandlerAdapter implements EventHandler<EventDisruptor> {
    private DomainEventHandler handler;

    public DomainEventHandlerAdapter(DomainEventHandler handler) {
        super();
        this.handler = handler;
    }

    public void onEvent(EventDisruptor event, long sequence, boolean endOfBatch) throws Exception {
        handler.onEvent(event, endOfBatch);
    }
}
