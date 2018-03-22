package com.uaes.esw.gwmc30demo.application.assembler;

import com.uaes.esw.gwmc30demo.domain.model.drivingModeSce.QueryDMReq;
import com.uaes.esw.gwmc30demo.domain.model.drivingModeSce.QueryDMRes;
import com.uaes.esw.gwmc30demo.domain.model.drivingModeSce.SetDMReq;
import com.uaes.esw.gwmc30demo.domain.model.drivingModeSce.SetDMRes;
import com.uaes.esw.gwmc30demo.domain.service.DrivingModeDomainService;

import static com.uaes.esw.gwmc30demo.infrastructure.json.JSONUtility.transferFromJSON2Object;
import static com.uaes.esw.gwmc30demo.infrastructure.json.JSONUtility.transferFromObject2JSON;

public interface VehicleService {


    static String queryDrivingMode(String queryStr){
        QueryDMReq queryDMReq = transferFromJSON2Object(queryStr, QueryDMReq.class);
        QueryDMRes queryDMRes = DrivingModeDomainService.queryDrivingModeDomainService(queryDMReq);
        return transferFromObject2JSON(queryDMRes);
    }

    static String setDefaultDrivingMode(String setStr){
        SetDMReq setDMReq = transferFromJSON2Object(setStr, SetDMReq.class);
        SetDMRes setDMRes = DrivingModeDomainService.setDefaultDrivingModeDomainService(setDMReq);
        return transferFromObject2JSON(setDMRes);
    }

    static String setCurrentDrivingMode(String setStr){
        SetDMReq setDMReq = transferFromJSON2Object(setStr, SetDMReq.class);
        SetDMRes setDMRes = DrivingModeDomainService.setCurrentDrivingModeDomainService(setDMReq);
        return transferFromObject2JSON(setDMRes);
    }

}
