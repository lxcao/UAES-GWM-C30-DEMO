package com.uaes.esw.gwmc30demo.domain.repository.vehicle;

import com.uaes.esw.gwmc30demo.domain.model.vehicle.Battery;
import com.uaes.esw.gwmc30demo.domain.model.vehicle.Vehicle;
import com.uaes.esw.gwmc30demo.infrastructure.redis.RedisFactory;
import redis.clients.jedis.Jedis;

import java.util.Map;

import static com.uaes.esw.gwmc30demo.constant.InfraRedisConstants.*;

public class VehicleRepositoryImpl implements IVehicleRepository {

    public static Vehicle getVehicleSnapshot(){

        Jedis jedisClient = RedisFactory.getOneJedisFromPool();

        Map<String, String> vehicleHashSet = jedisClient.hgetAll(REDIS_VEHICLE_HASH_NAME);
        Battery c30Battery = Battery.builder()
                .soc(Integer.valueOf(vehicleHashSet.get(REDIS_VEHICLE_HASH_KEY_SOC))).build();
        Vehicle c30Vehicle = Vehicle.builder()
                .battery(c30Battery)
                .vin(vehicleHashSet.get(REDIS_VEHICLE_HASH_KEY_VIN))
                .maxMileage(Double.valueOf(vehicleHashSet.get(REDIS_VEHICLE_HASH_KEY_MAXMILEAGE))).build();

        RedisFactory.releaseOneJedis2Pool(jedisClient);

        return c30Vehicle;
    }
}
