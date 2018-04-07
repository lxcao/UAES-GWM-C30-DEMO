package com.uaes.esw.gwmc30demo.domain.service;

import com.uaes.esw.gwmc30demo.domain.model.scenario.energySaving.*;
import com.uaes.esw.gwmc30demo.domain.repository.energySaving.IEnergySavingRepository;
import com.uaes.esw.gwmc30demo.infrastructure.utils.DateTimeUtils;

import static com.uaes.esw.gwmc30demo.domain.repository.energySaving.IEnergySavingRepository.*;

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
        ESRemindNotice esRemindNotice = ESRemindNotice.builder()
                .esRemind(esRemind).dateTime(DateTimeUtils.getDateTimeString()).build();
        return esRemindNotice;
    }

    static boolean isHVPowerOnNow(){
        int hvPowerOnValue = getHVPowerOnStatusNow();
        if(hvPowerOnValue == 0)
            return false;
        return true;
    }

    static boolean isHVPowerStatusChangeFromOn2Off(){
        int hvPowerOnNowValue = getHVPowerOnStatusNow();
        int hvPowerOnPreviousValue = getHVPowerOnStatusPrevious();
        if(hvPowerOnPreviousValue == 1 && hvPowerOnNowValue == 0)
            return true;
        return false;
    }

    static void getAndStoreLastEnergySavingCycle(){
        if(isHVPowerStatusChangeFromOn2Off())
            storeLastEnergySavingCycle();
    }

}
