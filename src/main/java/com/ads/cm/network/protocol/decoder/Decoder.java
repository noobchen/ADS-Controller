package com.ads.cm.network.protocol.decoder;

/**
 * Author: cyc
 * Date: 12-3-17
 * Time: 下午3:35
 * Description: to write something
 */
public interface Decoder<K> {
    public Object decode(K k) throws Exception;
}
