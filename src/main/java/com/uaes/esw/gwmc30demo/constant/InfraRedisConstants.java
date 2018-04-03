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
    public final static String REDIS_VCU_61_ZSET = "can:61:zset";
    public final static String REDIS_VCU_62_ZSET = "can:62:zset";
    public final static String REDIS_VCU_63_ZSET = "can:63:zset";
    public final static String REDIS_VCU_64_ZSET = "can:64:zset";
    public final static String REDIS_VCU_65_ZSET = "can:65:zset";
    public final static String REDIS_VCU_66_ZSET = "can:66:zset";
    public final static String REDIS_VCU_67_ZSET = "can:67:zset";
    public final static String REDIS_VCU_68_ZSET = "can:68:zset";
    public final static String REDIS_VCU_69_ZSET = "can:69:zset";
    public final static String REDIS_VCU_70_ZSET = "can:70:zset";
    public final static String REDIS_VCU_71_ZSET = "can:71:zset";
    public final static String REDIS_VCU_72_ZSET = "can:72:zset";
    public final static String REDIS_VCU_73_ZSET = "can:73:zset";
    public final static String REDIS_VCU_75_ZSET = "can:75:zset";
    public final static String REDIS_VCU_76_ZSET = "can:76:zset";

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

    public final static String REDIS_WEATHER_01_SET_NAME = "weather:01:set";
    public final static String REDIS_WEATHER_02_SET_NAME = "weather:02:set";
    public final static String REDIS_WEATHER_03_SET_NAME = "weather:03:set";
}
