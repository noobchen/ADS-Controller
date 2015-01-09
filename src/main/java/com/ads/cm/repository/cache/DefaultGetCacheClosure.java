package com.ads.cm.repository.cache;

/**
 * Author: cyc
 * Date: 11-12-24
 * Time: 下午10:59
 * Description: to write something
 */
public abstract class DefaultGetCacheClosure implements CacheClosure {

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public Integer getTime() {
        return null;
    }

    @Override
    public boolean getIfNullSetDefaultValue() {
        return false;
    }

    @Override
    public String getIfNullDefaultValue() {
        return null;
    }
}
