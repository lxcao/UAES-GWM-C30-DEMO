package com.uaes.esw.gwmc30demo.domain.repository.vehicle;

import com.uaes.esw.gwmc30demo.domain.model.can.B1CanMessage;
import com.uaes.esw.gwmc30demo.domain.model.driver.Driver;
import com.uaes.esw.gwmc30demo.domain.model.vehicle.Battery;
import com.uaes.esw.gwmc30demo.domain.model.vehicle.DrivingMode;
import com.uaes.esw.gwmc30demo.domain.model.vehicle.DrivingModeConfigure;
import com.uaes.esw.gwmc30demo.domain.model.vehicle.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.uaes.esw.gwmc30demo.constant.CommonConstants.PERCENTAGE;
import static com.uaes.esw.gwmc30demo.constant.DrivingModeConstants.*;
import static com.uaes.esw.gwmc30demo.constant.InfraRedisConstants.*;
import static com.uaes.esw.gwmc30demo.infrastructure.json.JSONUtility.transferFromJSON2Object;
import static com.uaes.esw.gwmc30demo.infrastructure.redis.RedisHandler.*;

public interface IVehicleRepository {
    //得到车辆的当前快照
     static Vehicle getVehicleSnapshot(String vinCode){

         //TODO: should get hash by vinCode
        Map<String, String> vehicleHashSet = hGetAll(REDIS_VEHICLE_HASH_NAME);
        Battery c30Battery = Battery.builder()
                .soc(Double.valueOf(vehicleHashSet.get(REDIS_VEHICLE_HASH_KEY_SOC))).build();
        Vehicle c30Vehicle = Vehicle.builder()
                .battery(c30Battery)
                .vin(vinCode)
                .maxMileage(Double.valueOf(vehicleHashSet.get(REDIS_VEHICLE_HASH_KEY_MAXMILEAGE))).build();

/*         Battery c30Battery = Battery.builder().soc(getLastOneSOCInZset()).build();
         Vehicle c30Vehicle = Vehicle.builder().battery(c30Battery).vin(vinCode)
                 .maxMileage(GWM_C30_MAX_MILEAGE).build();*/

        return c30Vehicle;
    }

    //把从车上收到的soc信息存放到Vehicle的hash中
     static void setSOC2VehicleSnapshot(String vehicleHashName, String soc){
         setValue2HashField(vehicleHashName,REDIS_VEHICLE_HASH_KEY_SOC,soc);
    }

    //轮询各个CAN的zset，取到最新的值，存放到Vehicle的hash中
    static void updateVehicleSnapShot(String vehicleHashName){
        setSOC2VehicleSnapshot(vehicleHashName, String.valueOf(getLastOneSOCInZset()));
    }



    //得到最新的SOC
    static double getLastOneSOCInZset(){
            double pack_Soc_BMS = 0.0;
            String lastString = getLastStringFromZset(REDIS_BMS_B1_ZSET);
            B1CanMessage b1CanMessage = transferFromJSON2Object(lastString,B1CanMessage.class);
            double soc = b1CanMessage.getPack_Soc_BMS();
            pack_Soc_BMS = soc *PERCENTAGE;

        return pack_Soc_BMS;
    }

    //得到所有车辆模式
    static List<DrivingMode> getAllDrivingMode(Driver driver){
         List<DrivingMode> drivingModeList = new ArrayList<>();
         drivingModeList.add(getVehicleEEMDrivingMode());
        drivingModeList.add(getVehicleECODrivingMode());
        drivingModeList.add(getVehicleNORDrivingMode());
        drivingModeList.add(getVehicleEPMDrivingMode());
        drivingModeList.add(getVehiclePOWDrivingMode());
        drivingModeList.add(getVehicleCSTDrivingMode(driver));
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
             return getVehicleCSTDrivingMode(driver);
         }

         else
             return getVehicleNORDrivingMode();
    }

    //生成DRIVING_MODE_CST
    static DrivingMode getVehicleCSTDrivingMode(Driver driver){
         //TODO: get from redis
        DrivingModeConfigure dmc = DrivingModeConfigure.builder()
                .accessoryPerformance(CST_ACCESSORY_PERFORMANCE)
                .energyRecovery(CST_ENERGY_RECOVERY)
                .maxSpeed(CST_MAX_SPEED)
                .powerCorresponding(CST_POWER_CORRESPONDING)
                .smoothness(CST_SMOOTHNESS)
                .build();
        DrivingMode dm = DrivingMode.builder().drivingModeConfigure(dmc)
                .drivingModeType(DRIVING_MODE_CST)
                .build();
        return dm;
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
        System.out.println("hashName="+hashName);
        System.out.println("currentDM="+driver.getCurrentDM());
         setValue2HashField(hashName, REDIS_DRIVER_HASH_KEY_CURRENTDM, driver.getCurrentDM());
    }

    static void setDefaultDM(Driver driver){
        String hashName = REDIS_DRIVER_HASH_NAME_PREFIX+driver.getCellPhone()+REDIS_DRIVER_HASH_NAME_SUFFIX;
        System.out.println("hashName="+hashName);
        System.out.println("defaultDM="+driver.getDefaultDM());
        setValue2HashField(hashName, REDIS_DRIVER_HASH_KEY_DEFAULTDM, driver.getDefaultDM());
    }
}
