package com.uaes.esw.gwmc30demo.domain.repository.battery;

import com.uaes.esw.gwmc30demo.domain.model.entity.can.B1CanMessage;
import com.uaes.esw.gwmc30demo.domain.model.entity.can.B2CanMessage;
import com.uaes.esw.gwmc30demo.domain.model.entity.can.B3CanMessage;

import static com.uaes.esw.gwmc30demo.constant.InfraRedisConstants.REDIS_BMS_B1_ZSET;
import static com.uaes.esw.gwmc30demo.constant.InfraRedisConstants.REDIS_BMS_B2_ZSET;
import static com.uaes.esw.gwmc30demo.constant.InfraRedisConstants.REDIS_BMS_B3_ZSET;
import static com.uaes.esw.gwmc30demo.infrastructure.json.JSONUtility.transferFromJSON2Object;
import static com.uaes.esw.gwmc30demo.infrastructure.redis.RedisHandler.getLastOneStringFromZset;

public interface IBatteryRepository {
    static B1CanMessage getB1CanMessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_BMS_B1_ZSET),
                B1CanMessage.class);
    }

    static B2CanMessage getB2CanMessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_BMS_B2_ZSET),
                B2CanMessage.class);
    }

    static B3CanMessage getB3CanMessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_BMS_B3_ZSET),
                B3CanMessage.class);
    }
}
