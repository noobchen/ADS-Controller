package com.ads.cm.repository.cache;

/**
 * Author: cyc
 * Date: 11-12-24
 * Time: 下午11:11
 * Description: to write something
 */
public abstract class DefaultSetTimeoutCacheClosure implements CacheClosure {
    @Override
    public boolean getIfNullSetDefaultValue() {
        return false;
    }

    @Override
    public String getIfNullDefaultValue() {
        return null;
    }
}
