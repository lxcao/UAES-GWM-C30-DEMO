package com.uaes.esw.gwmc30demo.domain.repository.driver;

import com.uaes.esw.gwmc30demo.domain.model.entity.driver.Driver;
import com.uaes.esw.gwmc30demo.infrastructure.redis.RedisHandler;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.uaes.esw.gwmc30demo.constant.DriverConstants.DRIVER_DUMMY_CELL_PHONE;
import static com.uaes.esw.gwmc30demo.constant.DriverConstants.DRIVER_DUMMY_PASSWORD;
import static com.uaes.esw.gwmc30demo.constant.DrivingModeConstants.DRIVING_MODE_NOR;
import static com.uaes.esw.gwmc30demo.constant.InfraRedisConstants.*;
import static com.uaes.esw.gwmc30demo.constant.DrivingModeConstants.*;
import static com.uaes.esw.gwmc30demo.constant.VehicleConstants.GMW_C30_VIN_CODE;
import static com.uaes.esw.gwmc30demo.infrastructure.redis.RedisHandler.getAllMemberFromSET;

public interface IDriverRepository {

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
        String driverHashName = REDIS_DRIVER_HASH_NAME_PREFIX+driver.getCellPhone()+REDIS_DRIVER_HASH_NAME_SUFFIX;
        RedisHandler.hSet(driverHashName,REDIS_DRIVER_HASH_KEY_CELLPHONE, driver.getCellPhone());
        RedisHandler.hSet(driverHashName,REDIS_DRIVER_HASH_KEY_PASSWORD, driver.getPassword());
        RedisHandler.hSet(driverHashName,REDIS_DRIVER_HASH_KEY_VIN, REDIS_VEHICLE_VIN_CODE);
        RedisHandler.hSet(driverHashName,REDIS_DRIVER_HASH_KEY_DEFAULTDM, DRIVING_MODE_NOR);
        RedisHandler.hSet(driverHashName,REDIS_DRIVER_HASH_KEY_CURRENTDM, DRIVING_MODE_NOR);

        String cstDrivingModeHashName = REDIS_DRIVER_CST_DM_HASH_NAME_PREFIX+driver.getCellPhone()+REDIS_DRIVER_CST_DM_HASH_NAME_SUFFIX;
        RedisHandler.hSet(cstDrivingModeHashName,REDIS_DRIVER_CST_DM_HASH_KEY_SP,String.valueOf(CST_MAX_SPEED));
        RedisHandler.hSet(cstDrivingModeHashName,REDIS_DRIVER_CST_DM_HASH_KEY_ER,String.valueOf(CST_ENERGY_RECOVERY));
        RedisHandler.hSet(cstDrivingModeHashName,REDIS_DRIVER_CST_DM_HASH_KEY_PC,String.valueOf(CST_POWER_CORRESPONDING));
        RedisHandler.hSet(cstDrivingModeHashName,REDIS_DRIVER_CST_DM_HASH_KEY_SM,String.valueOf(CST_SMOOTHNESS));
        RedisHandler.hSet(cstDrivingModeHashName,REDIS_DRIVER_CST_DM_HASH_KEY_AP,String.valueOf(CST_ACCESSORY_PERFORMANCE));
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

    static Driver createDummyDriver(){
        Driver dummyDriver = Driver.builder()
                .cellPhone(DRIVER_DUMMY_CELL_PHONE)
                .password(DRIVER_DUMMY_PASSWORD)
                .currentDM(DRIVING_MODE_NOR)
                .defaultDM(DRIVING_MODE_NOR)
                .vin(GMW_C30_VIN_CODE)
                .build();
        return dummyDriver;
    }

    static Set<Driver> getAllRegistedDriver(String vinCode){
        Set<String> registedDriverCellPhone = getAllMemberFromSET(REDIS_DRIVER_REGISTER_SET);
        Set<Driver> driverSet = new HashSet<>();
        registedDriverCellPhone.forEach(id -> driverSet.add(getDriverInfo(id)));
        return driverSet;
    }

}
