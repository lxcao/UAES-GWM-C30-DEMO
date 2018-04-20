package com.uaes.esw.gwmc30demo.domain.service;

import com.uaes.esw.gwmc30demo.domain.model.entity.driver.*;
import com.uaes.esw.gwmc30demo.domain.model.entity.vehicle.DrivingMode;
import com.uaes.esw.gwmc30demo.domain.model.scenario.drivingMode.QueryDMReq;
import com.uaes.esw.gwmc30demo.domain.model.scenario.drivingMode.QueryDMRes;
import com.uaes.esw.gwmc30demo.domain.model.scenario.drivingMode.SetDMReq;
import com.uaes.esw.gwmc30demo.domain.model.scenario.drivingMode.SetDMRes;
import com.uaes.esw.gwmc30demo.domain.repository.drivingMode.IDrivingModeRepository;
import com.uaes.esw.gwmc30demo.domain.repository.vehicle.IVehicleRepository;

import static com.uaes.esw.gwmc30demo.constant.CommonConstants.RESPONSE_CODE_SUCCESS;
import static com.uaes.esw.gwmc30demo.constant.DrivingModeConstants.DRIVING_MODE_CST;

public interface DrivingModeDomainService {
    static QueryDMRes queryDrivingModeDomainService(QueryDMReq queryDMReq){
        Driver driver = IDriver.getDriverInfo(queryDMReq.getDriver().getCellPhone());
        QueryDMRes queryDMRes = QueryDMRes.builder().dateTime(queryDMReq.getDateTime())
                .driver(driver).responseCode(RESPONSE_CODE_SUCCESS).build();
        queryDMRes.setDrivingMode(IDrivingModeRepository.getAllDrivingMode(driver));
        queryDMRes.setVehicle(IVehicleRepository.getVehicleSnapshot(driver.getVin()));
        return queryDMRes;
    }

    static SetDMRes setDefaultDrivingModeDomainService(SetDMReq setDMReq){
        IDrivingModeRepository.setDefaultDM(setDMReq.getDriver());
        DrivingMode setDM = setDMReq.getDrivingMode();
        if(DRIVING_MODE_CST.equals(setDM.getDrivingModeType())){
            setCustomerDrivingModeDomainService(setDMReq);
        }
        Driver driver = IDriver.getDriverInfo(setDMReq.getDriver().getCellPhone());
        SetDMRes setDMRes = SetDMRes.builder().dateTime(setDMReq.getDateTime())
                .driver(driver).responseCode(RESPONSE_CODE_SUCCESS).build();
        return setDMRes;
    }

    static SetDMRes setCurrentDrivingModeDomainService(SetDMReq setDMReq){
        IDrivingModeRepository.setCurrentDM(setDMReq.getDriver());
        DrivingMode setDM = setDMReq.getDrivingMode();
        if(DRIVING_MODE_CST.equals(setDM.getDrivingModeType())){
            setCustomerDrivingModeDomainService(setDMReq);
        }
        Driver driver = IDriver.getDriverInfo(setDMReq.getDriver().getCellPhone());
        // set current driving mode to vehicle
        IVehicleRepository.sendCurrentDM2Vehicle(driver);
        SetDMRes setDMRes = SetDMRes.builder().dateTime(setDMReq.getDateTime())
                .driver(driver).responseCode(RESPONSE_CODE_SUCCESS).build();
        return setDMRes;
    }

    static SetDMRes setCustomerDrivingModeDomainService(SetDMReq setDMReq){
        IDrivingModeRepository.setCustomerDM(setDMReq.getDriver(),setDMReq.getDrivingMode());
        Driver driver = IDriver.getDriverInfo(setDMReq.getDriver().getCellPhone());
        SetDMRes setDMRes = SetDMRes.builder().dateTime(setDMReq.getDateTime())
                .driver(driver).responseCode(RESPONSE_CODE_SUCCESS).build();
        return setDMRes;
    }
}
