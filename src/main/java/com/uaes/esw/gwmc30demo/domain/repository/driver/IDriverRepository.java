package com.uaes.esw.gwmc30demo.domain.repository.driver;

import com.uaes.esw.gwmc30demo.domain.model.driver.Driver;
import com.uaes.esw.gwmc30demo.domain.model.vehicle.DrivingMode;
import com.uaes.esw.gwmc30demo.infrastructure.redis.RedisHandler;

import java.util.Map;

import static com.uaes.esw.gwmc30demo.constant.DrivingModeConstants.DRIVING_MODE_NOR;
import static com.uaes.esw.gwmc30demo.constant.InfraRedisConstants.*;

public interface IDriverRepository {
    //是否存在
    boolean isExist(Driver driver);
    //增加
    boolean createDriver(Driver driver);
    //删除
    boolean deleteDriver(Driver driver);
    //更新
    boolean updateDriver(Driver driver);
    //查询
    Driver queryDriver(String cellPhone);

    //得到司机缺省模式
    static DrivingMode getDefaultDrivingMode(Driver driver){
        //TODO: 从司机hash中取到缺省模式
        return null;
    }

    //得到司机当前模式
    static DrivingMode getCurrentDrivingMode(Driver driver){
        //TODO: 从司机hash中取到当前模式
        return null;
    }

    //得到司机的信息
    static Driver getDriverInfo(String cellPhone){
        Map<String, String>  driverInfo = RedisHandler
                .hGetAll(REDIS_DRIVER_HASH_NAME_PREFIX+cellPhone+REDIS_DRIVER_HASH_NAME_SUFFIX);
        Driver driver = Driver.builder().cellPhone(driverInfo.get(REDIS_DRIVER_HASH_KEY_CELLPHONE))
                .password(driverInfo.get(REDIS_DRIVER_HASH_KEY_PASSWORD))
                .vin(driverInfo.get(REDIS_DRIVER_HASH_KEY_VIN))
                .defaultDM(driverInfo.get(REDIS_DRIVER_HASH_KEY_DEFAULTDM))
                .currentDM(driverInfo.get(REDIS_DRIVER_HASH_KEY_CURRENTDM))
                .build();
        return driver;
    }

    static long addRegisterDriver(String cellPhone){
        return RedisHandler.inputValue2SET(REDIS_DRIVER_REGISTER_SET,cellPhone);
    }

    static boolean isRegisterExist(String cellPhone){
        return RedisHandler.isValueInSET(REDIS_DRIVER_REGISTER_SET,cellPhone);
    }

    static void fillDriverInfo(Driver driver){
        String hashName = REDIS_DRIVER_HASH_NAME_PREFIX+driver.getCellPhone()+REDIS_DRIVER_HASH_NAME_SUFFIX;
        RedisHandler.hSet(hashName,REDIS_DRIVER_HASH_KEY_CELLPHONE, driver.getCellPhone());
        RedisHandler.hSet(hashName,REDIS_DRIVER_HASH_KEY_PASSWORD, driver.getPassword());
        RedisHandler.hSet(hashName,REDIS_DRIVER_HASH_KEY_VIN, REDIS_VEHICLE_VIN_CODE);
        RedisHandler.hSet(hashName,REDIS_DRIVER_HASH_KEY_DEFAULTDM, DRIVING_MODE_NOR);
        RedisHandler.hSet(hashName,REDIS_DRIVER_HASH_KEY_CURRENTDM, DRIVING_MODE_NOR);
    }

    static boolean isLoginExist(String cellPhone){
        return RedisHandler.isValueInSET(REDIS_DRIVER_LOGIN_SET,cellPhone);
    }

    static long addLoginDriver(String cellPhone){
        return RedisHandler.inputValue2SET(REDIS_DRIVER_LOGIN_SET,cellPhone);
    }

    static long removeLoginDriver(String cellPhone){
        return RedisHandler.removeValueInSET(REDIS_DRIVER_LOGIN_SET,cellPhone);
    }

}
