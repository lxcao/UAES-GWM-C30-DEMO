package com.uaes.esw.gwmc30demo.infrastructure.redis;

import redis.clients.jedis.Jedis;

import java.util.Map;
import java.util.Set;


public interface RedisHandler {

    static Map<String, String>  hGetAll(String hashName){
        Jedis jedisClient = RedisFactory.getOneJedisFromPool();
        Map<String, String> hashMap = jedisClient.hgetAll(hashName);
        RedisFactory.releaseOneJedis2Pool(jedisClient);
        return hashMap;
    }

    static Set<String> zRangeByPosition(String zsetName, int start, int end){
        Jedis jedisClient = RedisFactory.getOneJedisFromPool();
        Set<String> setResult = jedisClient.zrange(zsetName,start,end);
        RedisFactory.releaseOneJedis2Pool(jedisClient);
        return setResult;
    }

    static String getLastStringFromZset(String zsetName){
        Jedis jedisClient = RedisFactory.getOneJedisFromPool();
        Set<String> setResult = jedisClient.zrange(zsetName,-1,-1);
        String lastString = null;
        for (String result : setResult) {
            lastString = result;
        }
        RedisFactory.releaseOneJedis2Pool(jedisClient);
        return lastString;
    }

    static void setValue2HashField(String key, String field, String value){
        Jedis jedisClient = RedisFactory.getOneJedisFromPool();
        jedisClient.hset(key, field, value);
        RedisFactory.releaseOneJedis2Pool(jedisClient);
    }

}
