package com.ads.cm.repository.cache;

import com.ads.cm.constant.CacheConstants;

/**
 * Author: cyc
 * Date: 12-4-10
 * Time: 上午9:25
 * Description: to write something
 */
public abstract class DefaultHGetAndSetCacheClosure implements CacheClosure {
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
