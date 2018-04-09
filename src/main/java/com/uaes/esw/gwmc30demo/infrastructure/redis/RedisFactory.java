package com.uaes.esw.gwmc30demo.infrastructure.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import static com.uaes.esw.gwmc30demo.constant.InfraRedisConstants.*;

public class RedisFactory {

    private static JedisPool pool;

    public  static JedisPool getPool() {
        if (pool == null) {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxIdle(REDIS_CONFIG_MAX_IDLE);
            config.setMaxTotal(REDIS_CONFIG_MAX_TOTAL);
            config.setMaxWaitMillis(REDIS_CONFIG_MAX_WAIT_MILLIS);
            config.setTestOnBorrow(REDIS_CONFIG_TEST_ON_BORROW);
            String host = REDIS_CONFIG_HOST;
            String password = REDIS_CONFIG_PASSWORD;
            int port = REDIS_CONFIG_PORT;
            int timeout = REDIS_CONFIG_TIMEOUT;
            pool = new JedisPool(config, host, port, timeout, password);
        }
        return pool;
    }

    public static Jedis getOneJedisFromPool() {
        return getPool().getResource();
    }

    public static void releaseOneJedis2Pool(Jedis jedis) {
        jedis.close();
    }

}
