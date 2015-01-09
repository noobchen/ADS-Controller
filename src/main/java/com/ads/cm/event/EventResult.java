package com.ads.cm.event;

/**
 * Author: cyc
 * Date: 12-3-13
 * Time: 下午8:52
 * Description: to write something
 */
public interface EventResult {

    /**
     * get a Event Result until time out value: timeoutForReturnResult
     *
     * @return
     */
    Object get(int timeoutForReturnResult);

    /**
     * Blocking until get a Event Result
     *
     * @return
     */
    Object getBlockedValue();

    void send(Object eventResult);
}
