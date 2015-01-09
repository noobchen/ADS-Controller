package com.ads.cm.event.disuptor;

import com.ads.cm.domain.message.DomainMessage;

/**
 * Author: cyc
 * Date: 12-3-13
 * Time: 下午8:45
 * Description: to write something
 */
public class EventDisruptor {
    protected String topic;

    protected DomainMessage domainMessage;

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public DomainMessage getDomainMessage() {
        return domainMessage;
    }

    public void setDomainMessage(DomainMessage domainMessage) {
        this.domainMessage = domainMessage;
    }
}
