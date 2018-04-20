package com.uaes.esw.gwmc30demo.domain.repository.drivingMode;

import com.uaes.esw.gwmc30demo.domain.model.entity.driver.Driver;
import com.uaes.esw.gwmc30demo.domain.model.entity.vehicle.DrivingMode;
import com.uaes.esw.gwmc30demo.domain.model.entity.vehicle.DrivingModeConfigure;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.uaes.esw.gwmc30demo.constant.DrivingModeConstants.*;
import static com.uaes.esw.gwmc30demo.constant.DrivingModeConstants.DRIVING_MODE_CST;
import static com.uaes.esw.gwmc30demo.constant.InfraRedisConstants.*;
import static com.uaes.esw.gwmc30demo.constant.InfraRedisConstants.REDIS_DRIVER_CST_DM_HASH_KEY_PC;
import static com.uaes.esw.gwmc30demo.constant.InfraRedisConstants.REDIS_DRIVER_CST_DM_HASH_KEY_SM;
import static com.uaes.esw.gwmc30demo.infrastructure.redis.RedisHandler.hGetAll;
import static com.uaes.esw.gwmc30demo.infrastructure.redis.RedisHandler.setValue2HashField;
import static com.uaes.esw.gwmc30demo.infrastructure.utils.LoggerUtils.drivingModelLogInfo;

public interface IDrivingModeRepository {

    //得到所有车辆模式
    static List<DrivingMode> getAllDrivingMode(Driver driver){
        List<DrivingMode> drivingModeList = new ArrayList<>();
        drivingModeList.add(getVehicleEEMDrivingMode());
        drivingModeList.add(getVehicleECODrivingMode());
        drivingModeList.add(getVehicleNORDrivingMode());
        drivingModeList.add(getVehicleEPMDrivingMode());
        drivingModeList.add(getVehiclePOWDrivingMode());
        drivingModeList.add(getCustomerDM(driver));
        return drivingModeList;
    }

    //得到车辆模式
    static DrivingMode getDrivingMode(String modeType, Driver driver){
        if(DRIVING_MODE_EEM.equals(modeType))
            return getVehicleEEMDrivingMode();
        else if(DRIVING_MODE_ECO.equals(modeType))
            return getVehicleECODrivingMode();
        else if(DRIVING_MODE_NOR.equals(modeType))
            return getVehicleNORDrivingMode();
        else if(DRIVING_MODE_EPM.equals(modeType))
            return getVehicleEPMDrivingMode();
        else if(DRIVING_MODE_POW.equals(modeType))
            return getVehiclePOWDrivingMode();
        else if(DRIVING_MODE_CST.equals(modeType)){
            return getCustomerDM(driver);
        }

        else
            return getVehicleNORDrivingMode();
    }

    //生成ENHANCED_ECONOMY_MODE
    static DrivingMode getVehicleEEMDrivingMode(){
        DrivingModeConfigure dmc = DrivingModeConfigure.builder()
                .accessoryPerformance(EEM_ACCESSORY_PERFORMANCE)
                .energyRecovery(EEM_ENERGY_RECOVERY)
                .maxSpeed(EEM_MAX_SPEED)
                .powerCorresponding(EEM_POWER_CORRESPONDING)
                .smoothness(EEM_SMOOTHNESS)
                .build();
        DrivingMode dm = DrivingMode.builder().drivingModeConfigure(dmc)
                .drivingModeType(DRIVING_MODE_EEM)
                .build();
        return dm;
    }

    //生成ECONOMY_MODE
    static DrivingMode getVehicleECODrivingMode(){
        DrivingModeConfigure dmc = DrivingModeConfigure.builder()
                .accessoryPerformance(ECO_ACCESSORY_PERFORMANCE)
                .energyRecovery(ECO_ENERGY_RECOVERY)
                .maxSpeed(ECO_MAX_SPEED)
                .powerCorresponding(ECO_POWER_CORRESPONDING)
                .smoothness(ECO_SMOOTHNESS)
                .build();
        DrivingMode dm = DrivingMode.builder().drivingModeConfigure(dmc)
                .drivingModeType(DRIVING_MODE_ECO)
                .build();
        return dm;
    }

    //生成NORMAL_MODE
    static DrivingMode getVehicleNORDrivingMode(){
        DrivingModeConfigure dmc = DrivingModeConfigure.builder()
                .accessoryPerformance(NOR_ACCESSORY_PERFORMANCE)
                .energyRecovery(NOR_ENERGY_RECOVERY)
                .maxSpeed(NOR_MAX_SPEED)
                .powerCorresponding(NOR_POWER_CORRESPONDING)
                .smoothness(NOR_SMOOTHNESS)
                .build();
        DrivingMode dm = DrivingMode.builder().drivingModeConfigure(dmc)
                .drivingModeType(DRIVING_MODE_NOR)
                .build();
        return dm;
    }

    //生成ENHANCED_POWER_MODE
    static DrivingMode getVehicleEPMDrivingMode(){
        DrivingModeConfigure dmc = DrivingModeConfigure.builder()
                .accessoryPerformance(EPM_ACCESSORY_PERFORMANCE)
                .energyRecovery(EPM_ENERGY_RECOVERY)
                .maxSpeed(EPM_MAX_SPEED)
                .powerCorresponding(EPM_POWER_CORRESPONDING)
                .smoothness(EPM_SMOOTHNESS)
                .build();
        DrivingMode dm = DrivingMode.builder().drivingModeConfigure(dmc)
                .drivingModeType(DRIVING_MODE_EPM)
                .build();
        return dm;
    }

    //生成POWER_MODE
    static DrivingMode getVehiclePOWDrivingMode(){
        DrivingModeConfigure dmc = DrivingModeConfigure.builder()
                .accessoryPerformance(POW_ACCESSORY_PERFORMANCE)
                .energyRecovery(POW_ENERGY_RECOVERY)
                .maxSpeed(POW_MAX_SPEED)
                .powerCorresponding(POW_POWER_CORRESPONDING)
                .smoothness(POW_SMOOTHNESS)
                .build();
        DrivingMode dm = DrivingMode.builder().drivingModeConfigure(dmc)
                .drivingModeType(DRIVING_MODE_POW)
                .build();
        return dm;
    }

    //用户自定义CUSTOMER_MODE
    static DrivingMode setVehicleCSTDrivingMode(String modeName, int ap, int er, int ms, int pc, int sm){
        DrivingModeConfigure dmc = DrivingModeConfigure.builder()
                .accessoryPerformance(ap)
                .energyRecovery(er)
                .maxSpeed(ms)
                .powerCorresponding(pc)
                .smoothness(sm)
                .build();
        DrivingMode dm = DrivingMode.builder().drivingModeConfigure(dmc)
                .drivingModeType(DRIVING_MODE_CST)
                .build();
        return dm;
    }

    static void setCurrentDM(Driver driver){
        String hashName = REDIS_DRIVER_HASH_NAME_PREFIX+driver.getCellPhone()+REDIS_DRIVER_HASH_NAME_SUFFIX;
        drivingModelLogInfo("hashName="+hashName);
        drivingModelLogInfo("currentDM="+driver.getCurrentDM());
        setValue2HashField(hashName, REDIS_DRIVER_HASH_KEY_CURRENTDM, driver.getCurrentDM());
    }

    static void setDefaultDM(Driver driver){
        String hashName = REDIS_DRIVER_HASH_NAME_PREFIX+driver.getCellPhone()+REDIS_DRIVER_HASH_NAME_SUFFIX;
        drivingModelLogInfo("hashName="+hashName);
        drivingModelLogInfo("setDefaultDM="+driver.getDefaultDM());
        setValue2HashField(hashName, REDIS_DRIVER_HASH_KEY_DEFAULTDM, driver.getDefaultDM());
    }

    static DrivingMode getCurrentDM(Driver driver){
        String currentDM = driver.getCurrentDM();
        return getDrivingMode(currentDM, driver);
    }

    //保存DRIVING_MODE_CST
    static void setCustomerDM(Driver driver, DrivingMode dm){
        String cstDrivingModeHashName = REDIS_DRIVER_CST_DM_HASH_NAME_PREFIX
                +driver.getCellPhone()+REDIS_DRIVER_CST_DM_HASH_NAME_SUFFIX;
        drivingModelLogInfo("hashName="+cstDrivingModeHashName);
        drivingModelLogInfo("setCustomerDM="+dm.getDrivingModeConfigure().toString());
        setValue2HashField(cstDrivingModeHashName, REDIS_DRIVER_CST_DM_HASH_KEY_SP,
                String.valueOf(dm.getDrivingModeConfigure().getMaxSpeed()));
        setValue2HashField(cstDrivingModeHashName, REDIS_DRIVER_CST_DM_HASH_KEY_ER,
                String.valueOf(dm.getDrivingModeConfigure().getEnergyRecovery()));
        setValue2HashField(cstDrivingModeHashName, REDIS_DRIVER_CST_DM_HASH_KEY_PC,
                String.valueOf(dm.getDrivingModeConfigure().getPowerCorresponding()));
        setValue2HashField(cstDrivingModeHashName, REDIS_DRIVER_CST_DM_HASH_KEY_SM,
                String.valueOf(dm.getDrivingModeConfigure().getSmoothness()));
        setValue2HashField(cstDrivingModeHashName, REDIS_DRIVER_CST_DM_HASH_KEY_AP,
                String.valueOf(dm.getDrivingModeConfigure().getAccessoryPerformance()));
    }

    //得到DRIVING_MODE_CST
    static DrivingMode getCustomerDM(Driver driver){
        String cstDrivingModeHashName = REDIS_DRIVER_CST_DM_HASH_NAME_PREFIX
                +driver.getCellPhone()+REDIS_DRIVER_CST_DM_HASH_NAME_SUFFIX;
        Map<String, String> customerDMMap = hGetAll(cstDrivingModeHashName);
        DrivingModeConfigure dmc = DrivingModeConfigure.builder()
                .accessoryPerformance(Integer.parseInt(customerDMMap.get(REDIS_DRIVER_CST_DM_HASH_KEY_AP)))
                .energyRecovery(Integer.parseInt(customerDMMap.get(REDIS_DRIVER_CST_DM_HASH_KEY_ER)))
                .maxSpeed(Integer.parseInt(customerDMMap.get(REDIS_DRIVER_CST_DM_HASH_KEY_SP)))
                .powerCorresponding(Integer.parseInt(customerDMMap.get(REDIS_DRIVER_CST_DM_HASH_KEY_PC)))
                .smoothness(Integer.parseInt(customerDMMap.get(REDIS_DRIVER_CST_DM_HASH_KEY_SM)))
                .build();
        DrivingMode dm = DrivingMode.builder().drivingModeConfigure(dmc)
                .drivingModeType(DRIVING_MODE_CST)
                .build();
        return dm;
    }


}
