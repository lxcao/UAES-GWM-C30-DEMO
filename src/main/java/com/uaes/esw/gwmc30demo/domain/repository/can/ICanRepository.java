package com.uaes.esw.gwmc30demo.domain.repository.can;

import com.uaes.esw.gwmc30demo.domain.model.entity.can.*;

import static com.uaes.esw.gwmc30demo.constant.InfraRedisConstants.*;
import static com.uaes.esw.gwmc30demo.infrastructure.json.JSONUtility.transferFromJSON2Object;
import static com.uaes.esw.gwmc30demo.infrastructure.redis.RedisHandler.*;

public interface ICanRepository {

    static VCU61CanMessage getLastVCU61MessageFromRedis(){
        return transferFromJSON2Object(getLast30StringFromZset(REDIS_VCU_61_ZSET),
                VCU61CanMessage.class);
    }

    static VCU62CanMessage getLastVCU62MessageFromRedis(){
        return transferFromJSON2Object(getLast30StringFromZset(REDIS_VCU_62_ZSET),
                VCU62CanMessage.class);
    }

    static VCU63CanMessage getLastVCU63MessageFromRedis(){
        return transferFromJSON2Object(getLast30StringFromZset(REDIS_VCU_63_ZSET),
                VCU63CanMessage.class);
    }

    static VCU64CanMessage getLastVCU64MessageFromRedis(){
        return transferFromJSON2Object(getLast30StringFromZset(REDIS_VCU_64_ZSET),
                VCU64CanMessage.class);
    }

    static VCU65CanMessage getLastVCU65MessageFromRedis(){
        return transferFromJSON2Object(getLast30StringFromZset(REDIS_VCU_65_ZSET),
                VCU65CanMessage.class);
    }

    static VCU66CanMessage getLastVCU66MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_VCU_66_ZSET),
                VCU66CanMessage.class);
    }

    static VCU67CanMessage getLastVCU67MessageFromRedis(){
        return transferFromJSON2Object(getLast30StringFromZset(REDIS_VCU_67_ZSET),
                VCU67CanMessage.class);
    }

    static VCU68CanMessage getLastVCU68MessageFromRedis(){
        return transferFromJSON2Object(getLast30StringFromZset(REDIS_VCU_68_ZSET),
                VCU68CanMessage.class);
    }

    static VCU69CanMessage getLastVCU69MessageFromRedis(){
        return transferFromJSON2Object(getLast30StringFromZset(REDIS_VCU_69_ZSET),
                VCU69CanMessage.class);
    }

    static VCU70CanMessage getLastVCU70MessageFromRedis(){
        return transferFromJSON2Object(getLast30StringFromZset(REDIS_VCU_70_ZSET),
                VCU70CanMessage.class);
    }

    static VCU71CanMessage getLastVCU71MessageFromRedis(){
        return transferFromJSON2Object(getLast30StringFromZset(REDIS_VCU_71_ZSET),
                VCU71CanMessage.class);
    }

    static VCU72CanMessage getLastVCU72MessageFromRedis(){
        return transferFromJSON2Object(getLast30StringFromZset(REDIS_VCU_72_ZSET),
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

    static B1CanMessage getLastBMSB1CanMessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_BMS_B1_ZSET),
                B1CanMessage.class);
    }

    static B2CanMessage getLastBMSB2CanMessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_BMS_B2_ZSET),
                B2CanMessage.class);
    }

    static B3CanMessage getLastBMSB3CanMessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_BMS_B3_ZSET),
                B3CanMessage.class);
    }

    static VCU29DCanMessage getLastVCU29DCanMessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_VCU_29D_ZSET),
                VCU29DCanMessage.class);
    }

    static VCU294CanMessage getLastVCU294CanMessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_VCU_294_ZSET),
                VCU294CanMessage.class);
    }

    static VCUFBCanMessage getLastVCUFBCanMessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_VCU_FB_ZSET),
                VCUFBCanMessage.class);
    }

    static VCUFACanMessage getLastVCUFACanMessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_VCU_FA_ZSET),
                VCUFACanMessage.class);
    }

    static VCU77CanMessage getLastVCU77MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_VCU_77_ZSET),
                VCU77CanMessage.class);
    }

    static VCU78CanMessage getLastVCU78MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_VCU_78_ZSET),
                VCU78CanMessage.class);
    }

    static BMS410CanMessage getLastBMS410MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_BMS_410_ZSET),
                BMS410CanMessage.class);
    }

    static BMS411CanMessage getLastBMS411MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_BMS_411_ZSET),
                BMS411CanMessage.class);
    }

    static BMS412CanMessage getLastBMS412MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_BMS_412_ZSET),
                BMS412CanMessage.class);
    }

    static BMS413CanMessage getLastBMS413MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_BMS_413_ZSET),
                BMS413CanMessage.class);
    }

    static BMS414CanMessage getLastBMS414MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_BMS_414_ZSET),
                BMS414CanMessage.class);
    }

    static BMS415CanMessage getLastBMS415MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_BMS_415_ZSET),
                BMS415CanMessage.class);
    }

    static BMS416CanMessage getLastBMS416MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_BMS_416_ZSET),
                BMS416CanMessage.class);
    }

    static BMS417CanMessage getLastBMS417MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_BMS_417_ZSET),
                BMS417CanMessage.class);
    }

    static BMS418CanMessage getLastBMS418MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_BMS_418_ZSET),
                BMS418CanMessage.class);
    }

    static BMS420CanMessage getLastBMS420MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_BMS_420_ZSET),
                BMS420CanMessage.class);
    }

    static BMS421CanMessage getLastBMS421MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_BMS_421_ZSET),
                BMS421CanMessage.class);
    }

    static BMS422CanMessage getLastBMS422MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_BMS_422_ZSET),
                BMS422CanMessage.class);
    }

    static BMS423CanMessage getLastBMS423MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_BMS_423_ZSET),
                BMS423CanMessage.class);
    }

    static BMS424CanMessage getLastBMS424MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_BMS_424_ZSET),
                BMS424CanMessage.class);
    }

    static BMS425CanMessage getLastBMS425MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_BMS_425_ZSET),
                BMS425CanMessage.class);
    }

    static BMS426CanMessage getLastBMS426MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_BMS_426_ZSET),
                BMS426CanMessage.class);
    }

    static BMS427CanMessage getLastBMS427MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_BMS_427_ZSET),
                BMS427CanMessage.class);
    }

    static BMS428CanMessage getLastBMS428MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_BMS_428_ZSET),
                BMS428CanMessage.class);
    }

    static BMS430CanMessage getLastBMS430MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_BMS_430_ZSET),
                BMS430CanMessage.class);
    }

    static BMS431CanMessage getLastBMS431MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_BMS_431_ZSET),
                BMS431CanMessage.class);
    }

    static BMS432CanMessage getLastBMS432MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_BMS_432_ZSET),
                BMS432CanMessage.class);
    }

    static BMS433CanMessage getLastBMS433MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_BMS_433_ZSET),
                BMS433CanMessage.class);
    }

    static BMS434CanMessage getLastBMS434MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_BMS_434_ZSET),
                BMS434CanMessage.class);
    }

    static BMS435CanMessage getLastBMS435MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_BMS_435_ZSET),
                BMS435CanMessage.class);
    }

    static BMS436CanMessage getLastBMS436MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_BMS_436_ZSET),
                BMS436CanMessage.class);
    }

    static BMS437CanMessage getLastBMS437MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_BMS_437_ZSET),
                BMS437CanMessage.class);
    }

    static BMS440CanMessage getLastBMS440MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_BMS_440_ZSET),
                BMS440CanMessage.class);
    }

    static BMS442CanMessage getLastBMS442MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_BMS_442_ZSET),
                BMS442CanMessage.class);
    }

    static BMS443CanMessage getLastBMS443MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_BMS_443_ZSET),
                BMS443CanMessage.class);
    }

    static BMS444CanMessage getLastBMS444MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_BMS_444_ZSET),
                BMS444CanMessage.class);
    }

    static BMS445CanMessage getLastBMS445MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_BMS_445_ZSET),
                BMS445CanMessage.class);
    }

    static BMS446CanMessage getLastBMS446MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_BMS_446_ZSET),
                BMS446CanMessage.class);
    }

    static BMS447CanMessage getLastBMS447MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_BMS_447_ZSET),
                BMS447CanMessage.class);
    }

    static BMS449CanMessage getLastBMS449MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_BMS_449_ZSET),
                BMS449CanMessage.class);
    }

    static BMS452CanMessage getLastBMS452MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_BMS_452_ZSET),
                BMS452CanMessage.class);
    }

    static BMS454CanMessage getLastBMS454MessageFromRedis(){
        return transferFromJSON2Object(getLastOneStringFromZset(REDIS_BMS_454_ZSET),
                BMS454CanMessage.class);
    }
}
