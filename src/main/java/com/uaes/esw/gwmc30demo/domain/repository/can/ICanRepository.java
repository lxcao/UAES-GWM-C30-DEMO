package com.uaes.esw.gwmc30demo.domain.repository.can;

import com.uaes.esw.gwmc30demo.domain.model.entity.can.*;

import static com.uaes.esw.gwmc30demo.constant.InfraRedisConstants.*;
import static com.uaes.esw.gwmc30demo.infrastructure.json.JSONUtility.transferFromJSON2Object;
import static com.uaes.esw.gwmc30demo.infrastructure.redis.RedisHandler.getLastOneStringFromZset;
import static com.uaes.esw.gwmc30demo.infrastructure.redis.RedisHandler.getPreviousOneStringFromZset;

public interface ICanRepository {

    static VCU61CanMessage getLastVCU61MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_VCU_61_ZSET),
                VCU61CanMessage.class);
    }

    static VCU62CanMessage getLastVCU62MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_VCU_62_ZSET),
                VCU62CanMessage.class);
    }

    static VCU63CanMessage getLastVCU63MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_VCU_63_ZSET),
                VCU63CanMessage.class);
    }

    static VCU64CanMessage getLastVCU64MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_VCU_64_ZSET),
                VCU64CanMessage.class);
    }

    static VCU65CanMessage getLastVCU65MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_VCU_65_ZSET),
                VCU65CanMessage.class);
    }

    static VCU66CanMessage getLastVCU66MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_VCU_66_ZSET),
                VCU66CanMessage.class);
    }

    static VCU67CanMessage getLastVCU67MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_VCU_67_ZSET),
                VCU67CanMessage.class);
    }

    static VCU68CanMessage getLastVCU68MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_VCU_68_ZSET),
                VCU68CanMessage.class);
    }

    static VCU69CanMessage getLastVCU69MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_VCU_69_ZSET),
                VCU69CanMessage.class);
    }

    static VCU70CanMessage getLastVCU70MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_VCU_70_ZSET),
                VCU70CanMessage.class);
    }

    static VCU71CanMessage getLastVCU71MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_VCU_71_ZSET),
                VCU71CanMessage.class);
    }

    static VCU72CanMessage getLastVCU72MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_VCU_72_ZSET),
                VCU72CanMessage.class);
    }

    static VCU73CanMessage getLastVCU73MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_VCU_73_ZSET),
                VCU73CanMessage.class);
    }

    static VCU73CanMessage getPreviousVCU73MessageFromRedis(){
        return transferFromJSON2Object(getPreviousOneStringFromZset(REDIS_VCU_73_ZSET),
                VCU73CanMessage.class);
    }

    static VCU75CanMessage getLastVCU75MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_VCU_75_ZSET),
                VCU75CanMessage.class);
    }

    static VCU76CanMessage getLastVCU76MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_VCU_76_ZSET),
                VCU76CanMessage.class);
    }

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
