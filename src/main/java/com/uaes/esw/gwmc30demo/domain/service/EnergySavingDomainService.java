package com.uaes.esw.gwmc30demo.domain.service;

import com.uaes.esw.gwmc30demo.domain.model.entity.can.*;
import com.uaes.esw.gwmc30demo.domain.model.scenario.energySaving.*;
import com.uaes.esw.gwmc30demo.domain.repository.energySaving.IEnergySavingRepository;
import com.uaes.esw.gwmc30demo.infrastructure.utils.DateTimeUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import static com.uaes.esw.gwmc30demo.constant.CommonConstants.RESPONSE_CODE_SUCCESS;
import static com.uaes.esw.gwmc30demo.constant.DrivingModeConstants.*;
import static com.uaes.esw.gwmc30demo.constant.DrivingModeConstants.DRIVING_MODE_ABB_CST;
import static com.uaes.esw.gwmc30demo.constant.DrivingModeConstants.DRIVING_MODE_ABB_POW;
import static com.uaes.esw.gwmc30demo.constant.EnergySavingConstants.*;
import static com.uaes.esw.gwmc30demo.constant.EnergySavingConstants.ELECTRICITY_SAVING_ONE;
import static com.uaes.esw.gwmc30demo.constant.InfraRedisConstants.REDIS_ENERGY_SAVING_DRIVING_CYCLE_ZSET;
import static com.uaes.esw.gwmc30demo.domain.repository.driver.IDriverRepository.createDummyDriver;
import static com.uaes.esw.gwmc30demo.domain.repository.energySaving.IEnergySavingRepository.*;
import static com.uaes.esw.gwmc30demo.infrastructure.json.JSONUtility.transferFromJSON2Object;
import static com.uaes.esw.gwmc30demo.infrastructure.redis.RedisHandler.getLastOneStringFromZset;
import static com.uaes.esw.gwmc30demo.infrastructure.redis.RedisHandler.zRangeByScore;
import static com.uaes.esw.gwmc30demo.infrastructure.utils.DateTimeUtils.*;
import static com.uaes.esw.gwmc30demo.infrastructure.utils.DateTimeUtils.getTodayEndUnixTime;
import static com.uaes.esw.gwmc30demo.infrastructure.utils.DateTimeUtils.transfer2UnixTime;
import static com.uaes.esw.gwmc30demo.infrastructure.utils.LoggerUtils.energySavingLogInfo;

public interface EnergySavingDomainService {
    static QueryESRes queryESCurrentDomainService(QueryESReq esReq){
        if(!isHVPowerOnNow())
            return createCurrentZero(esReq);
        return getCurrentES(esReq);
    }
    static QueryESRes queryESLastCycleDomainService(QueryESReq esReq){
        return getLastCycleES(esReq);
    }

    static QueryESRes queryESTodayDomainService(QueryESReq esReq){
        return getTodayCycleES(esReq);
    }

    static QueryESRes queryESThisWeekDomainService(QueryESReq esReq){
        return getWeeklyCycleES(esReq);
    }

    static QueryESRes queryESCustomerDomainService(QueryESReq esReq){
        return getCustomerCycleES(esReq);
    }

    static ESRemindNotice createESRemind(){
        ESRemind esRemind = IEnergySavingRepository.getRemind();
        return ESRemindNotice.builder()
                .esRemind(esRemind).dateTime(DateTimeUtils.getDateTimeString()).build();
    }

    static boolean isHVPowerOnNow(){
        int hvPowerOnValue = getHVPowerOnStatusNow();
        return hvPowerOnValue != 0;
    }

    static boolean isHVPowerStatusChangeFromOn2Off(){
        int hvPowerOnNowValue = getHVPowerOnStatusNow();
        int hvPowerOnPreviousValue = getHVPowerOnStatusPrevious();
        return hvPowerOnPreviousValue == 1 && hvPowerOnNowValue == 0;
    }

    static void getAndStoreLastEnergySavingCycle(){
        if(isHVPowerStatusChangeFromOn2Off())
            storeLastEnergySavingCycle();
    }

    static double calEnergyKWH(VCU61CanMessage vcu61CanMessage){
        return vcu61CanMessage.getHVBatt_Energy_Sum_Cyc()/ELECTRICITY_SAVING_K;
    }

    static double calEnergyKWHPer100KM(VCU61CanMessage vcu61CanMessage, VCU62CanMessage vcu62CanMessage){
        double mileageCyc = vcu62CanMessage.getMileage_Cyc();
        if(mileageCyc != ELECTRICITY_SAVING_ZERO)
            return calEnergyKWH(vcu61CanMessage)*ELECTRICITY_SAVING_100KM/mileageCyc;
        return ELECTRICITY_SAVING_ZERO;
    }

    static double calCSTEnergyKWH(VCU75CanMessage vcu75CanMessage){
        return vcu75CanMessage.getHVBatt_Energy_Custom_Cyc()/ELECTRICITY_SAVING_K;
    }

    static double calCSTEnergyKWHPer100KM(VCU75CanMessage vcu75CanMessage, VCU76CanMessage vcu76CanMessage){
        double mileageCustomCyc = vcu76CanMessage.getMileage_Custom_Cyc();
        if(mileageCustomCyc != ELECTRICITY_SAVING_ZERO)
            return calCSTEnergyKWH(vcu75CanMessage)*ELECTRICITY_SAVING_100KM/mileageCustomCyc;
        return ELECTRICITY_SAVING_ZERO;
    }

    static double calNOREnergyKWH(VCU67CanMessage vcu67CanMessage){
        return vcu67CanMessage.getHVBatt_Energy_Norm_Cyc()/ELECTRICITY_SAVING_K;
    }

    static double calNOREnergyKWHPer100KM(VCU67CanMessage vcu67CanMessage, VCU68CanMessage vcu68CanMessage){
        double mileageNormCyc = vcu68CanMessage.getMileage_Norm_Cyc();
        if(mileageNormCyc != ELECTRICITY_SAVING_ZERO)
            return calNOREnergyKWH(vcu67CanMessage)*ELECTRICITY_SAVING_100KM/mileageNormCyc;
        return ELECTRICITY_SAVING_ZERO;
    }

    static double calPOWEnergyKWH(VCU69CanMessage vcu69CanMessage){
        return vcu69CanMessage.getHVBatt_Energy_Sport_Cyc()/ELECTRICITY_SAVING_K;
    }

    static double calPOWEnergyKWHPer100KM(VCU69CanMessage vcu69CanMessage, VCU70CanMessage vcu70CanMessage){
        double mileageSportCyc = vcu70CanMessage.getMileage_Sport_Cyc();
        if(mileageSportCyc != ELECTRICITY_SAVING_ZERO)
            return calPOWEnergyKWH(vcu69CanMessage)*ELECTRICITY_SAVING_100KM/mileageSportCyc;
        return ELECTRICITY_SAVING_ZERO;
    }

    static double calEPMEnergyKWH(VCU71CanMessage vcu71CanMessage){
        return vcu71CanMessage.getHVBatt_Energy_UltraSport_Cyc()/ELECTRICITY_SAVING_K;
    }

    static double calEPMEnergyKWHPer100KM(VCU71CanMessage vcu71CanMessage, VCU72CanMessage vcu72CanMessage){
        double mileageUltraSportCyc = vcu72CanMessage.getMileage_UltraSport_Cyc();
        if(mileageUltraSportCyc != ELECTRICITY_SAVING_ZERO)
            return calEPMEnergyKWH(vcu71CanMessage)*ELECTRICITY_SAVING_100KM/mileageUltraSportCyc;
        return ELECTRICITY_SAVING_ZERO;
    }

    static double calECOEnergyKWH(VCU65CanMessage vcu65CanMessage){
        return vcu65CanMessage.getHVBatt_Energy_Eco_Cyc()/ELECTRICITY_SAVING_K;
    }

    static double calECOEnergyKWHPer100KM(VCU65CanMessage vcu65CanMessage, VCU66CanMessage vcu66CanMessage){
        double mileageEcoCyc = vcu66CanMessage.getMileage_Eco_Cyc();
        if(mileageEcoCyc != ELECTRICITY_SAVING_ZERO)
            return calECOEnergyKWH(vcu65CanMessage)*ELECTRICITY_SAVING_100KM/mileageEcoCyc;
        return ELECTRICITY_SAVING_ZERO;
    }

    static double calEEMEnergyKWH(VCU63CanMessage vcu63CanMessage){
        return vcu63CanMessage.getHVBatt_Energy_UltraEco_Cyc()/ELECTRICITY_SAVING_K;
    }

    static double calEEMEnergyKWHPer100KM(VCU63CanMessage vcu63CanMessage, VCU64CanMessage vcu64CanMessage){
        double mileageUltraEcoCyc = vcu64CanMessage.getMileage_UltraEco_Cyc();
        if(mileageUltraEcoCyc != ELECTRICITY_SAVING_ZERO)
            return calEEMEnergyKWH(vcu63CanMessage)*ELECTRICITY_SAVING_100KM/mileageUltraEcoCyc;
        return ELECTRICITY_SAVING_ZERO;
    }

    static double calEnergyCost(VCU61CanMessage vcu61CanMessage){
        return calEnergyKWH(vcu61CanMessage)*ELECTRICITY_SAVING_FEE_RMBYUAN_PER_KWH;
    }

    static double calCompACEnergyPert(VCU61CanMessage vcu61CanMessage, VCU62CanMessage vcu62CanMessage){
        double hvBattEnergySumCyc = vcu61CanMessage.getHVBatt_Energy_Sum_Cyc();
        if(hvBattEnergySumCyc != ELECTRICITY_SAVING_ZERO)
            return (vcu61CanMessage.getPTC_Energy_Sum_Cyc()+vcu62CanMessage.getAC_Energy_Sum_Cyc())
                /hvBattEnergySumCyc;
        return ELECTRICITY_SAVING_ZERO;
    }
    static double calCompDCDCEnergyPert(VCU61CanMessage vcu61CanMessage){
        double hvBattEnergySumCyc = vcu61CanMessage.getHVBatt_Energy_Sum_Cyc();
        if(hvBattEnergySumCyc != ELECTRICITY_SAVING_ZERO)
            return vcu61CanMessage.getDCDC_Energy_Sum_Cyc()/hvBattEnergySumCyc;
        return ELECTRICITY_SAVING_ZERO;
    }

    static double calCompMTEnergyPert(VCU61CanMessage vcu61CanMessage){
        double hvBattEnergySumCyc = vcu61CanMessage.getHVBatt_Energy_Sum_Cyc();
        if(hvBattEnergySumCyc != ELECTRICITY_SAVING_ZERO)
            return vcu61CanMessage.getMotor_Energy_Sum_Cyc()/hvBattEnergySumCyc;
        return ELECTRICITY_SAVING_ZERO;
    }

    static double calCompBRKEnergyPert(VCU61CanMessage vcu61CanMessage, VCU62CanMessage vcu62CanMessage){
        double hvBattEnergySumCyc = vcu61CanMessage.getHVBatt_Energy_Sum_Cyc();
        if(hvBattEnergySumCyc != ELECTRICITY_SAVING_ZERO)
            return vcu62CanMessage.getFriction_Brk_Energy_Sum_Cyc()/hvBattEnergySumCyc;
        return ELECTRICITY_SAVING_ZERO;
    }

    static double calCompRESTEnergyPert(VCU61CanMessage vcu61CanMessage, VCU62CanMessage vcu62CanMessage){
        return ELECTRICITY_SAVING_ONE - calCompACEnergyPert(vcu61CanMessage,vcu62CanMessage)
                - calCompDCDCEnergyPert(vcu61CanMessage) - calCompMTEnergyPert(vcu61CanMessage);
    }

    static double calEEMACEnergyPert(VCU63CanMessage vcu63CanMessage, VCU64CanMessage vcu64CanMessage){
        double hvBattEnergyUltraEcoCyc = vcu63CanMessage.getHVBatt_Energy_UltraEco_Cyc();
        if(hvBattEnergyUltraEcoCyc != ELECTRICITY_SAVING_ZERO)
            return (vcu63CanMessage.getPTC_Energy_UltraEco_Cyc()+vcu64CanMessage.getAC_Energy_UltraEco_Cyc())
                /hvBattEnergyUltraEcoCyc;
        return ELECTRICITY_SAVING_ZERO;
    }
    static double calEEMDCDCEnergyPert(VCU63CanMessage vcu63CanMessage){
        double hvBattEnergyUltraEcoCyc = vcu63CanMessage.getHVBatt_Energy_UltraEco_Cyc();
        if(hvBattEnergyUltraEcoCyc != ELECTRICITY_SAVING_ZERO)
            return vcu63CanMessage.getDCDC_Energy_UltraEco_Cyc()/hvBattEnergyUltraEcoCyc;
        return ELECTRICITY_SAVING_ZERO;
    }

    static double calEEMMTEnergyPert(VCU63CanMessage vcu63CanMessage){
        double hvBattEnergyUltraEcoCyc = vcu63CanMessage.getHVBatt_Energy_UltraEco_Cyc();
        if(hvBattEnergyUltraEcoCyc != ELECTRICITY_SAVING_ZERO)
            return vcu63CanMessage.getMotor_Energy_UltraEco_Cyc()/hvBattEnergyUltraEcoCyc;
        return ELECTRICITY_SAVING_ZERO;
    }

    static double calEEMRESTEnergyPert(VCU63CanMessage vcu63CanMessage, VCU64CanMessage vcu64CanMessage){
        return ELECTRICITY_SAVING_ONE - calEEMACEnergyPert(vcu63CanMessage,vcu64CanMessage)
                - calEEMDCDCEnergyPert(vcu63CanMessage) - calEEMMTEnergyPert(vcu63CanMessage);
    }

    static double calECOACEnergyPert(VCU65CanMessage vcu65CanMessage, VCU66CanMessage vcu66CanMessage){
        double hvBattEnergyEcoCyc = vcu65CanMessage.getHVBatt_Energy_Eco_Cyc();
        if(hvBattEnergyEcoCyc != ELECTRICITY_SAVING_ZERO)
            return (vcu65CanMessage.getPTC_Energy_Eco_Cyc()+vcu66CanMessage.getAC_Energy_Eco_Cyc())
                /hvBattEnergyEcoCyc;
        return ELECTRICITY_SAVING_ZERO;
    }
    static double calECODCDCEnergyPert(VCU65CanMessage vcu65CanMessage){
        double hvBattEnergyEcoCyc = vcu65CanMessage.getHVBatt_Energy_Eco_Cyc();
        if(hvBattEnergyEcoCyc != ELECTRICITY_SAVING_ZERO)
            return vcu65CanMessage.getDCDC_Energy_Eco_Cyc()/hvBattEnergyEcoCyc;
        return ELECTRICITY_SAVING_ZERO;
    }

    static double calECOMTEnergyPert(VCU65CanMessage vcu65CanMessage){
        double hvBattEnergyEcoCyc = vcu65CanMessage.getHVBatt_Energy_Eco_Cyc();
        if(hvBattEnergyEcoCyc != ELECTRICITY_SAVING_ZERO)
            return vcu65CanMessage.getMotor_Energy_Eco_Cyc()/hvBattEnergyEcoCyc;
        return ELECTRICITY_SAVING_ZERO;
    }

    static double calECORESTEnergyPert(VCU65CanMessage vcu65CanMessage, VCU66CanMessage vcu66CanMessage){
        return ELECTRICITY_SAVING_ONE - calECOACEnergyPert(vcu65CanMessage,vcu66CanMessage)
                - calECODCDCEnergyPert(vcu65CanMessage) - calECOMTEnergyPert(vcu65CanMessage);
    }

    static double calNORACEnergyPert(VCU67CanMessage vcu67CanMessage, VCU68CanMessage vcu68CanMessage){
        double hvBattEnergyNormCyc = vcu67CanMessage.getHVBatt_Energy_Norm_Cyc();
        if(hvBattEnergyNormCyc != ELECTRICITY_SAVING_ZERO)
            return (vcu67CanMessage.getPTC_Energy_Norm_Cyc()+vcu68CanMessage.getAC_Energy_Norm_Cyc())
                /hvBattEnergyNormCyc;
        return ELECTRICITY_SAVING_ZERO;
    }
    static double calNORDCDCEnergyPert(VCU67CanMessage vcu67CanMessage){
        double hvBattEnergyNormCyc = vcu67CanMessage.getHVBatt_Energy_Norm_Cyc();
        if(hvBattEnergyNormCyc != ELECTRICITY_SAVING_ZERO)
            return vcu67CanMessage.getDCDC_Energy_Norm_Cyc()/hvBattEnergyNormCyc;
        return ELECTRICITY_SAVING_ZERO;
    }

    static double calNORMTEnergyPert(VCU67CanMessage vcu67CanMessage){
        double hvBattEnergyNormCyc = vcu67CanMessage.getHVBatt_Energy_Norm_Cyc();
        if(hvBattEnergyNormCyc != ELECTRICITY_SAVING_ZERO)
            return vcu67CanMessage.getMotor_Energy_Norm_Cyc()/hvBattEnergyNormCyc;
        return ELECTRICITY_SAVING_ZERO;
    }

    static double calNORRESTEnergyPert(VCU67CanMessage vcu67CanMessage, VCU68CanMessage vcu68CanMessage){
        return ELECTRICITY_SAVING_ONE - calNORACEnergyPert(vcu67CanMessage,vcu68CanMessage)
                - calNORDCDCEnergyPert(vcu67CanMessage) - calNORMTEnergyPert(vcu67CanMessage);
    }

    static double calEPMACEnergyPert(VCU71CanMessage vcu71CanMessage, VCU72CanMessage vcu72CanMessage){
        double hvBattEnergyUltrSportCyc = vcu71CanMessage.getHVBatt_Energy_UltraSport_Cyc();
        if(hvBattEnergyUltrSportCyc != ELECTRICITY_SAVING_ZERO)
            return (vcu71CanMessage.getPTC_Energy_UltraSport_Cyc()+vcu72CanMessage.getAC_Energy_UltraSport_Cyc())
                /hvBattEnergyUltrSportCyc;
        return ELECTRICITY_SAVING_ZERO;
    }
    static double calEPMDCDCEnergyPert(VCU71CanMessage vcu71CanMessage){
        double hvBattEnergyUltrSportCyc = vcu71CanMessage.getHVBatt_Energy_UltraSport_Cyc();
        if(hvBattEnergyUltrSportCyc != ELECTRICITY_SAVING_ZERO)
            return vcu71CanMessage.getDCDC_Energy_UltraSport_Cyc()/hvBattEnergyUltrSportCyc;
        return ELECTRICITY_SAVING_ZERO;
    }

    static double calEPMMTEnergyPert(VCU71CanMessage vcu71CanMessage){
        double hvBattEnergyUltrSportCyc = vcu71CanMessage.getHVBatt_Energy_UltraSport_Cyc();
        if(hvBattEnergyUltrSportCyc != ELECTRICITY_SAVING_ZERO)
            return vcu71CanMessage.getMotor_Energy_UltraSport_Cyc()/hvBattEnergyUltrSportCyc;
        return ELECTRICITY_SAVING_ZERO;
    }

    static double calEPMRESTEnergyPert(VCU71CanMessage vcu71CanMessage, VCU72CanMessage vcu72CanMessage){
        return ELECTRICITY_SAVING_ONE - calEPMACEnergyPert(vcu71CanMessage,vcu72CanMessage)
                - calEPMDCDCEnergyPert(vcu71CanMessage) - calEPMMTEnergyPert(vcu71CanMessage);
    }

    static double calPOWACEnergyPert(VCU69CanMessage vcu69CanMessage, VCU70CanMessage vcu70CanMessage){
        double hvBattEnergySportCyc = vcu69CanMessage.getHVBatt_Energy_Sport_Cyc();
        if(hvBattEnergySportCyc != ELECTRICITY_SAVING_ZERO)
            return (vcu69CanMessage.getPTC_Energy_Sport_Cyc()+vcu70CanMessage.getAC_Energy_Sport_Cyc())
                /hvBattEnergySportCyc;
        return ELECTRICITY_SAVING_ZERO;
    }
    static double calPOWDCDCEnergyPert(VCU69CanMessage vcu69CanMessage){
        double hvBattEnergySportCyc = vcu69CanMessage.getHVBatt_Energy_Sport_Cyc();
        if(hvBattEnergySportCyc != ELECTRICITY_SAVING_ZERO)
            return vcu69CanMessage.getDCDC_Energy_Sport_Cyc()/hvBattEnergySportCyc;
        return ELECTRICITY_SAVING_ZERO;
    }

    static double calPOWMTEnergyPert(VCU69CanMessage vcu69CanMessage){
        double hvBattEnergySportCyc = vcu69CanMessage.getHVBatt_Energy_Sport_Cyc();
        if(hvBattEnergySportCyc != ELECTRICITY_SAVING_ZERO)
            return vcu69CanMessage.getMotor_Energy_Sport_Cyc()/hvBattEnergySportCyc;
        return ELECTRICITY_SAVING_ZERO;
    }

    static double calPOWRESTEnergyPert(VCU69CanMessage vcu69CanMessage, VCU70CanMessage vcu70CanMessage){
        return ELECTRICITY_SAVING_ONE - calPOWACEnergyPert(vcu69CanMessage,vcu70CanMessage)
                - calPOWDCDCEnergyPert(vcu69CanMessage) - calPOWMTEnergyPert(vcu69CanMessage);
    }

    static double calCSTACEnergyPert(VCU75CanMessage vcu75CanMessage, VCU76CanMessage vcu76CanMessage){
        double hvBattEnergyCustomCyc = vcu75CanMessage.getHVBatt_Energy_Custom_Cyc();
        if(hvBattEnergyCustomCyc != ELECTRICITY_SAVING_ZERO)
            return (vcu75CanMessage.getPTC_Energy_Custom_Cyc()+vcu76CanMessage.getAC_Energy_Custom_Cyc())
                /hvBattEnergyCustomCyc;
        return ELECTRICITY_SAVING_ZERO;
    }
    static double calCSTDCDCEnergyPert(VCU75CanMessage vcu75CanMessage){
        double hvBattEnergyCustomCyc = vcu75CanMessage.getHVBatt_Energy_Custom_Cyc();
        if(hvBattEnergyCustomCyc != ELECTRICITY_SAVING_ZERO)
            return vcu75CanMessage.getDCDC_Energy_Custom_Cyc()/hvBattEnergyCustomCyc;
        return ELECTRICITY_SAVING_ZERO;
    }

    static double calCSTMTEnergyPert(VCU75CanMessage vcu75CanMessage){
        double hvBattEnergyCustomCyc = vcu75CanMessage.getHVBatt_Energy_Custom_Cyc();
        if(hvBattEnergyCustomCyc != ELECTRICITY_SAVING_ZERO)
            return vcu75CanMessage.getMotor_Energy_Custom_Cyc()/hvBattEnergyCustomCyc;
        return ELECTRICITY_SAVING_ZERO;
    }

    static double calCSTRESTEnergyPert(VCU75CanMessage vcu75CanMessage, VCU76CanMessage vcu76CanMessage){
        return ELECTRICITY_SAVING_ONE - calCSTACEnergyPert(vcu75CanMessage,vcu76CanMessage)
                - calCSTDCDCEnergyPert(vcu75CanMessage) - calCSTMTEnergyPert(vcu75CanMessage);
    }

    static QueryESRes getCurrentES(QueryESReq esReq){
        energySavingLogInfo("esReq="+esReq);
        EnergySavingCanMessage energySavingCurrentCanMessage = EnergySavingCanMessage.builder().build();
        VCU61CanMessage vcu61CanMessage = getLastVCU61MessageFromRedis();
        energySavingCurrentCanMessage.setVcu61CanMessage(vcu61CanMessage);
        VCU62CanMessage vcu62CanMessage = getLastVCU62MessageFromRedis();
        energySavingCurrentCanMessage.setVcu62CanMessage(vcu62CanMessage);
        VCU63CanMessage vcu63CanMessage = getLastVCU63MessageFromRedis();
        energySavingCurrentCanMessage.setVcu63CanMessage(vcu63CanMessage);
        VCU64CanMessage vcu64CanMessage = getLastVCU64MessageFromRedis();
        energySavingCurrentCanMessage.setVcu64CanMessage(vcu64CanMessage);
        VCU65CanMessage vcu65CanMessage = getLastVCU65MessageFromRedis();
        energySavingCurrentCanMessage.setVcu65CanMessage(vcu65CanMessage);
        VCU66CanMessage vcu66CanMessage = getLastVCU66MessageFromRedis();
        energySavingCurrentCanMessage.setVcu66CanMessage(vcu66CanMessage);
        VCU67CanMessage vcu67CanMessage = getLastVCU67MessageFromRedis();
        energySavingCurrentCanMessage.setVcu67CanMessage(vcu67CanMessage);
        VCU68CanMessage vcu68CanMessage = getLastVCU68MessageFromRedis();
        energySavingCurrentCanMessage.setVcu68CanMessage(vcu68CanMessage);
        VCU69CanMessage vcu69CanMessage = getLastVCU69MessageFromRedis();
        energySavingCurrentCanMessage.setVcu69CanMessage(vcu69CanMessage);
        VCU70CanMessage vcu70CanMessage = getLastVCU70MessageFromRedis();
        energySavingCurrentCanMessage.setVcu70CanMessage(vcu70CanMessage);
        VCU71CanMessage vcu71CanMessage = getLastVCU71MessageFromRedis();
        energySavingCurrentCanMessage.setVcu71CanMessage(vcu71CanMessage);
        VCU72CanMessage vcu72CanMessage = getLastVCU72MessageFromRedis();
        energySavingCurrentCanMessage.setVcu72CanMessage(vcu72CanMessage);
        VCU75CanMessage vcu75CanMessage = getLastVCU75MessageFromRedis();
        energySavingCurrentCanMessage.setVcu75CanMessage(vcu75CanMessage);
        VCU76CanMessage vcu76CanMessage = getLastVCU76MessageFromRedis();
        energySavingCurrentCanMessage.setVcu76CanMessage(vcu76CanMessage);
        energySavingCurrentCanMessage.setTimestamp(getDateTimeNowTimeStamp());
        return opsEnergySavingCanMessage(esReq, energySavingCurrentCanMessage);
    }

    static QueryESRes getTodayCycleES(QueryESReq esReq){
        Set<String> energySavingTodaySet = zRangeByScore(REDIS_ENERGY_SAVING_DRIVING_CYCLE_ZSET,
                getTodayBeginUnixTime(),getTodayEndUnixTime());
        energySavingLogInfo("getTodayCycleES from "+getTodayBeginUnixTime()
                +" to "+getTodayEndUnixTime());
        energySavingLogInfo("number of energySavingTodaySet="+energySavingTodaySet.size());
        return getCycleESByPeriod(esReq, energySavingTodaySet);
    }

    static QueryESRes getWeeklyCycleES(QueryESReq esReq){
        Set<String> energySavingWeeklySet = zRangeByScore(REDIS_ENERGY_SAVING_DRIVING_CYCLE_ZSET,
                getBeforeOneWeekBeginUnixTime(),getTodayEndUnixTime());
        energySavingLogInfo("getWeeklyCycleES from "+getBeforeOneWeekBeginUnixTime()
                +" to "+getTodayEndUnixTime());
        energySavingLogInfo("number of energySavingWeeklySet="+energySavingWeeklySet.size());
        return getCycleESByPeriod(esReq, energySavingWeeklySet);
    }

    static QueryESRes getCustomerCycleES(QueryESReq esReq){
        Set<String> energySavingCustomerSet = zRangeByScore(REDIS_ENERGY_SAVING_DRIVING_CYCLE_ZSET,
                transfer2UnixTime(esReq.getStartDateTime()),transfer2UnixTime(esReq.getEndDateTime()));
        energySavingLogInfo("getCustomerCycleES from "+transfer2UnixTime(esReq.getStartDateTime())
                +" to "+transfer2UnixTime(esReq.getEndDateTime()));
        energySavingLogInfo("number of energySavingCustomerSet="+energySavingCustomerSet.size());
        return getCycleESByPeriod(esReq, energySavingCustomerSet);
    }

    static QueryESRes getLastCycleES(QueryESReq esReq){
        energySavingLogInfo("esReq="+esReq);
        EnergySavingCanMessage energySavingCanMessage = transferFromJSON2Object(
                getLastOneStringFromZset(REDIS_ENERGY_SAVING_DRIVING_CYCLE_ZSET)
                ,EnergySavingCanMessage.class);
        return opsEnergySavingCanMessage(esReq, energySavingCanMessage);
    }

    static QueryESRes getCycleESByPeriod(QueryESReq esReq, Set<String> energySavingSet){
        energySavingLogInfo("esReq="+esReq);
        EnergySavingCanMessage energySavingCanMessageAll = initialEnergySavingCanMessage();
        energySavingSet.forEach(est -> {
            EnergySavingCanMessage energySavingCanMessage = transferFromJSON2Object(
                    est,EnergySavingCanMessage.class);
            energySavingLogInfo("energySavingCanMessage="+energySavingCanMessage);
            energySavingCanMessageAll.setVcu61CanMessage(energySavingCanMessageAll
                    .getVcu61CanMessage().adding(energySavingCanMessage.getVcu61CanMessage()));
            energySavingCanMessageAll.setVcu62CanMessage(energySavingCanMessageAll
                    .getVcu62CanMessage().adding(energySavingCanMessage.getVcu62CanMessage()));
            energySavingCanMessageAll.setVcu63CanMessage(energySavingCanMessageAll
                    .getVcu63CanMessage().adding(energySavingCanMessage.getVcu63CanMessage()));
            energySavingCanMessageAll.setVcu64CanMessage(energySavingCanMessageAll
                    .getVcu64CanMessage().adding(energySavingCanMessage.getVcu64CanMessage()));
            energySavingCanMessageAll.setVcu65CanMessage(energySavingCanMessageAll
                    .getVcu65CanMessage().adding(energySavingCanMessage.getVcu65CanMessage()));
            energySavingCanMessageAll.setVcu66CanMessage(energySavingCanMessageAll
                    .getVcu66CanMessage().adding(energySavingCanMessage.getVcu66CanMessage()));
            energySavingCanMessageAll.setVcu67CanMessage(energySavingCanMessageAll
                    .getVcu67CanMessage().adding(energySavingCanMessage.getVcu67CanMessage()));
            energySavingCanMessageAll.setVcu68CanMessage(energySavingCanMessageAll
                    .getVcu68CanMessage().adding(energySavingCanMessage.getVcu68CanMessage()));
            energySavingCanMessageAll.setVcu69CanMessage(energySavingCanMessageAll
                    .getVcu69CanMessage().adding(energySavingCanMessage.getVcu69CanMessage()));
            energySavingCanMessageAll.setVcu70CanMessage(energySavingCanMessageAll
                    .getVcu70CanMessage().adding(energySavingCanMessage.getVcu70CanMessage()));
            energySavingCanMessageAll.setVcu71CanMessage(energySavingCanMessageAll
                    .getVcu71CanMessage().adding(energySavingCanMessage.getVcu71CanMessage()));
            energySavingCanMessageAll.setVcu72CanMessage(energySavingCanMessageAll
                    .getVcu72CanMessage().adding(energySavingCanMessage.getVcu72CanMessage()));
            energySavingCanMessageAll.setVcu75CanMessage(energySavingCanMessageAll
                    .getVcu75CanMessage().adding(energySavingCanMessage.getVcu75CanMessage()));
            energySavingCanMessageAll.setVcu76CanMessage(energySavingCanMessageAll
                    .getVcu76CanMessage().adding(energySavingCanMessage.getVcu76CanMessage()));
        });

        return opsEnergySavingCanMessage(esReq, energySavingCanMessageAll);
    }

    static QueryESRes opsEnergySavingCanMessage(QueryESReq esReq, EnergySavingCanMessage energySavingCanMessage){
        //energySavingLogInfo("OpsEnergySavingCanMessage="+energySavingCanMessage);
        VCU61CanMessage vcu61CanMessage = energySavingCanMessage.getVcu61CanMessage();
        //energySavingLogInfo("vcu61CanMessage="+vcu61CanMessage);
        VCU62CanMessage vcu62CanMessage = energySavingCanMessage.getVcu62CanMessage();
        //energySavingLogInfo("vcu62CanMessage="+vcu62CanMessage);
        ESSummary esSummary = ESSummary.builder()
                .energyPer100KM(calEnergyKWHPer100KM(vcu61CanMessage,vcu62CanMessage))
                .sumEnergy(calEnergyKWH(vcu61CanMessage))
                .sumEnergyCost(calEnergyCost(vcu61CanMessage))
                .build();
        //energySavingLogInfo("esSummary="+esSummary.toString());
        ComponentsPercentSum componentsPercentSum = ComponentsPercentSum.builder()
                .RESTEnergyPert(calCompRESTEnergyPert(vcu61CanMessage,vcu62CanMessage))
                .ACEnergyPert(calCompACEnergyPert(vcu61CanMessage,vcu62CanMessage))
                .DCDCEngergyPert(calCompDCDCEnergyPert(vcu61CanMessage))
                .MTEnergyPert(calCompMTEnergyPert(vcu61CanMessage))
                .BRKEnergyPert(calCompBRKEnergyPert(vcu61CanMessage,vcu62CanMessage))
                .build();
        //energySavingLogInfo("componentsPercentSum="+componentsPercentSum.toString());
        VCU63CanMessage vcu63CanMessage = energySavingCanMessage.getVcu63CanMessage();
        //energySavingLogInfo("vcu63CanMessage="+vcu63CanMessage);
        VCU64CanMessage vcu64CanMessage = energySavingCanMessage.getVcu64CanMessage();
        //energySavingLogInfo("vcu64CanMessage="+vcu64CanMessage);
        VCU65CanMessage vcu65CanMessage = energySavingCanMessage.getVcu65CanMessage();
        //energySavingLogInfo("vcu65CanMessage="+vcu65CanMessage);
        VCU66CanMessage vcu66CanMessage = energySavingCanMessage.getVcu66CanMessage();
        //energySavingLogInfo("vcu66CanMessage="+vcu66CanMessage);
        VCU67CanMessage vcu67CanMessage = energySavingCanMessage.getVcu67CanMessage();
        //energySavingLogInfo("vcu67CanMessage="+vcu67CanMessage);
        VCU68CanMessage vcu68CanMessage = energySavingCanMessage.getVcu68CanMessage();
        //energySavingLogInfo("vcu68CanMessage="+vcu68CanMessage);
        VCU69CanMessage vcu69CanMessage = energySavingCanMessage.getVcu69CanMessage();
        //energySavingLogInfo("vcu69CanMessage="+vcu69CanMessage);
        VCU70CanMessage vcu70CanMessage = energySavingCanMessage.getVcu70CanMessage();
        //energySavingLogInfo("vcu70CanMessage="+vcu70CanMessage);
        VCU71CanMessage vcu71CanMessage = energySavingCanMessage.getVcu71CanMessage();
        //energySavingLogInfo("vcu71CanMessage="+vcu71CanMessage);
        VCU72CanMessage vcu72CanMessage = energySavingCanMessage.getVcu72CanMessage();
        //energySavingLogInfo("vcu72CanMessage="+vcu72CanMessage);
        VCU75CanMessage vcu75CanMessage = energySavingCanMessage.getVcu75CanMessage();
        //SenergySavingLogInfo("vcu75CanMessage="+vcu75CanMessage);
        VCU76CanMessage vcu76CanMessage = energySavingCanMessage.getVcu76CanMessage();
        //energySavingLogInfo("vcu76CanMessage="+vcu76CanMessage);
        Per100KMByDM per100KMByDM = Per100KMByDM.builder()
                .CustomerDM(calCSTEnergyKWHPer100KM(vcu75CanMessage,vcu76CanMessage))
                .EconomyDM(calECOEnergyKWHPer100KM(vcu65CanMessage,vcu66CanMessage))
                .NormalDM(calNOREnergyKWHPer100KM(vcu67CanMessage,vcu68CanMessage))
                .PowerDM(calPOWEnergyKWHPer100KM(vcu69CanMessage,vcu70CanMessage))
                .EPowerDM(calEPMEnergyKWHPer100KM(vcu71CanMessage,vcu72CanMessage))
                .EEconomyDM(calEEMEnergyKWHPer100KM(vcu63CanMessage,vcu64CanMessage))
                .build();
        //energySavingLogInfo("per100KMByDM="+per100KMByDM.toString());
        Map<String,ComponentsPercent> cpByDM = new HashMap<>();
        ComponentsPercent componentsPercentEEM = ComponentsPercent.builder()
                .RESTEnergyPert(calEEMRESTEnergyPert(vcu63CanMessage,vcu64CanMessage))
                .ACEnergyPert(calEEMACEnergyPert(vcu63CanMessage,vcu64CanMessage))
                .DCDCEngergyPert(calEEMDCDCEnergyPert(vcu63CanMessage))
                .MTEnergyPert(calEEMMTEnergyPert(vcu63CanMessage))
                .build();
        //energySavingLogInfo("componentsPercentEEM="+componentsPercentEEM.toString());
        cpByDM.put(DRIVING_MODE_ABB_EEM,componentsPercentEEM);
        ComponentsPercent componentsPercentECO = ComponentsPercent.builder()
                .RESTEnergyPert(calECORESTEnergyPert(vcu65CanMessage,vcu66CanMessage))
                .ACEnergyPert(calECOACEnergyPert(vcu65CanMessage,vcu66CanMessage))
                .DCDCEngergyPert(calECODCDCEnergyPert(vcu65CanMessage))
                .MTEnergyPert(calECOMTEnergyPert(vcu65CanMessage))
                .build();
        //energySavingLogInfo("componentsPercentECO="+componentsPercentECO);
        cpByDM.put(DRIVING_MODE_ABB_ECO,componentsPercentECO);
        ComponentsPercent componentsPercentNOR = ComponentsPercent.builder()
                .RESTEnergyPert(calNORRESTEnergyPert(vcu67CanMessage,vcu68CanMessage))
                .ACEnergyPert(calNORACEnergyPert(vcu67CanMessage,vcu68CanMessage))
                .DCDCEngergyPert(calNORDCDCEnergyPert(vcu67CanMessage))
                .MTEnergyPert(calNORMTEnergyPert(vcu67CanMessage))
                .build();
        //energySavingLogInfo("componentsPercentNOR="+componentsPercentNOR);
        cpByDM.put(DRIVING_MODE_ABB_NOR,componentsPercentNOR);
        ComponentsPercent componentsPercentEPM = ComponentsPercent.builder()
                .RESTEnergyPert(calEPMRESTEnergyPert(vcu71CanMessage,vcu72CanMessage))
                .ACEnergyPert(calEPMACEnergyPert(vcu71CanMessage,vcu72CanMessage))
                .DCDCEngergyPert(calEPMDCDCEnergyPert(vcu71CanMessage))
                .MTEnergyPert(calEPMMTEnergyPert(vcu71CanMessage))
                .build();
        //energySavingLogInfo("componentsPercentEPM="+componentsPercentEPM);
        cpByDM.put(DRIVING_MODE_ABB_EPM,componentsPercentEPM);
        ComponentsPercent componentsPercentPOW = ComponentsPercent.builder()
                .RESTEnergyPert(calPOWRESTEnergyPert(vcu69CanMessage,vcu70CanMessage))
                .ACEnergyPert(calPOWACEnergyPert(vcu69CanMessage,vcu70CanMessage))
                .DCDCEngergyPert(calPOWDCDCEnergyPert(vcu69CanMessage))
                .MTEnergyPert(calPOWMTEnergyPert(vcu69CanMessage))
                .build();
        //energySavingLogInfo("componentsPercentPOW="+componentsPercentPOW);
        cpByDM.put(DRIVING_MODE_ABB_POW,componentsPercentPOW);
        ComponentsPercent componentsPercentCST = ComponentsPercent.builder()
                .RESTEnergyPert(calCSTRESTEnergyPert(vcu75CanMessage,vcu76CanMessage))
                .ACEnergyPert(calCSTACEnergyPert(vcu75CanMessage,vcu76CanMessage))
                .DCDCEngergyPert(calCSTDCDCEnergyPert(vcu75CanMessage))
                .MTEnergyPert(calCSTMTEnergyPert(vcu75CanMessage))
                .build();
        //energySavingLogInfo("componentsPercentCST="+componentsPercentCST);
        cpByDM.put(DRIVING_MODE_ABB_CST,componentsPercentCST);
        QueryESRes queryESRes = QueryESRes.builder()
                .driver(esReq.getDriver())
                .dateTime(esReq.getDateTime())
                .esSummary(esSummary)
                .componentsPercentSum(componentsPercentSum)
                .per100KMByDM(per100KMByDM)
                .CPByDM(cpByDM)
                .responseCode(RESPONSE_CODE_SUCCESS)
                .build();
        //energySavingLogInfo("queryESRes="+queryESRes);
        return queryESRes;
    }

    static ESCurrentCycleNotice getESCurrentCycleInfoNotice(){
        QueryESReq queryESReq = QueryESReq.builder()
                .dateTime(getDateTimeString())
                .driver(createDummyDriver())
                .build();
        QueryESRes queryESRes = getCurrentES(queryESReq);
        ESCurrentCycleInfo esCurrentCycleInfo = ESCurrentCycleInfo.builder()
                .esSummary(queryESRes.getEsSummary())
                .componentsPercentSum(queryESRes.getComponentsPercentSum())
                .CPByDM(queryESRes.getCPByDM())
                .per100KMByDM(queryESRes.getPer100KMByDM())
                .build();
        ESCurrentCycleNotice esCurrentCycleNotice = ESCurrentCycleNotice.builder()
                .esCurrentCycleInfo(esCurrentCycleInfo)
                .dateTime(getDateTimeString())
                .build();
        return esCurrentCycleNotice;
    }

}
