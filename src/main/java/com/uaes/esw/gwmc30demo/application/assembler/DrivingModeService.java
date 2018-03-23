package com.uaes.esw.gwmc30demo.application.assembler;

import com.uaes.esw.gwmc30demo.domain.model.scenario.drivingMode.QueryDMReq;
import com.uaes.esw.gwmc30demo.domain.model.scenario.drivingMode.QueryDMRes;
import com.uaes.esw.gwmc30demo.domain.model.scenario.drivingMode.SetDMReq;
import com.uaes.esw.gwmc30demo.domain.model.scenario.drivingMode.SetDMRes;
import com.uaes.esw.gwmc30demo.domain.service.DrivingModeDomainService;
import com.uaes.esw.gwmc30demo.domain.service.LogInDomainService;

import static com.uaes.esw.gwmc30demo.infrastructure.json.JSONUtility.transferFromJSON2Object;
import static com.uaes.esw.gwmc30demo.infrastructure.json.JSONUtility.transferFromObject2JSON;

public interface DrivingModeService {

    static String queryDrivingMode(String queryStr){
        QueryDMReq queryDMReq = transferFromJSON2Object(queryStr, QueryDMReq.class);
        if(LogInDomainService.notLogInDomainService(queryDMReq.getDriver(),queryDMReq.getDateTime()))
            return LogInDomainService.feedbackNotLogInDomainService(queryDMReq.getDriver(),queryDMReq.getDateTime());
        QueryDMRes queryDMRes = DrivingModeDomainService.queryDrivingModeDomainService(queryDMReq);
        return transferFromObject2JSON(queryDMRes);
    }

    static String setDefaultDrivingMode(String setStr){
        SetDMReq setDMReq = transferFromJSON2Object(setStr, SetDMReq.class);
        if(LogInDomainService.notLogInDomainService(setDMReq.getDriver(),setDMReq.getDateTime()))
            return LogInDomainService.feedbackNotLogInDomainService(setDMReq.getDriver(),setDMReq.getDateTime());
        SetDMRes setDMRes = DrivingModeDomainService.setDefaultDrivingModeDomainService(setDMReq);
        return transferFromObject2JSON(setDMRes);
    }

    static String setCurrentDrivingMode(String setStr){
        SetDMReq setDMReq = transferFromJSON2Object(setStr, SetDMReq.class);
        if(LogInDomainService.notLogInDomainService(setDMReq.getDriver(),setDMReq.getDateTime()))
            return LogInDomainService.feedbackNotLogInDomainService(setDMReq.getDriver(),setDMReq.getDateTime());
        SetDMRes setDMRes = DrivingModeDomainService.setCurrentDrivingModeDomainService(setDMReq);
        return transferFromObject2JSON(setDMRes);
    }

    static String setCustomerDrivingMode(String setStr){
        SetDMReq setDMReq = transferFromJSON2Object(setStr, SetDMReq.class);
        if(LogInDomainService.notLogInDomainService(setDMReq.getDriver(),setDMReq.getDateTime()))
            return LogInDomainService.feedbackNotLogInDomainService(setDMReq.getDriver(),setDMReq.getDateTime());
        SetDMRes setDMRes = DrivingModeDomainService.setCustomerDrivingModeDomainService(setDMReq);
        return transferFromObject2JSON(setDMRes);
    }

}
