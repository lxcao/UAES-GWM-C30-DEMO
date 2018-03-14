package com.uaes.esw.gwmc30demo.domain.repository.vehicle;

import com.uaes.esw.gwmc30demo.domain.model.can.B1CanMessage;
import com.uaes.esw.gwmc30demo.domain.model.vehicle.Battery;
import com.uaes.esw.gwmc30demo.domain.model.vehicle.Vehicle;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.uaes.esw.gwmc30demo.constant.CommonConstants.PERCENTAGE;
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

    //每500毫秒轮询并更新Vehicle Hash
    static void updateVehicleSnapShotManager(){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            while(true){
                updateVehicleSnapShot(REDIS_VEHICLE_HASH_NAME);
                try{
                    TimeUnit.MILLISECONDS.sleep(REDIS_VEHICLE_HASH_UPDATE_INTERVAL_MS);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    //开始和车辆相关的数据操作
    static void startVehicleDataService(){
        updateVehicleSnapShotManager();
    }

    //得到最新的SOC
    static double getLastOneSOCInZset(){
            double pack_Soc_BMS = 0.0;
            String lastString = getLastStringFromZset(REDIS_BMS_B1_ZSET);
            B1CanMessage b1CanMessage = transferFromJSON2Object(lastString,B1CanMessage.class);
            int soc = b1CanMessage.getPack_Soc_BMS();
            pack_Soc_BMS = (double) soc *PERCENTAGE;

        return pack_Soc_BMS;
    }
}
