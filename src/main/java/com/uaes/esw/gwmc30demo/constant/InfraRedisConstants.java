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
    public final static String REDIS_VEHICLE_VIN_CODE = "LGWEESK55HE001050";
    public final static String REDIS_VEHICLE_HASH_NAME = "gwm:c30:"+REDIS_VEHICLE_VIN_CODE+":hash";
    public final static String REDIS_VEHICLE_HASH_KEY_VIN = "vin";
    public final static String REDIS_VEHICLE_HASH_KEY_SOC = "soc";
    public final static String REDIS_VEHICLE_HASH_KEY_MAXMILEAGE = "mmg";

    public final static String REDIS_BMS_B1_ZSET = "can:B1:zset";

    public final static int REDIS_VEHICLE_HASH_UPDATE_INTERVAL_MS = 500;

    public final static String REDIS_DRIVER_HASH_NAME_PREFIX = "gwm:c30:driver:";
    public final static String REDIS_DRIVER_HASH_NAME_SUFFIX = ":hash";
    public final static String REDIS_DRIVER_HASH_KEY_CELLPHONE = "cellPhone";
    public final static String REDIS_DRIVER_HASH_KEY_PASSWORD = "password";
    public final static String REDIS_DRIVER_HASH_KEY_VIN = "vin";
    public final static String REDIS_DRIVER_HASH_KEY_CURRENTDM = "currentDM";
    public final static String REDIS_DRIVER_HASH_KEY_DEFAULTDM = "defaultDM";
    public final static String REDIS_DRIVER_REGISTER_SET = "gwm:c30:driver:register:set";
    public final static String REDIS_DRIVER_LOGIN_SET = "gwm:c30:driver:login:set";
    public final static String REDIS_VEHICLE_LIST = "gwm:c30:vehicle:list";

    public final static String REDIS_DRIVER_CST_DM_HASH_NAME_PREFIX = "gwm:c30:driver:";
    public final static String REDIS_DRIVER_CST_DM_HASH_NAME_SUFFIX = ":cstdm:hash";
    public final static String REDIS_DRIVER_CST_DM_HASH_KEY_SP = "sp"; //SPEED
    public final static String REDIS_DRIVER_CST_DM_HASH_KEY_ER = "er"; //ENERGY_RECOVERY
    public final static String REDIS_DRIVER_CST_DM_HASH_KEY_PC = "pc"; //POWER_CORRESPONDING
    public final static String REDIS_DRIVER_CST_DM_HASH_KEY_SM = "sm"; //SMOOTHNESS
    public final static String REDIS_DRIVER_CST_DM_HASH_KEY_AP = "ap"; //ACCESSORY_PERFORMANCE
}
