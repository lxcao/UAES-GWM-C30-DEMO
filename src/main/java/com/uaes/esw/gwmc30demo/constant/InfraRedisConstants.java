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
    public final static String REDIS_VEHICLE_HASH_KEY_BATTERY_SOC = "soc";
    public final static String REDIS_VEHICLE_HASH_KEY_BATTERY_CURRENT = "batteryCurrent";
    public final static String REDIS_VEHICLE_HASH_KEY_BATTERY_VOLTAGE = "batteryVoltage";
    public final static String REDIS_VEHICLE_HASH_KEY_BATTERY_TEMPERATURE = "batteryTemperature";
    public final static String REDIS_VEHICLE_HASH_KEY_BATTERY_BALANCE_STATUS = "batteryBalanceStatus";
    public final static String REDIS_VEHICLE_HASH_KEY_BATTERY_CHARGING_STATUS = "batteryChargingStatus";
    public final static String REDIS_VEHICLE_HASH_KEY_BATTERY_SOC_MAX = "socMax";
    public final static String REDIS_VEHICLE_HASH_KEY_BATTERY_SOC_MIN = "socMin";
    public final static String REDIS_VEHICLE_HASH_KEY_BATTERY_CHARGING_TIME = "batteryChargingTime";
    public final static String REDIS_VEHICLE_HASH_KEY_MAXMILEAGE = "mmg";
    public final static String REDIS_VEHICLE_HASH_KEY_HVPOWER = "hvPower";
    public final static String REDIS_VEHICLE_HASH_KEY_REMAIN_DISTANCE = "remainDistance";
    public final static String REDIS_VEHICLE_HASH_KEY_TM_OPERATION_MODE = "tmOperMod";
    public final static String REDIS_VEHICLE_HASH_KEY_DCDC_OPERATION_MODE = "dcdcOperMod";
    public final static String REDIS_VEHICLE_HASH_KEY_PTC_POWER_CONSUME = "ptcPCnsmptn";
    public final static String REDIS_VEHICLE_HASH_KEY_CMP_POWER_CONSUME = "cmpPCnsmptn";

    public final static String REDIS_BMS_B1_ZSET = "can:B1:zset";
    public final static String REDIS_BMS_B2_ZSET = "can:B2:zset";
    public final static String REDIS_BMS_B3_ZSET = "can:B3:zset";
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
    public final static String REDIS_VCU_294_ZSET = "can:294:zset";
    public final static String REDIS_VCU_FB_ZSET = "can:FB:zset";
    public final static String REDIS_VCU_FA_ZSET = "can:FA:zset";
    public final static String REDIS_VCU_29D_ZSET = "can:29D:zset";
    public final static String REDIS_VCU_77_ZSET = "can:77:zset";
    public final static String REDIS_VCU_78_ZSET = "can:78:zset";
    public final static String REDIS_BMS_410_ZSET = "can:410:zset";
    public final static String REDIS_BMS_411_ZSET = "can:411:zset";
    public final static String REDIS_BMS_412_ZSET = "can:412:zset";
    public final static String REDIS_BMS_413_ZSET = "can:413:zset";
    public final static String REDIS_BMS_414_ZSET = "can:414:zset";
    public final static String REDIS_BMS_415_ZSET = "can:415:zset";
    public final static String REDIS_BMS_416_ZSET = "can:416:zset";
    public final static String REDIS_BMS_417_ZSET = "can:417:zset";
    public final static String REDIS_BMS_418_ZSET = "can:418:zset";
    public final static String REDIS_BMS_420_ZSET = "can:420:zset";
    public final static String REDIS_BMS_421_ZSET = "can:421:zset";
    public final static String REDIS_BMS_422_ZSET = "can:422:zset";
    public final static String REDIS_BMS_423_ZSET = "can:423:zset";
    public final static String REDIS_BMS_424_ZSET = "can:424:zset";
    public final static String REDIS_BMS_425_ZSET = "can:425:zset";
    public final static String REDIS_BMS_426_ZSET = "can:426:zset";
    public final static String REDIS_BMS_427_ZSET = "can:427:zset";
    public final static String REDIS_BMS_428_ZSET = "can:428:zset";
    public final static String REDIS_BMS_430_ZSET = "can:430:zset";
    public final static String REDIS_BMS_431_ZSET = "can:431:zset";
    public final static String REDIS_BMS_432_ZSET = "can:432:zset";
    public final static String REDIS_BMS_433_ZSET = "can:433:zset";
    public final static String REDIS_BMS_434_ZSET = "can:434:zset";
    public final static String REDIS_BMS_435_ZSET = "can:435:zset";
    public final static String REDIS_BMS_436_ZSET = "can:436:zset";
    public final static String REDIS_BMS_437_ZSET = "can:437:zset";
    public final static String REDIS_BMS_440_ZSET = "can:440:zset";
    public final static String REDIS_BMS_442_ZSET = "can:442:zset";
    public final static String REDIS_BMS_443_ZSET = "can:443:zset";
    public final static String REDIS_BMS_444_ZSET = "can:444:zset";
    public final static String REDIS_BMS_445_ZSET = "can:445:zset";
    public final static String REDIS_BMS_446_ZSET = "can:446:zset";
    public final static String REDIS_BMS_447_ZSET = "can:447:zset";
    public final static String REDIS_BMS_449_ZSET = "can:449:zset";
    public final static String REDIS_BMS_452_ZSET = "can:452:zset";
    public final static String REDIS_BMS_454_ZSET = "can:454:zset";

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

    public final static int REDIS_ZSET_INDEX_LAST_ONE = -1;
    public final static int REDIS_ZSET_INDEX_PREVIOUS_ONE = -2;
    public final static int REDIS_ZSET_INDEX_LAST_TEN = -10;
    public final static int REDIS_ZSET_INDEX_LAST_THIRTY = -30;

    public final static String REDIS_ENERGY_SAVING_DRIVING_CYCLE_ZSET = "gwm:c30:es:dc:zset";

    public final static String REDIS_BATTERY_BALANCE_START_POINT_ZSET = "gwm:c30:bb:start:zset";
    public final static String REDIS_BATTERY_BALANCE_STOP_POINT_ZSET = "gwm:c30:bb:stop:zset";

    public final static String REDIS_CURRENT_DRIVING_MODE_ZSET = "gwm:c30:current:dm:zset";
}
