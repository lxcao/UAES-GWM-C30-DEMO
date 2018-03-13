package com.uaes.esw.gwmc30demo.constant;

public class InfraRedisConstants {
    public final static String REDIS_CONFIG_HOST = "r-uf6a0bbe11aa7764.redis.rds.aliyuncs.com";
    public final static int REDIS_CONFIG_PORT = 6379;
    public final static String REDIS_CONFIG_PASSWORD = "Uaes123456";
    public final static int REDIS_CONFIG_MAX_IDLE = 5;
    public final static int REDIS_CONFIG_MAX_TOTAL = 200;
    public final static int REDIS_CONFIG_MAX_WAIT_MILLIS = 1000*3;
    public final static int REDIS_CONFIG_TIMEOUT = 1000*3;
    public final static boolean REDIS_CONFIG_TEST_ON_BORROW = true;

    public final static String REDIS_VEHICLE_HASH_NAME = "gmw:c30:111111:hash";
    public final static String REDIS_VEHICLE_HASH_KEY_VIN = "vin";
    public final static String REDIS_VEHICLE_HASH_KEY_SOC = "soc";
    public final static String REDIS_VEHICLE_HASH_KEY_MAXMILEAGE = "mmg";

    public final static String REDIS_BMS_B1_ZSET = "can:B1:zset";
}
