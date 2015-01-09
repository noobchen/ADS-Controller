package com.ads.cm.util.event;

import com.ads.cm.annotation.Send;
import com.ads.cm.domain.message.DomainMessage;
import com.ads.cm.event.EventFirer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Author: cyc
 * Date: 12-3-19
 * Time: 下午4:37
 * Description: to write something
 */
public class EventUtils {
    private static final Logger logger = LoggerFactory.getLogger(EventUtils.class);
    private static EventFirer eventFirer;
    private static int timeoutForReturnResult = 10000;  //默认10秒超时

    public static void fireEvent(DomainMessage message, Send send) {
        message.setTimeoutForReturnResult(timeoutForReturnResult);
        eventFirer.fire(message, send);
    }

    public static void fireEvent(DomainMessage message, String topic) {
//        logger.debug("EventUtils.fireEvent:{} topic:{}",message,topic);
        message.setTimeoutForReturnResult(timeoutForReturnResult);
        eventFirer.fire(message, topic);
    }

    public void setEventFirer(EventFirer eventFirer) {
        EventUtils.eventFirer = eventFirer;
    }

    public void setTimeoutForReturnResult(int timeoutForReturnResult) {
        EventUtils.timeoutForReturnResult = timeoutForReturnResult;
    }
}
