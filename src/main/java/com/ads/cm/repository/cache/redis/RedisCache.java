package com.ads.cm.repository.cache.redis;

import com.ads.cm.repository.cache.Cache;
import com.ads.cm.repository.cache.CacheClosure;
import com.ads.cm.repository.cache.DefaultHGetAndSetCacheClosure;
import com.ads.cm.util.json.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.BitOP;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: cyc
 * Date: 12-3-22
 * Time: 上午9:50
 * Description: redis cache class
 */
public class RedisCache implements Cache {
    private final Logger logger = LoggerFactory.getLogger(RedisCache.class);
    private final String DEFAULT_HOST = "127.0.0.1";
    private final int DEFAULT_PORT = 6379;
    private JedisPool pool;
    private String host = DEFAULT_HOST;
    private int port = DEFAULT_PORT;
    private int timeout = 60000;
    private int maxActive = 100;
    private int maxIdle = 20;
    private int maxWait = 1000;
    private boolean testOnBorrow = false;

    @Override
    public void init() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxActive(maxActive);
        config.setMaxIdle(maxIdle);
        config.setMaxWait(maxWait);
        config.setTestOnBorrow(testOnBorrow);
        pool = new JedisPool(config, host, port, timeout);
    }

    @Override
    public void destroy() {
        pool.destroy();
    }

    @Override
    public boolean exists(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.exists(key);
        } catch (Exception e) {
            logger.error("save key:{} failure.", key);
            return false;
        } finally {
            pool.returnResource(jedis);
        }


    }

    @Override
    public long decrease(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.decr(key);
        } catch (Exception e) {
            logger.error("decrease key:{} failure.", key);
            return 0;
        } finally {
            pool.returnResource(jedis);
        }
    }


    @Override
    public boolean setnx(String key, Object value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            Long result = jedis.setnx(key, JsonUtils.objectToJson(value));

            if (result == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            logger.error("setnx key:{} failure.", key);
            return false;
        } finally {
            pool.returnResource(jedis);
        }
    }


    @Override
    public void del(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.del(key);
        } catch (Exception e) {
            logger.error("redis del key:{} failure.", key);
        } finally {
            pool.returnResource(jedis);
        }
    }

    @Override
    public boolean set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.set(key, value);

            return true;
        } catch (Exception e) {
            logger.error("set key:{} failure.", key);
            return false;
        } finally {
            pool.returnResource(jedis);
        }
    }

    @Override
    public Object set(CacheClosure cacheClosure) {
        Object value = cacheClosure.getValue();
        if (null != value) {
            String json = JsonUtils.objectToJson(value);

            Jedis jedis = null;
            try {
                jedis = pool.getResource();
                if (null == cacheClosure.getTime()) {
                    jedis.set(cacheClosure.getKey(), json);
                } else {
                    jedis.setex(cacheClosure.getKey(), cacheClosure.getTime(), json);
                }
            } catch (Exception e) {
                logger.error("redis put object:{} failure.", cacheClosure);
            } finally {
                pool.returnResource(jedis);
            }
        } else {
            //if json is null,set default value
            if (cacheClosure.getIfNullSetDefaultValue()) {
                Jedis jedis = null;
                try {
                    jedis = pool.getResource();
                    if (null == cacheClosure.getTime()) {
                        jedis.set(cacheClosure.getKey(), cacheClosure.getIfNullDefaultValue());
                    } else {
                        jedis.setex(cacheClosure.getKey(), cacheClosure.getTime(), cacheClosure.getIfNullDefaultValue());
                    }
                } catch (Exception e) {
                    logger.error("redis put object:{} failure.", cacheClosure);
                } finally {
                    pool.returnResource(jedis);
                }
            }
        }
        return value;
    }

    @Override
    public Object get(CacheClosure cacheClosure) {
        Jedis jedis = null;
        String json = null;

        try {
            jedis = pool.getResource();
            json = jedis.get(cacheClosure.getKey());
        } catch (Exception e) {
            logger.error("redis get object:{} failure.", cacheClosure);
        } finally {
            pool.returnResource(jedis);
        }

        if (null == json) {
            return null;
        } else {
            if (cacheClosure.getIfNullSetDefaultValue()) {
                if (json.equals(cacheClosure.getIfNullDefaultValue())) {
                    return null;
                }
            }
            return JsonUtils.jsonToObject(json, cacheClosure.getTypeReference());
        }
    }

    @Override
    public Object get(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.get(key);
        } catch (Exception e) {
            logger.error("redis get key:{}  failure.", key);

        } finally {
            pool.returnResource(jedis);
        }

        return "";
    }

    @Override
    public <T> List<T> mulGet(Class<T> className, boolean ifNullSetDefaultValue, String defaultValue, String... key) {
        Jedis jedis = null;
        List<String> jsonList = null;
        try {
            jedis = pool.getResource();
            jsonList = jedis.mget(key);
        } catch (Exception e) {
            logger.error("redis get object:{} failure.", key);
        } finally {
            pool.returnResource(jedis);
        }

        if (null == jsonList || jsonList.size() == 0) {
            return null;
        } else {
            List<T> result = new ArrayList<T>();

            for (String json : jsonList) {
                if (null == json) {
                    result.add(null);
                } else {
                    if (ifNullSetDefaultValue) {
                        if (json.equals(defaultValue)) {
                            result.add(null);
                            continue;
                        }
                    }
                    T object = JsonUtils.jsonToObject(json, className, false);
                    result.add(object);
                }
            }

            return result;
        }
    }

    @Override
    public List mulGetAndSet(final TypeReference typeReference, boolean ifNullSetDefaultValue, String defaultValue, final String[] key, final CacheClosure[] values) {
        Jedis jedis = null;
        List<String> jsonList = null;
        try {
            jedis = pool.getResource();
            jsonList = jedis.mget(key);
        } catch (Exception e) {
            logger.error("redis get object:{} failure.", key);
        } finally {
            pool.returnResource(jedis);
        }

        List result = new ArrayList();
        if (null == jsonList || jsonList.size() == 0) {           //缓存中没有值
            for (int i = 0; i < key.length; i++) {
                Object value = set(values[i]);       //存入数据库中的值到redis中
                result.add(value);
            }
            return result;
        } else {                                             //缓存中有值
            for (int i = 0; i < jsonList.size(); i++) {
                String json = jsonList.get(i);
                final int index = i;
                if (null == json) {
                    Object value = set(values[i]);
                    result.add(value);
                } else {
                    if (ifNullSetDefaultValue) {
                        if (json.equals(defaultValue)) {
                            result.add(null);
                            continue;
                        }
                    }
                    Object object = JsonUtils.jsonToObject(json, typeReference);
                    result.add(object);
                }
            }

            return result;
        }
    }

    @Override
    public Object getAndSet(CacheClosure cacheClosure) {
        Jedis jedis = null;
        String json = null;
        try {
            jedis = pool.getResource();
            json = jedis.get(cacheClosure.getKey());
        } catch (Exception e) {
            logger.error("redis get object:{} failure.", cacheClosure);
        } finally {
            pool.returnResource(jedis);
        }

        if (null == json) {
            Object value = cacheClosure.getValue();
            logger.info("redis donot have the key,find value:{} by databases ", value);
            if (null != value) {
                json = JsonUtils.objectToJson(value);
            } else if (cacheClosure.getIfNullSetDefaultValue()) {
                json = cacheClosure.getIfNullDefaultValue();
            }

            if (null != json) {
                try {
                    jedis = pool.getResource();
                    if (null == cacheClosure.getTime()) {
                        jedis.set(cacheClosure.getKey(), json);
                    } else {
                        jedis.setex(cacheClosure.getKey(), cacheClosure.getTime(), json);
                    }
                } catch (Exception e) {
                    logger.error("redis set object:{} failure.", cacheClosure);
                } finally {
                    pool.returnResource(jedis);
                }
            }

            return value;
        } else {
            if (cacheClosure.getIfNullSetDefaultValue()) {
                if (json.equals(cacheClosure.getIfNullDefaultValue())) {
                    return null;
                }
            }
            return JsonUtils.jsonToObject(json, cacheClosure.getTypeReference());
        }
    }

    @Override
    public Object hgetAndSet(String key, DefaultHGetAndSetCacheClosure cacheClosure) {
        Jedis jedis = null;
        String json = null;
        try {
            jedis = pool.getResource();
            json = jedis.hget(key, cacheClosure.getKey());
        } catch (Exception e) {
            logger.error("redis get object:{} failure.", cacheClosure);
        } finally {
            pool.returnResource(jedis);
        }

        if (null == json) {
            Object value = cacheClosure.getValue();

            if (null != value) {
                json = JsonUtils.objectToJson(value);
            } else if (cacheClosure.getIfNullSetDefaultValue()) {
                json = cacheClosure.getIfNullDefaultValue();
            }

            if (null != json) {
                try {
                    jedis = pool.getResource();
                    jedis.hset(key, cacheClosure.getKey(), json);
                } catch (Exception e) {
                    logger.error("redis set object:{} failure.", cacheClosure);
                } finally {
                    pool.returnResource(jedis);
                }
            }

            return value;
        } else {
            if (cacheClosure.getIfNullSetDefaultValue()) {
                if (json.equals(cacheClosure.getIfNullDefaultValue())) {
                    return null;
                }
            }
            return JsonUtils.jsonToObject(json, cacheClosure.getTypeReference());
        }
    }

    @Override
    public long increment(String key, long value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.incrBy(key, value);
        } catch (Exception e) {
            logger.error("redis increment key:{} value:{} failure.", key, value);
            return 0;
        } finally {
            pool.returnResource(jedis);
        }
    }

    @Override
    public void setTimeout(String key, int seconds) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.expire(key, seconds);
        } catch (Exception e) {
            logger.error("redis set key:{} timeout:{} failure.", key, seconds);
        } finally {
            pool.returnResource(jedis);
        }
    }

    @Override
    public boolean setBit(String key, long offset, boolean value) {
        Jedis jedis = null;

        try {
            jedis = pool.getResource();

            return jedis.setbit(key, offset, value);
        } catch (Exception e) {
            logger.error("redis setbit key:{} offset:{} value:{} failure.", new Object[]{key, offset, value});

        } finally {
            pool.returnResource(jedis);
        }

        return false;

    }

    @Override
    public long bitCount(String key, long start, long end) {
        Jedis jedis = null;

        try {
            jedis = pool.getResource();

            return jedis.bitcount(key, start, end);
        } catch (Exception e) {
            logger.error("redis bitCount key:{} start:{} end:{} failure.", new Object[]{key, start, end});

        } finally {
            pool.returnResource(jedis);
        }


        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void bitop(BitOP bitOP, String destKey, String... keys) {
        Jedis jedis = null;

        try {
            jedis = pool.getResource();

            jedis.bitop(bitOP, destKey, keys);
        } catch (Exception e) {
            logger.error("redis bitop bitOP:{} destKey:{} keys:{} failure.", new Object[]{bitOP, destKey, keys});

        } finally {
            pool.returnResource(jedis);
        }

    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(int maxWait) {
        this.maxWait = maxWait;
    }

    public JedisPool getPool() {
        return pool;
    }

    public void setPool(JedisPool pool) {
        this.pool = pool;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }


    public static void main(String[] args) {
//        RedisCache cache = new RedisCache();
//        cache.init();
//
//        Jedis jedis = cache.getPool().getResource();
//
//        String values = jedis.get("123123");
//
//        if (StringUtils.isNotEmpty(values)) {
//
//            System.out.println(values);
//        }


    }
}
