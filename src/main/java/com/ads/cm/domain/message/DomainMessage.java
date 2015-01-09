package com.ads.cm.domain.message;

import com.ads.cm.event.EventResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Author: cyc
 * Date: 12-3-13
 * Time: 下午8:54
 * Description: to write something
 */
public class DomainMessage {
    private final Logger logger = LoggerFactory.getLogger(DomainMessage.class);
    protected Object eventSource;
    protected EventResult resultEvent;
    private int timeoutForReturnResult = 10000; //默认为10秒超时               cyc change to 15S

    public DomainMessage(Object eventSource) {
        super();
        this.eventSource = eventSource;
    }

    public Object getEventSource() {
        return eventSource;
    }

    public void setEventSource(Object eventSource) {
        this.eventSource = eventSource;
    }

    public void setResultEvent(EventResult resultEvent) {
        this.resultEvent = resultEvent;
    }

    public EventResult getResultEvent() {
        return resultEvent;
    }

    /**
     * setup time out(MILLISECONDS) value for get a Event Result
     *
     * @param timeoutForReturnResult MILLISECONDS
     */
    public void setTimeoutForReturnResult(int timeoutForReturnResult) {
        this.timeoutForReturnResult = timeoutForReturnResult;
    }

    /**
     * get a Event Result until time out value
     *
     * @return Event Result
     */
    public Object getEventResult() {
        if (resultEvent == null) {
            logger.error("eventMessage:{} is null.", eventSource.getClass());
            return null;
        } else
            return resultEvent.get(timeoutForReturnResult);
    }

    /**
     * * Blocking until get a Event Result
     *
     * @return
     */
    public Object getBlockEventResult() {
        if (resultEvent == null) {
            logger.error("eventMessage:{} is null.", eventSource.getClass());
            return null;
        } else
            return resultEvent.getBlockedValue();
    }

    public void setEventResult(Object eventResultValue) {
        if (resultEvent != null)
            resultEvent.send(eventResultValue);
        else
            logger.error("eventMessage:{} is null.", eventSource.getClass());
    }
}
