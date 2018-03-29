package com.uaes.esw.gwmc30demo.application.assembler;

import com.uaes.esw.gwmc30demo.domain.model.scenario.energySaving.ESRemindNotice;
import com.uaes.esw.gwmc30demo.domain.model.scenario.energySaving.QueryESReq;
import com.uaes.esw.gwmc30demo.domain.model.scenario.energySaving.QueryESRes;
import com.uaes.esw.gwmc30demo.domain.service.LogInDomainService;
import com.uaes.esw.gwmc30demo.infrastructure.websocket.WebSocketHandler;

import static com.uaes.esw.gwmc30demo.domain.service.EnergySavingDomainService.*;
import static com.uaes.esw.gwmc30demo.infrastructure.json.JSONUtility.transferFromJSON2Object;
import static com.uaes.esw.gwmc30demo.infrastructure.json.JSONUtility.transferFromObject2JSON;

public interface EnergySavingService {
    static String queryESThisTime(String queryStr){
        QueryESReq esReq = transferFromJSON2Object(queryStr, QueryESReq.class);
        if(LogInDomainService.notLogInDomainService(esReq.getDriver(),esReq.getDateTime()))
            return LogInDomainService.feedbackNotLogInDomainService(esReq.getDriver(),esReq.getDateTime());
        QueryESRes esRes =queryESThisTimeDomainService(esReq);
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

    static void sendOutRemindNotice(){
        WebSocketHandler.sendStrMessage(transferFromObject2JSON(createESRemind()));
    }
}
