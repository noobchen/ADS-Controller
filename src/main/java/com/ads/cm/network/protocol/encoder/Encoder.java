package com.ads.cm.network.protocol.encoder;

/**
 * Author: cyc
 * Date: 12-3-17
 * Time: 下午3:35
 * Description: to write something
 */
public interface Encoder<T> {
    public Object encode(T t) throws Exception;
}
