package com.uaes.esw.gwmc30demo.application.service;

import com.uaes.esw.gwmc30demo.domain.model.driver.*;
import com.uaes.esw.gwmc30demo.domain.repository.vehicle.IVehicleRepository;


import static com.uaes.esw.gwmc30demo.constant.CommonConstants.RESPONSE_CODE_SUCCESS;
import static com.uaes.esw.gwmc30demo.infrastructure.json.JSONUtility.transferFromJSON2Object;
import static com.uaes.esw.gwmc30demo.infrastructure.json.JSONUtility.transferFromObject2JSON;

public interface VehicleService {


    static String queryDrivingMode(String queryStr){
        QueryDMReq queryDMReq = transferFromJSON2Object(queryStr, QueryDMReq.class);
        Driver driver = IDriver.getDriverInfo(queryDMReq.getDriver().getCellPhone());
        QueryDMRes queryDMRes = QueryDMRes.builder().dateTime(queryDMReq.getDateTime())
                .driver(driver).responseCode(RESPONSE_CODE_SUCCESS).build();
/*        String defaultDrivingMode = driver.getDefaultDM();
        System.out.println("defaultDrivingMode="+defaultDrivingMode);
        DrivingMode defaultDM = IVehicleRepository.getDrivingMode(defaultDrivingMode,driver);
        String currentDrivingMode = driver.getCurrentDM();
        System.out.println("currentDrivingMode="+currentDrivingMode);
        DrivingMode currentDM = IVehicleRepository.getDrivingMode(currentDrivingMode,driver);
        List<DrivingMode> DrivingModeList = new ArrayList<>();
        DrivingModeList.add(defaultDM);
        DrivingModeList.add(currentDM);
        queryDMRes.setDrivingMode(DrivingModeList);*/
        queryDMRes.setDrivingMode(IVehicleRepository.getAllDrivingMode(driver));
        return transferFromObject2JSON(queryDMRes);
    }

    static String setDefaultDrivingMode(String setStr){
        SetDMReq setDMReq = transferFromJSON2Object(setStr, SetDMReq.class);
        IVehicleRepository.setDefaultDM(setDMReq.getDriver());
        Driver driver = IDriver.getDriverInfo(setDMReq.getDriver().getCellPhone());
        SetDMRes setDMRes = SetDMRes.builder().dateTime(setDMReq.getDateTime())
                .driver(driver).responseCode(RESPONSE_CODE_SUCCESS).build();
        return transferFromObject2JSON(setDMRes);
    }

    static String setCurrentDrivingMode(String setStr){
        SetDMReq setDMReq = transferFromJSON2Object(setStr, SetDMReq.class);
        IVehicleRepository.setCurrentDM(setDMReq.getDriver());
        Driver driver = IDriver.getDriverInfo(setDMReq.getDriver().getCellPhone());
        SetDMRes setDMRes = SetDMRes.builder().dateTime(setDMReq.getDateTime())
                .driver(driver).responseCode(RESPONSE_CODE_SUCCESS).build();
        return transferFromObject2JSON(setDMRes);
    }

}
