package com.uaes.esw.gwmc30demo.domain.repository.vehicle;

import com.uaes.esw.gwmc30demo.domain.model.can.B1CanMessage;
import com.uaes.esw.gwmc30demo.domain.model.vehicle.Battery;
import com.uaes.esw.gwmc30demo.domain.model.vehicle.Vehicle;

import static com.uaes.esw.gwmc30demo.constant.InfraRedisConstants.REDIS_BMS_B1_ZSET;
import static com.uaes.esw.gwmc30demo.constant.VehicleConstants.GWM_C30_MAX_MILEAGE;
import static com.uaes.esw.gwmc30demo.infrastructure.json.JSONUtility.transferFromJSON2Object;
import static com.uaes.esw.gwmc30demo.infrastructure.redis.RedisHandler.*;

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

         Battery c30Battery = Battery.builder().soc(getLastOneSOCInZset()).build();
         Vehicle c30Vehicle = Vehicle.builder().battery(c30Battery).vin("111111")
                 .maxMileage(GWM_C30_MAX_MILEAGE).build();

        return c30Vehicle;
    }
    //把从车上收到的信息存放到redis中
     static void setVehicleSnapshot(String canData){

    }
    //得到最新的SOC
    static double getLastOneSOCInZset(){
            double pack_Soc_BMS = 0.0;
            String lastString = getLastStringFromZset(REDIS_BMS_B1_ZSET);
            System.out.println(lastString);
            B1CanMessage b1CanMessage = transferFromJSON2Object(lastString,B1CanMessage.class);
            int soc = b1CanMessage.getPack_Soc_BMS();
            System.out.println(soc);
            pack_Soc_BMS = (double) soc *0.01;
            System.out.println(pack_Soc_BMS);

        return pack_Soc_BMS;
    }
}
