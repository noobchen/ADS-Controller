package com.ads.cm.repository.cache;

import com.ads.cm.constant.CacheConstants;

/**
 * Author: cyc
 * Date: 11-12-24
 * Time: 下午11:09
 * Description: to write something
 */
public abstract class DefaultSetCacheClosure implements CacheClosure {

    @Override
    public Integer getTime() {
        return null;
    }

    @Override
    public boolean getIfNullSetDefaultValue() {
        return true;
    }

    @Override
    public String getIfNullDefaultValue() {
        return CacheConstants.CACHE_DEFAULT_VALUE;
    }
}
