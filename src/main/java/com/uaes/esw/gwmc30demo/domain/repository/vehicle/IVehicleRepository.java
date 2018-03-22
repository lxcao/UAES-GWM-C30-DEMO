package com.uaes.esw.gwmc30demo.domain.repository.vehicle;

import com.uaes.esw.gwmc30demo.domain.model.entity.can.B1CanMessage;
import com.uaes.esw.gwmc30demo.domain.model.entity.driver.Driver;
import com.uaes.esw.gwmc30demo.domain.model.entity.vehicle.Battery;
import com.uaes.esw.gwmc30demo.domain.model.entity.vehicle.DrivingMode;
import com.uaes.esw.gwmc30demo.domain.model.entity.vehicle.Vehicle;
import com.uaes.esw.gwmc30demo.domain.repository.drivingMode.IDrivingModeRepository;
import com.uaes.esw.gwmc30demo.infrastructure.json.JSONUtility;
import com.uaes.esw.gwmc30demo.infrastructure.kafka.KafkaProducerFactory;

import java.util.Map;
import java.util.Random;

import static com.uaes.esw.gwmc30demo.constant.CommonConstants.PERCENTAGE;
import static com.uaes.esw.gwmc30demo.constant.InfraKafkaConstants.KAFKA_CONFIG_CURRENT_DM_KEY;
import static com.uaes.esw.gwmc30demo.constant.InfraKafkaConstants.KAFKA_CONFIG_DEFAULT_DM_KEY;
import static com.uaes.esw.gwmc30demo.constant.InfraKafkaConstants.KAFKA_CONFIG_NORMAL_DM_KEY;
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

    //发送Current DrivingMode到Vehicle
    static void sendCurrentDM2Vehicle(Driver driver){
        DrivingMode currentDM = IDrivingModeRepository.getDrivingMode(driver.getCurrentDM(),driver);
        String currentDMStr = JSONUtility.transferFromObject2JSON(currentDM);
        System.out.println("Send CurrentDM2Vehicle="+currentDMStr);
        //send to kafka
        KafkaProducerFactory.sendMessage(KAFKA_CONFIG_CURRENT_DM_KEY,currentDMStr);
    }

    //发送Default DrivingMode到Vehicle
    static void sendDefaultDM2Vehicle(Driver driver){
        DrivingMode defaultDM = IDrivingModeRepository.getDrivingMode(driver.getDefaultDM(),driver);
        String defaultDMStr = JSONUtility.transferFromObject2JSON(defaultDM);
        System.out.println("Send DefaultDM2Vehicle="+defaultDMStr);
        //send to kafka
        KafkaProducerFactory.sendMessage(KAFKA_CONFIG_DEFAULT_DM_KEY,defaultDMStr);
    }

    //发送Normal DrivingMode到Vehicle
    static void sendNormalDM2Vehicle(Driver driver){
         DrivingMode normalDM = IDrivingModeRepository.getVehicleNORDrivingMode();
         String normalDMStr = JSONUtility.transferFromObject2JSON(normalDM);
        System.out.println("Send NormalDM2Vehicle="+normalDMStr);
        //send to kafka
        KafkaProducerFactory.sendMessage(KAFKA_CONFIG_NORMAL_DM_KEY,normalDMStr);
    }

}
