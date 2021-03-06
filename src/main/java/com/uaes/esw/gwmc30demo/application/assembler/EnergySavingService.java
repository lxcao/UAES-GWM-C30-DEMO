package com.uaes.esw.gwmc30demo.application.assembler;

import com.uaes.esw.gwmc30demo.domain.model.scenario.energySaving.QueryESReq;
import com.uaes.esw.gwmc30demo.domain.model.scenario.energySaving.QueryESRes;
import com.uaes.esw.gwmc30demo.domain.service.LogInDomainService;
import com.uaes.esw.gwmc30demo.infrastructure.websocket.WebSocketHandler;

import static com.uaes.esw.gwmc30demo.domain.service.EnergySavingDomainService.*;
import static com.uaes.esw.gwmc30demo.domain.service.VehicleDomainService.isHVPowerOnNow;
import static com.uaes.esw.gwmc30demo.infrastructure.json.JSONUtility.transferFromJSON2Object;
import static com.uaes.esw.gwmc30demo.infrastructure.json.JSONUtility.transferFromObject2JSON;

public interface EnergySavingService {

    static String queryESCurrent(String queryStr){
        QueryESReq esReq = transferFromJSON2Object(queryStr, QueryESReq.class);
        if(LogInDomainService.notLogInDomainService(esReq.getDriver(),esReq.getDateTime()))
            return LogInDomainService.feedbackNotLogInDomainService(esReq.getDriver(),esReq.getDateTime());
        QueryESRes esRes =queryESCurrentDomainService(esReq);
        return transferFromObject2JSON(esRes);
    }
    static String queryESLastCycle(String queryStr){
        QueryESReq esReq = transferFromJSON2Object(queryStr, QueryESReq.class);
        if(LogInDomainService.notLogInDomainService(esReq.getDriver(),esReq.getDateTime()))
            return LogInDomainService.feedbackNotLogInDomainService(esReq.getDriver(),esReq.getDateTime());
        QueryESRes esRes =queryESLastCycleDomainService(esReq);
        return transferFromObject2JSON(esRes);
    }

    static String queryESToday(String queryStr){
        QueryESReq esReq = transferFromJSON2Object(queryStr, QueryESReq.class);
        if(LogInDomainService.notLogInDomainService(esReq.getDriver(),esReq.getDateTime()))
            return LogInDomainService.feedbackNotLogInDomainService(esReq.getDriver(),esReq.getDateTime());
        QueryESRes esRes =queryESTodayDomainService(esReq);
        return transferFromObject2JSON(esRes);
    }

    static String queryESThisWeek(String queryStr){
        QueryESReq esReq = transferFromJSON2Object(queryStr, QueryESReq.class);
        if(LogInDomainService.notLogInDomainService(esReq.getDriver(),esReq.getDateTime()))
            return LogInDomainService.feedbackNotLogInDomainService(esReq.getDriver(),esReq.getDateTime());
        QueryESRes esRes =queryESThisWeekDomainService(esReq);
        return transferFromObject2JSON(esRes);
    }

    static String queryESCustomer(String queryStr){
        QueryESReq esReq = transferFromJSON2Object(queryStr, QueryESReq.class);
        if(LogInDomainService.notLogInDomainService(esReq.getDriver(),esReq.getDateTime()))
            return LogInDomainService.feedbackNotLogInDomainService(esReq.getDriver(),esReq.getDateTime());
        QueryESRes esRes =queryESCustomerDomainService(esReq);
        return transferFromObject2JSON(esRes);
    }

    static void sendOutEnergySavingRemindNotice(){
        WebSocketHandler.sendStrMessage(transferFromObject2JSON(createESRemind()));
    }

    static void sendOutEnergySavingCurrentCycleNotice(){
        if(isHVPowerOnNow())
            WebSocketHandler.sendStrMessage(transferFromObject2JSON(getESCurrentCycleInfoNotice()));
    }
}
