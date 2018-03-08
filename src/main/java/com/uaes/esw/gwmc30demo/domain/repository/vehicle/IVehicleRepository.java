package com.uaes.esw.gwmc30demo.domain.repository.vehicle;

import com.uaes.esw.gwmc30demo.domain.model.vehicle.Battery;
import com.uaes.esw.gwmc30demo.domain.model.vehicle.Vehicle;

import java.util.Map;

import static com.uaes.esw.gwmc30demo.constant.InfraRedisConstants.*;
import static com.uaes.esw.gwmc30demo.constant.VehicleConstants.GWM_C30_MAX_MILEAGE;
import static com.uaes.esw.gwmc30demo.infrastructure.redis.RedisHandler.hgetall;

public interface IVehicleRepository {
    //得到车辆的当前快照
     static Vehicle getVehicleSnapshot(){

/*        Map<String, String> vehicleHashSet = hgetall(REDIS_VEHICLE_HASH_NAME);
        Battery c30Battery = Battery.builder()
                .soc(Double.valueOf(vehicleHashSet.get(REDIS_VEHICLE_HASH_KEY_SOC))).build();
        Vehicle c30Vehicle = Vehicle.builder()
                .battery(c30Battery)
                .vin(vehicleHashSet.get(REDIS_VEHICLE_HASH_KEY_VIN))
                .maxMileage(Double.valueOf(vehicleHashSet.get(REDIS_VEHICLE_HASH_KEY_MAXMILEAGE))).build();*/

         Battery c30Battery = Battery.builder().soc(0.5).build();
         Vehicle c30Vehicle = Vehicle.builder().battery(c30Battery).vin("111111")
                 .maxMileage(GWM_C30_MAX_MILEAGE).build();

        return c30Vehicle;
    }
    //把从车上收到的信息存放到redis中
     static void setVehicleSnapshot(String canData){

    }
}
