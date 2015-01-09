package com.ads.cm.event.disuptor;

import com.ads.cm.domain.message.DomainMessage;


public interface DisruptorListener {
    void action(DomainMessage domainMessage);
}
