package com.uaes.esw.gwmc30demo.domain.repository.energySaving;

import com.uaes.esw.gwmc30demo.domain.model.entity.can.*;
import com.uaes.esw.gwmc30demo.domain.model.scenario.energySaving.*;

import java.util.HashMap;
import java.util.Map;

import static com.uaes.esw.gwmc30demo.constant.CommonConstants.RESPONSE_CODE_SUCCESS;
import static com.uaes.esw.gwmc30demo.constant.DrivingModeConstants.*;
import static com.uaes.esw.gwmc30demo.constant.DrivingModeConstants.DRIVING_MODE_ABB_CST;
import static com.uaes.esw.gwmc30demo.constant.DrivingModeConstants.DRIVING_MODE_ABB_POW;
import static com.uaes.esw.gwmc30demo.constant.EnergySavingConstants.*;
import static com.uaes.esw.gwmc30demo.constant.InfraRedisConstants.*;
import static com.uaes.esw.gwmc30demo.domain.repository.can.ICanRepository.*;
import static com.uaes.esw.gwmc30demo.infrastructure.json.JSONUtility.transferFromObject2JSON;
import static com.uaes.esw.gwmc30demo.infrastructure.redis.RedisHandler.*;
import static com.uaes.esw.gwmc30demo.infrastructure.utils.DateTimeUtils.*;
import static com.uaes.esw.gwmc30demo.infrastructure.utils.LoggerUtils.energySavingLogInfo;

public interface IEnergySavingRepository {

    static ESRemind getRemind(){
        VCU73CanMessage vcu73CanMessage = getLastVCU73MessageFromRedis();
        return ESRemind.builder()
                .motorEfficiency(vcu73CanMessage.getMotor_Efficiency())
                .ACOnOff(vcu73CanMessage.getAC_On_Off_Remind())
                .driveStatus(vcu73CanMessage.getFierce_Drive_Status()).build();
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
        energySavingLogInfo("foundLastEnergySavingCycle");
        VCU61CanMessage vcu61CanMessage = getLastVCU61MessageFromRedis();
        energySavingLogInfo("vcu61CanMessage="+vcu61CanMessage);
        VCU62CanMessage vcu62CanMessage = getLastVCU62MessageFromRedis();
        energySavingLogInfo("vcu62CanMessage="+vcu62CanMessage);
        VCU63CanMessage vcu63CanMessage = getLastVCU63MessageFromRedis();
        energySavingLogInfo("vcu63CanMessage="+vcu63CanMessage);
        VCU64CanMessage vcu64CanMessage = getLastVCU64MessageFromRedis();
        energySavingLogInfo("vcu64CanMessage="+vcu64CanMessage);
        VCU65CanMessage vcu65CanMessage = getLastVCU65MessageFromRedis();
        energySavingLogInfo("vcu65CanMessage="+vcu65CanMessage);
        VCU66CanMessage vcu66CanMessage = getLastVCU66MessageFromRedis();
        energySavingLogInfo("vcu66CanMessage="+vcu66CanMessage);
        VCU67CanMessage vcu67CanMessage = getLastVCU67MessageFromRedis();
        energySavingLogInfo("vcu67CanMessage="+vcu67CanMessage);
        VCU68CanMessage vcu68CanMessage = getLastVCU68MessageFromRedis();
        energySavingLogInfo("vcu68CanMessage="+vcu68CanMessage);
        VCU69CanMessage vcu69CanMessage = getLastVCU69MessageFromRedis();
        energySavingLogInfo("vcu69CanMessage="+vcu69CanMessage);
        VCU70CanMessage vcu70CanMessage = getLastVCU70MessageFromRedis();
        energySavingLogInfo("vcu70CanMessage="+vcu70CanMessage);
        VCU71CanMessage vcu71CanMessage = getLastVCU71MessageFromRedis();
        energySavingLogInfo("vcu71CanMessage="+vcu71CanMessage);
        VCU72CanMessage vcu72CanMessage = getLastVCU72MessageFromRedis();
        energySavingLogInfo("vcu72CanMessage="+vcu72CanMessage);
        VCU75CanMessage vcu75CanMessage = getLastVCU75MessageFromRedis();
        energySavingLogInfo("vcu75CanMessage="+vcu75CanMessage);
        VCU76CanMessage vcu76CanMessage = getLastVCU76MessageFromRedis();
        energySavingLogInfo("vcu76CanMessage="+vcu76CanMessage);

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

        energySavingLogInfo("storeLastEnergySavingCycle="+energySavingCanMessage.toString());

    }

}
