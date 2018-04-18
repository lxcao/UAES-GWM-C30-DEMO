package com.uaes.esw.gwmc30demo.domain.repository.energySaving;

import com.uaes.esw.gwmc30demo.domain.model.entity.can.*;
import com.uaes.esw.gwmc30demo.domain.model.scenario.energySaving.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.uaes.esw.gwmc30demo.constant.CommonConstants.RESPONSE_CODE_SUCCESS;
import static com.uaes.esw.gwmc30demo.constant.DrivingModeConstants.*;
import static com.uaes.esw.gwmc30demo.constant.DrivingModeConstants.DRIVING_MODE_ABB_CST;
import static com.uaes.esw.gwmc30demo.constant.DrivingModeConstants.DRIVING_MODE_ABB_POW;
import static com.uaes.esw.gwmc30demo.constant.EnergySavingConstants.*;
import static com.uaes.esw.gwmc30demo.constant.InfraRedisConstants.*;
import static com.uaes.esw.gwmc30demo.infrastructure.json.JSONUtility.transferFromJSON2Object;
import static com.uaes.esw.gwmc30demo.infrastructure.json.JSONUtility.transferFromObject2JSON;
import static com.uaes.esw.gwmc30demo.infrastructure.redis.RedisHandler.*;
import static com.uaes.esw.gwmc30demo.infrastructure.utils.DateTimeUtils.*;

public interface IEnergySavingRepository {

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

    static ESRemind getRemind(){
        VCU73CanMessage vcu73CanMessage = getLastVCU73MessageFromRedis();
        return ESRemind.builder()
                .motorEfficiency(vcu73CanMessage.getMotor_Efficiency())
                .ACOnOff(vcu73CanMessage.getAC_On_Off_Remind())
                .driveStatus(vcu73CanMessage.getFierce_Drive_Status()).build();
    }

    static int getHVPowerOnStatusNow(){
        VCU73CanMessage vcu73CanMessage = getLastVCU73MessageFromRedis();
        return vcu73CanMessage.getHV_PowerOn();
    }

    static int getHVPowerOnStatusPrevious(){
        VCU73CanMessage vcu73CanMessage = getPreviousVCU73MessageFromRedis();
        return vcu73CanMessage.getHV_PowerOn();
    }

    static EnergySavingCanMessage initialEnergySavingCanMessage(){
        long nowTimeStamp = getDateTimeNowTimeStamp();
        VCU61CanMessage vcu61CanMessage = VCU61CanMessage.builder()
                .DCDC_Energy_Sum_Cyc(ELECTRICITY_SAVING_ZERO)
                .HVBatt_Energy_Sum_Cyc(ELECTRICITY_SAVING_ZERO)
                .Motor_Energy_Sum_Cyc(ELECTRICITY_SAVING_ZERO)
                .PTC_Energy_Sum_Cyc(ELECTRICITY_SAVING_ZERO)
                .unixtimestamp(nowTimeStamp).build();
        VCU62CanMessage vcu62CanMessage = VCU62CanMessage.builder()
                .AC_Energy_Sum_Cyc(ELECTRICITY_SAVING_ZERO)
                .Friction_Brk_Energy_Sum_Cyc(ELECTRICITY_SAVING_ZERO)
                .Mileage_Cyc(ELECTRICITY_SAVING_ZERO)
                .unixtimestamp(nowTimeStamp).build();
        VCU63CanMessage vcu63CanMessage = VCU63CanMessage.builder()
                .DCDC_Energy_UltraEco_Cyc(ELECTRICITY_SAVING_ZERO)
                .HVBatt_Energy_UltraEco_Cyc(ELECTRICITY_SAVING_ZERO)
                .Motor_Energy_UltraEco_Cyc(ELECTRICITY_SAVING_ZERO)
                .PTC_Energy_UltraEco_Cyc(ELECTRICITY_SAVING_ZERO)
                .unixtimestamp(nowTimeStamp).build();
        VCU64CanMessage vcu64CanMessage = VCU64CanMessage.builder()
                .AC_Energy_UltraEco_Cyc(ELECTRICITY_SAVING_ZERO)
                .Mileage_UltraEco_Cyc(ELECTRICITY_SAVING_ZERO)
                .unixtimestamp(nowTimeStamp).build();
        VCU65CanMessage vcu65CanMessage = VCU65CanMessage.builder()
                .DCDC_Energy_Eco_Cyc(ELECTRICITY_SAVING_ZERO)
                .HVBatt_Energy_Eco_Cyc(ELECTRICITY_SAVING_ZERO)
                .Motor_Energy_Eco_Cyc(ELECTRICITY_SAVING_ZERO)
                .PTC_Energy_Eco_Cyc(ELECTRICITY_SAVING_ZERO)
                .unixtimestamp(nowTimeStamp).build();
        VCU66CanMessage vcu66CanMessage = VCU66CanMessage.builder()
                .AC_Energy_Eco_Cyc(ELECTRICITY_SAVING_ZERO)
                .Mileage_Eco_Cyc(ELECTRICITY_SAVING_ZERO)
                .unixtimestamp(nowTimeStamp).build();
        VCU67CanMessage vcu67CanMessage = VCU67CanMessage.builder()
                .DCDC_Energy_Norm_Cyc(ELECTRICITY_SAVING_ZERO)
                .HVBatt_Energy_Norm_Cyc(ELECTRICITY_SAVING_ZERO)
                .Motor_Energy_Norm_Cyc(ELECTRICITY_SAVING_ZERO)
                .PTC_Energy_Norm_Cyc(ELECTRICITY_SAVING_ZERO)
                .unixtimestamp(nowTimeStamp).build();
        VCU68CanMessage vcu68CanMessage = VCU68CanMessage.builder()
                .AC_Energy_Norm_Cyc(ELECTRICITY_SAVING_ZERO)
                .Mileage_Norm_Cyc(ELECTRICITY_SAVING_ZERO)
                .unixtimestamp(nowTimeStamp).build();
        VCU69CanMessage vcu69CanMessage = VCU69CanMessage.builder()
                .DCDC_Energy_Sport_Cyc(ELECTRICITY_SAVING_ZERO)
                .HVBatt_Energy_Sport_Cyc(ELECTRICITY_SAVING_ZERO)
                .Motor_Energy_Sport_Cyc(ELECTRICITY_SAVING_ZERO)
                .PTC_Energy_Sport_Cyc(ELECTRICITY_SAVING_ZERO)
                .unixtimestamp(nowTimeStamp).build();
        VCU70CanMessage vcu70CanMessage = VCU70CanMessage.builder()
                .AC_Energy_Sport_Cyc(ELECTRICITY_SAVING_ZERO)
                .Mileage_Sport_Cyc(ELECTRICITY_SAVING_ZERO)
                .unixtimestamp(nowTimeStamp).build();
        VCU71CanMessage vcu71CanMessage = VCU71CanMessage.builder()
                .DCDC_Energy_UltraSport_Cyc(ELECTRICITY_SAVING_ZERO)
                .HVBatt_Energy_UltraSport_Cyc(ELECTRICITY_SAVING_ZERO)
                .Motor_Energy_UltraSport_Cyc(ELECTRICITY_SAVING_ZERO)
                .PTC_Energy_UltraSport_Cyc(ELECTRICITY_SAVING_ZERO)
                .unixtimestamp(nowTimeStamp).build();
        VCU72CanMessage vcu72CanMessage = VCU72CanMessage.builder()
                .AC_Energy_UltraSport_Cyc(ELECTRICITY_SAVING_ZERO)
                .Mileage_UltraSport_Cyc(ELECTRICITY_SAVING_ZERO)
                .unixtimestamp(nowTimeStamp).build();
        VCU75CanMessage vcu75CanMessage = VCU75CanMessage.builder()
                .PTC_Energy_Custom_Cyc(ELECTRICITY_SAVING_ZERO)
                .Motor_Energy_Custom_Cyc(ELECTRICITY_SAVING_ZERO)
                .HVBatt_Energy_Custom_Cyc(ELECTRICITY_SAVING_ZERO)
                .DCDC_Energy_Custom_Cyc(ELECTRICITY_SAVING_ZERO)
                .unixtimestamp(nowTimeStamp).build();
        VCU76CanMessage vcu76CanMessage = VCU76CanMessage.builder()
                .Mileage_Custom_Cyc(ELECTRICITY_SAVING_ZERO)
                .AC_Energy_Custom_Cyc(ELECTRICITY_SAVING_ZERO)
                .unixtimestamp(nowTimeStamp).build();
        return EnergySavingCanMessage.builder()
                .vcu61CanMessage(vcu61CanMessage)
                .vcu62CanMessage(vcu62CanMessage)
                .vcu63CanMessage(vcu63CanMessage)
                .vcu64CanMessage(vcu64CanMessage)
                .vcu65CanMessage(vcu65CanMessage)
                .vcu66CanMessage(vcu66CanMessage)
                .vcu67CanMessage(vcu67CanMessage)
                .vcu68CanMessage(vcu68CanMessage)
                .vcu69CanMessage(vcu69CanMessage)
                .vcu70CanMessage(vcu70CanMessage)
                .vcu71CanMessage(vcu71CanMessage)
                .vcu72CanMessage(vcu72CanMessage)
                .vcu75CanMessage(vcu75CanMessage)
                .vcu76CanMessage(vcu76CanMessage).build();
    }

    static QueryESRes createCurrentZero(QueryESReq esReq){
        ESSummary esSummary = ESSummary.builder().energyPer100KM(ELECTRICITY_SAVING_ZERO)
                .sumEnergy(ELECTRICITY_SAVING_ZERO).sumEnergyCost(ELECTRICITY_SAVING_ZERO).build();
        ComponentsPercentSum componentsPercentSum = ComponentsPercentSum.builder()
                .RESTEnergyPert(ELECTRICITY_SAVING_ZERO).ACEnergyPert(ELECTRICITY_SAVING_ZERO)
                .DCDCEngergyPert(ELECTRICITY_SAVING_ZERO).MTEnergyPert(ELECTRICITY_SAVING_ZERO)
                .BRKEnergyPert(ELECTRICITY_SAVING_ZERO).build();
        Per100KMByDM per100KMByDM = Per100KMByDM.builder()
                .CustomerDM(ELECTRICITY_SAVING_ZERO).EconomyDM(ELECTRICITY_SAVING_ZERO)
                .NormalDM(ELECTRICITY_SAVING_ZERO).PowerDM(ELECTRICITY_SAVING_ZERO)
                .EPowerDM(ELECTRICITY_SAVING_ZERO).EEconomyDM(ELECTRICITY_SAVING_ZERO).build();
        Map<String,ComponentsPercent> cpByDM = new HashMap<>();
        ComponentsPercent componentsPercentEEM = ComponentsPercent.builder()
                .RESTEnergyPert(ELECTRICITY_SAVING_ZERO).ACEnergyPert(ELECTRICITY_SAVING_ZERO)
                .DCDCEngergyPert(ELECTRICITY_SAVING_ZERO).MTEnergyPert(ELECTRICITY_SAVING_ZERO).build();
        cpByDM.put(DRIVING_MODE_ABB_EEM,componentsPercentEEM);
        ComponentsPercent componentsPercentECO = ComponentsPercent.builder()
                .RESTEnergyPert(ELECTRICITY_SAVING_ZERO).ACEnergyPert(ELECTRICITY_SAVING_ZERO)
                .DCDCEngergyPert(ELECTRICITY_SAVING_ZERO).MTEnergyPert(ELECTRICITY_SAVING_ZERO).build();
        cpByDM.put(DRIVING_MODE_ABB_ECO,componentsPercentECO);
        ComponentsPercent componentsPercentNOR = ComponentsPercent.builder()
                .RESTEnergyPert(ELECTRICITY_SAVING_ZERO).ACEnergyPert(ELECTRICITY_SAVING_ZERO)
                .DCDCEngergyPert(ELECTRICITY_SAVING_ZERO).MTEnergyPert(ELECTRICITY_SAVING_ZERO).build();
        cpByDM.put(DRIVING_MODE_ABB_NOR,componentsPercentNOR);
        ComponentsPercent componentsPercentEPM = ComponentsPercent.builder()
                .RESTEnergyPert(ELECTRICITY_SAVING_ZERO).ACEnergyPert(ELECTRICITY_SAVING_ZERO)
                .DCDCEngergyPert(ELECTRICITY_SAVING_ZERO).MTEnergyPert(ELECTRICITY_SAVING_ZERO).build();
        cpByDM.put(DRIVING_MODE_ABB_EPM,componentsPercentEPM);
        ComponentsPercent componentsPercentPOW = ComponentsPercent.builder()
                .RESTEnergyPert(ELECTRICITY_SAVING_ZERO).ACEnergyPert(ELECTRICITY_SAVING_ZERO)
                .DCDCEngergyPert(ELECTRICITY_SAVING_ZERO).MTEnergyPert(ELECTRICITY_SAVING_ZERO).build();
        cpByDM.put(DRIVING_MODE_ABB_POW,componentsPercentPOW);
        ComponentsPercent componentsPercentCST = ComponentsPercent.builder()
                .RESTEnergyPert(ELECTRICITY_SAVING_ZERO).ACEnergyPert(ELECTRICITY_SAVING_ZERO)
                .DCDCEngergyPert(ELECTRICITY_SAVING_ZERO).MTEnergyPert(ELECTRICITY_SAVING_ZERO).build();
        cpByDM.put(DRIVING_MODE_ABB_CST,componentsPercentCST);
        return QueryESRes.builder()
                .driver(esReq.getDriver())
                .dateTime(esReq.getDateTime())
                .esSummary(esSummary)
                .componentsPercentSum(componentsPercentSum)
                .per100KMByDM(per100KMByDM)
                .CPByDM(cpByDM)
                .responseCode(RESPONSE_CODE_SUCCESS)
                .build();
    }

    static void storeLastEnergySavingCycle(){
        System.out.println("foundLastEnergySavingCycle");
        VCU61CanMessage vcu61CanMessage = getLastVCU61MessageFromRedis();
        System.out.println("vcu61CanMessage="+vcu61CanMessage);
        VCU62CanMessage vcu62CanMessage = getLastVCU62MessageFromRedis();
        System.out.println("vcu62CanMessage="+vcu62CanMessage);
        VCU63CanMessage vcu63CanMessage = getLastVCU63MessageFromRedis();
        System.out.println("vcu63CanMessage="+vcu63CanMessage);
        VCU64CanMessage vcu64CanMessage = getLastVCU64MessageFromRedis();
        System.out.println("vcu64CanMessage="+vcu64CanMessage);
        VCU65CanMessage vcu65CanMessage = getLastVCU65MessageFromRedis();
        System.out.println("vcu65CanMessage="+vcu65CanMessage);
        VCU66CanMessage vcu66CanMessage = getLastVCU66MessageFromRedis();
        System.out.println("vcu66CanMessage="+vcu66CanMessage);
        VCU67CanMessage vcu67CanMessage = getLastVCU67MessageFromRedis();
        System.out.println("vcu67CanMessage="+vcu67CanMessage);
        VCU68CanMessage vcu68CanMessage = getLastVCU68MessageFromRedis();
        System.out.println("vcu68CanMessage="+vcu68CanMessage);
        VCU69CanMessage vcu69CanMessage = getLastVCU69MessageFromRedis();
        System.out.println("vcu69CanMessage="+vcu69CanMessage);
        VCU70CanMessage vcu70CanMessage = getLastVCU70MessageFromRedis();
        System.out.println("vcu70CanMessage="+vcu70CanMessage);
        VCU71CanMessage vcu71CanMessage = getLastVCU71MessageFromRedis();
        System.out.println("vcu71CanMessage="+vcu71CanMessage);
        VCU72CanMessage vcu72CanMessage = getLastVCU72MessageFromRedis();
        System.out.println("vcu72CanMessage="+vcu72CanMessage);
        VCU75CanMessage vcu75CanMessage = getLastVCU75MessageFromRedis();
        System.out.println("vcu75CanMessage="+vcu75CanMessage);
        VCU76CanMessage vcu76CanMessage = getLastVCU76MessageFromRedis();
        System.out.println("vcu76CanMessage="+vcu76CanMessage);

        EnergySavingCanMessage energySavingCanMessage = EnergySavingCanMessage.builder()
                .vcu61CanMessage(vcu61CanMessage)
                .vcu62CanMessage(vcu62CanMessage)
                .vcu63CanMessage(vcu63CanMessage)
                .vcu64CanMessage(vcu64CanMessage)
                .vcu65CanMessage(vcu65CanMessage)
                .vcu66CanMessage(vcu66CanMessage)
                .vcu67CanMessage(vcu67CanMessage)
                .vcu68CanMessage(vcu68CanMessage)
                .vcu69CanMessage(vcu69CanMessage)
                .vcu70CanMessage(vcu70CanMessage)
                .vcu71CanMessage(vcu71CanMessage)
                .vcu72CanMessage(vcu72CanMessage)
                .vcu75CanMessage(vcu75CanMessage)
                .vcu76CanMessage(vcu76CanMessage)
                .timestamp(getDateTimeNowTimeStamp())
                .build();

        inputValue2ZSET(REDIS_ENERGY_SAVING_DRIVING_CYCLE_ZSET,
                energySavingCanMessage.getTimestamp(),
                transferFromObject2JSON(energySavingCanMessage));

        System.out.println("storeLastEnergySavingCycle="+energySavingCanMessage.toString());

    }

}
