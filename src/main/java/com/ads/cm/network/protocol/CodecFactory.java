package com.ads.cm.network.protocol;

/**
 * Author: cyc
 * Date: 12-3-12
 * Time: 下午3:01
 * Description: to write something
 */
public interface CodecFactory {
    public Object decode(Object o) throws Exception;

    public Object encode(Object o) throws Exception;
}
