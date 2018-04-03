package com.uaes.esw.gwmc30demo.domain.service;

import com.uaes.esw.gwmc30demo.domain.model.scenario.energySaving.*;
import com.uaes.esw.gwmc30demo.domain.repository.energySaving.IEnergySavingRepository;
import com.uaes.esw.gwmc30demo.infrastructure.utils.DateTimeUtils;

import static com.uaes.esw.gwmc30demo.domain.repository.energySaving.IEnergySavingRepository.createCurrentZero;
import static com.uaes.esw.gwmc30demo.domain.repository.energySaving.IEnergySavingRepository.getCurrentES;
import static com.uaes.esw.gwmc30demo.domain.repository.energySaving.IEnergySavingRepository.getHVPowerOn;

public interface EnergySavingDomainService {
    static QueryESRes queryESCurrentDomainService(QueryESReq esReq){
        if(!isHVPowerOn())
            return createCurrentZero(esReq);
        return getCurrentES(esReq);
    }
    static QueryESRes queryESLastCycleDomainService(QueryESReq esReq){
        return createCurrentZero(esReq);
    }

    static QueryESRes queryESTodayDomainService(QueryESReq esReq){
        return createCurrentZero(esReq);
    }

    static QueryESRes queryESThisWeekDomainService(QueryESReq esReq){
        return createCurrentZero(esReq);
    }

    static QueryESRes queryESCustomerDomainService(QueryESReq esReq){
        return createCurrentZero(esReq);
    }

    static ESRemindNotice createESRemind(){
        ESRemind esRemind = IEnergySavingRepository.getRemind();
        ESRemindNotice esRemindNotice = ESRemindNotice.builder()
                .esRemind(esRemind).dateTime(DateTimeUtils.getDateTimeString()).build();
        return esRemindNotice;
    }

    static boolean isHVPowerOn(){
        int hvPowerOnValue = getHVPowerOn();
        if(hvPowerOnValue == 0)
            return false;
        return true;
    }

}
