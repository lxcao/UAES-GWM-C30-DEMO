package com.uaes.esw.gwmc30demo.infrastructure.redis;

import redis.clients.jedis.Jedis;

import java.util.Map;

public interface RedisHandler {

    static Map<String, String>  hgetall(String hashName){
        Jedis jedisClient = RedisFactory.getOneJedisFromPool();
        Map<String, String> hashMap = jedisClient.hgetAll(hashName);
        RedisFactory.releaseOneJedis2Pool(jedisClient);
        return hashMap;

    }

}
