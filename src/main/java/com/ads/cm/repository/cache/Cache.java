package com.ads.cm.repository.cache;

import org.codehaus.jackson.type.TypeReference;
import redis.clients.jedis.BitOP;

import java.util.List;

/**
 * Author: cyc
 * Date: 12-3-22
 * Time: 上午9:47
 * Description: to write something
 */
public interface Cache {
    public void init();


    public void destroy();

    public boolean exists(String key);

    public long decrease(String key);

    public boolean setnx(String key, Object value);

    public void del(String key);

    public boolean set(String key,String value);

    public Object set(CacheClosure cacheClosure);

    public Object get(CacheClosure cacheClosure);

    public Object get(String key);

    public <T> List<T> mulGet(Class<T> className, boolean ifNullSetDefaultValue, String defaultValue, String... key);

    public List mulGetAndSet(TypeReference typeReference, boolean ifNullSetDefaultValue, String defaultValue, String[] key, CacheClosure[] values);

    public Object getAndSet(CacheClosure cacheClosure);

    public Object hgetAndSet(String key, DefaultHGetAndSetCacheClosure cacheClosure);

    public long increment(String key, long value);

    public void setTimeout(String key, int seconds);

    public boolean setBit(String key,long offset,boolean value);

    public long bitCount(String key,long start,long end);

    public void bitop(BitOP bitOP,String destKey,String... keys);


}
