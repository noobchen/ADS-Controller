package com.ads.cm.util.string;

/**
 * Author: cyc
 * Date: 11-10-21
 * Time: 下午11:20
 * Description: to write something
 */
public interface Transformer<FROM, TO> {
    public TO transform(FROM from);
}
