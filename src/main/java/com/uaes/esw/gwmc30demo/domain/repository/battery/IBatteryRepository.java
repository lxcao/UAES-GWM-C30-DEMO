package com.uaes.esw.gwmc30demo.domain.repository.battery;

import com.uaes.esw.gwmc30demo.domain.model.entity.can.B1CanMessage;
import com.uaes.esw.gwmc30demo.domain.model.entity.can.B2CanMessage;
import com.uaes.esw.gwmc30demo.domain.model.entity.can.B3CanMessage;
import com.uaes.esw.gwmc30demo.domain.model.entity.vehicle.Battery;
import com.uaes.esw.gwmc30demo.domain.model.scenario.blackBox.SocTracker;
import com.uaes.esw.gwmc30demo.domain.repository.vehicle.IVehicleRepository;
import com.uaes.esw.gwmc30demo.infrastructure.redis.RedisHandler;
import com.uaes.esw.gwmc30demo.infrastructure.utils.DateTimeUtils;

import java.util.HashSet;
import java.util.Set;

import static com.uaes.esw.gwmc30demo.constant.BatteryBalanceConstants.BATTERY_BALANCE_DOUBLE_ZERO;
import static com.uaes.esw.gwmc30demo.constant.BatteryBalanceConstants.BATTERY_BALANCE_INTEGER_ZERO;
import static com.uaes.esw.gwmc30demo.constant.InfraRedisConstants.*;
import static com.uaes.esw.gwmc30demo.infrastructure.json.JSONUtility.transferFromJSON2Object;
import static com.uaes.esw.gwmc30demo.infrastructure.json.JSONUtility.transferFromObject2JSON;
import static com.uaes.esw.gwmc30demo.infrastructure.redis.RedisHandler.getLastOneStringFromZset;
import static com.uaes.esw.gwmc30demo.infrastructure.redis.RedisHandler.zRangeByScore;
import static com.uaes.esw.gwmc30demo.infrastructure.utils.DateTimeUtils.getDateTimeNowTimeStamp;

public interface IBatteryRepository {

    static Battery getBatterySnapshot(String vinCode){
        return IVehicleRepository.getVehicleSnapshot(vinCode).getBattery();
    }

    static long storeBalanceStartPoint(Battery battery){
         return RedisHandler.inputValue2ZSET(REDIS_BATTERY_BALANCE_START_POINT_ZSET,
                getDateTimeNowTimeStamp(), transferFromObject2JSON(battery));
    }

    static Battery getLastBalanceStartPoint(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_BATTERY_BALANCE_START_POINT_ZSET),
                Battery.class);
    }

    static long storeBalanceStopPoint(Battery battery){
        return RedisHandler.inputValue2ZSET(REDIS_BATTERY_BALANCE_STOP_POINT_ZSET,
                getDateTimeNowTimeStamp(), transferFromObject2JSON(battery));
    }

    static Battery getLastBalanceStopPoint(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_BATTERY_BALANCE_STOP_POINT_ZSET),
                Battery.class);
    }

    static Battery createBatteryZero(){
        Battery battery = Battery.builder()
                .soc(BATTERY_BALANCE_DOUBLE_ZERO)
                .balanceStatus(BATTERY_BALANCE_INTEGER_ZERO)
                .chargingStatus(BATTERY_BALANCE_INTEGER_ZERO)
                .chargingTime(BATTERY_BALANCE_DOUBLE_ZERO)
                .current(BATTERY_BALANCE_DOUBLE_ZERO)
                .socMax(BATTERY_BALANCE_DOUBLE_ZERO)
                .socMin(BATTERY_BALANCE_DOUBLE_ZERO)
                .temperature(BATTERY_BALANCE_DOUBLE_ZERO)
                .voltage(BATTERY_BALANCE_DOUBLE_ZERO)
                .build();
        return battery;
    }

    static Set<SocTracker> getSocTrackerByPeroid(long startUnixDateTime, long endUnixDateTime) {
        Set<String> bmsB1RedisResult = zRangeByScore(REDIS_BMS_B1_ZSET,
                startUnixDateTime, endUnixDateTime);
        Set<SocTracker> socTrackerSet  = new HashSet<>();
        bmsB1RedisResult.forEach(s -> {
            B1CanMessage b1CanMessage = transferFromJSON2Object(s, B1CanMessage.class);
            socTrackerSet.add(SocTracker.builder()
                    .soc(b1CanMessage.getPack_Soc_BMS())
                    .timeStamp(b1CanMessage.getUnixtimestamp()).build());
        });
        return socTrackerSet;
    }
}
