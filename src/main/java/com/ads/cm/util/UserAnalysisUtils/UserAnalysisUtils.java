package com.ads.cm.util.UserAnalysisUtils;

import com.ads.cm.constant.CacheConstants;
import com.ads.cm.repository.cache.Cache;
import com.ads.cm.util.datetime.DateTimeUtils;
import redis.clients.jedis.BitOP;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-5-9
 * Time: 上午9:41
 * To change this template use File | Settings | File Templates.
 */
public class UserAnalysisUtils {

    public static long getNewUserByAppkey(Cache cache, String appKey, String date) {
        String key = CacheConstants.CACHE_NEW__ + appKey + CacheConstants.CACHE_KEY_SEPARATOR + date;

        return cache.bitCount(key, 0, -1);
    }

    public static long getNewUserByAppkeyGroupByChannel(Cache cache, String appKey, String appChannel, String date) {
        String appChannelNewUserKey = CacheConstants.CACHE_NEW__ + appKey + CacheConstants.CACHE_KEY_SEPARATOR + appChannel + CacheConstants.CACHE_KEY_SEPARATOR + date;

        return cache.bitCount(appChannelNewUserKey, 0, -1);
    }

    public static long getActiveUserByAppkey(Cache cache, String appKey, String date) {
        String key = CacheConstants.CACHE_ACTIVE_ + appKey + CacheConstants.CACHE_KEY_SEPARATOR + date;

        return cache.bitCount(key, 0, -1);
    }

    public static long getRetentionUserByAppkey(Cache cache, String appKey, String newDate, String activeDate) {

        String destKey = null;

        String newKey = CacheConstants.CACHE_NEW__ + appKey + CacheConstants.CACHE_KEY_SEPARATOR + newDate;
        String activeKey = CacheConstants.CACHE_ACTIVE_ + appKey + CacheConstants.CACHE_KEY_SEPARATOR + activeDate;

        String[] keys = new String[]{newKey, activeKey};

        cache.bitop(BitOP.AND, destKey, keys);

        return cache.bitCount(destKey, 0, -1);
    }
}
