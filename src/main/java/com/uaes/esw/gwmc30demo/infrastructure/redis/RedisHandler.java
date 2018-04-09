package com.uaes.esw.gwmc30demo.infrastructure.redis;

import redis.clients.jedis.Jedis;

import java.util.Map;
import java.util.Set;

import static com.uaes.esw.gwmc30demo.constant.InfraRedisConstants.REDIS_ZSET_INDEX_LAST_ONE;
import static com.uaes.esw.gwmc30demo.constant.InfraRedisConstants.REDIS_ZSET_INDEX_PREVIOUS_ONE;


public interface RedisHandler {

    static Map<String, String>  hGetAll(String hashName){
        Jedis jedisClient = RedisFactory.getOneJedisFromPool();
        Map<String, String> hashMap = jedisClient.hgetAll(hashName);
        RedisFactory.releaseOneJedis2Pool(jedisClient);
        return hashMap;
    }

    static long hSet(String hashName, String field, String value){
        Jedis jedisClient = RedisFactory.getOneJedisFromPool();
        long result = jedisClient.hset(hashName, field, value);
        RedisFactory.releaseOneJedis2Pool(jedisClient);
        return result;
    }

    static Set<String> zRangeByScore(String zsetName, long start, long end){
        Jedis jedisClient = RedisFactory.getOneJedisFromPool();
        Set<String> setResult = jedisClient.zrangeByScore(zsetName, start, end);
        RedisFactory.releaseOneJedis2Pool(jedisClient);
        return setResult;
    }

    static Set<String> zRangeByPosition(String zsetName, int start, int end){
        Jedis jedisClient = RedisFactory.getOneJedisFromPool();
        Set<String> setResult = jedisClient.zrange(zsetName,start,end);
        RedisFactory.releaseOneJedis2Pool(jedisClient);
        return setResult;
    }

    static String getOneStringFromZsetByIndex(String zsetName, int index){
        Jedis jedisClient = RedisFactory.getOneJedisFromPool();
        Set<String> setResult = jedisClient.zrange(zsetName,index,index);
        String lastString = null;
        for (String result : setResult) {
            lastString = result;
        }
        RedisFactory.releaseOneJedis2Pool(jedisClient);
        return lastString;
    }

    static String getLastOneStringFromZset(String zsetName){
        return getOneStringFromZsetByIndex(zsetName, REDIS_ZSET_INDEX_LAST_ONE);
    }

    static String getPreviousOneStringFromZset(String zsetName){
        return getOneStringFromZsetByIndex(zsetName, REDIS_ZSET_INDEX_PREVIOUS_ONE);
    }

    static long setValue2HashField(String key, String field, String value){
        Jedis jedisClient = RedisFactory.getOneJedisFromPool();
        long result = jedisClient.hset(key, field, value);
        RedisFactory.releaseOneJedis2Pool(jedisClient);
        return result;
    }

    static long inputValue2SET(String setName, String member){
        Jedis jedisClient = RedisFactory.getOneJedisFromPool();
        long result = jedisClient.sadd(setName, member);
        RedisFactory.releaseOneJedis2Pool(jedisClient);
        return result;
    }

    static boolean isValueInSET(String setName, String member){
        Jedis jedisClient = RedisFactory.getOneJedisFromPool();
        boolean result = jedisClient.sismember(setName, member);
        RedisFactory.releaseOneJedis2Pool(jedisClient);
        return result;
    }

    static long removeValueInSET(String setName, String member){
        Jedis jedisClient = RedisFactory.getOneJedisFromPool();
        long result = jedisClient.srem(setName,member);
        RedisFactory.releaseOneJedis2Pool(jedisClient);
        return result;
    }

    static long inputValue2ZSET(String zsetName, double score,  String member){
        Jedis jedisClient = RedisFactory.getOneJedisFromPool();
        long result = jedisClient.zadd(zsetName,score,member);
        RedisFactory.releaseOneJedis2Pool(jedisClient);
        return result;
    }

}
