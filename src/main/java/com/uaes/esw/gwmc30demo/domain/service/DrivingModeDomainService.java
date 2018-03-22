package com.uaes.esw.gwmc30demo.domain.service;

import com.uaes.esw.gwmc30demo.domain.model.driver.*;
import com.uaes.esw.gwmc30demo.domain.model.drivingModeSce.QueryDMReq;
import com.uaes.esw.gwmc30demo.domain.model.drivingModeSce.QueryDMRes;
import com.uaes.esw.gwmc30demo.domain.model.drivingModeSce.SetDMReq;
import com.uaes.esw.gwmc30demo.domain.model.drivingModeSce.SetDMRes;
import com.uaes.esw.gwmc30demo.domain.repository.vehicle.IVehicleRepository;

import static com.uaes.esw.gwmc30demo.constant.CommonConstants.RESPONSE_CODE_SUCCESS;

public interface DrivingModeDomainService {
    static QueryDMRes queryDrivingModeDomainService(QueryDMReq queryDMReq){
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
        return queryDMRes;
    }

    static SetDMRes setDefaultDrivingModeDomainService(SetDMReq setDMReq){
        IVehicleRepository.setDefaultDM(setDMReq.getDriver());
        Driver driver = IDriver.getDriverInfo(setDMReq.getDriver().getCellPhone());
        SetDMRes setDMRes = SetDMRes.builder().dateTime(setDMReq.getDateTime())
                .driver(driver).responseCode(RESPONSE_CODE_SUCCESS).build();
        return setDMRes;
    }

    static SetDMRes setCurrentDrivingModeDomainService(SetDMReq setDMReq){
        IVehicleRepository.setCurrentDM(setDMReq.getDriver());
        Driver driver = IDriver.getDriverInfo(setDMReq.getDriver().getCellPhone());
        SetDMRes setDMRes = SetDMRes.builder().dateTime(setDMReq.getDateTime())
                .driver(driver).responseCode(RESPONSE_CODE_SUCCESS).build();
        return setDMRes;
    }
}
