package com.uaes.esw.gwmc30demo.domain.service;

import com.uaes.esw.gwmc30demo.application.assembler.DriverService;
import com.uaes.esw.gwmc30demo.domain.model.entity.driver.Driver;
import com.uaes.esw.gwmc30demo.domain.model.entity.driver.IDriver;
import com.uaes.esw.gwmc30demo.domain.model.scenario.logInOut.LogInReq;
import com.uaes.esw.gwmc30demo.domain.model.scenario.logInOut.LogInRes;
import com.uaes.esw.gwmc30demo.domain.repository.vehicle.IVehicleRepository;

import static com.uaes.esw.gwmc30demo.constant.CommonConstants.*;
import static com.uaes.esw.gwmc30demo.infrastructure.json.JSONUtility.transferFromObject2JSON;

public interface LogInDomainService {
    static LogInRes logInDomainService(LogInReq logInReq){
        Driver beforeLoginDriver = logInReq.getDriver();
        Driver driver = IDriver.getDriverInfo(beforeLoginDriver.getCellPhone());
        LogInRes logInRes = LogInRes.builder().dateTime(logInReq.getDateTime()).build();
        boolean isRegister = IDriver.isRegister(beforeLoginDriver);
        if(isRegister){
            boolean isAlreadyLogin = IDriver.isLogin(beforeLoginDriver);
            if(isAlreadyLogin){
                logInRes.setResponseCode(RESPONSE_CODE_EXISTED);
                logInRes.setDriver(driver);
            }
            else{
                boolean isLoginSuccessful = IDriver.login(beforeLoginDriver);
                if(isLoginSuccessful){
                    logInRes.setResponseCode(RESPONSE_CODE_SUCCESS);
                    logInRes.setDriver(driver);
                    //send default driving mode to vehicle
                    IVehicleRepository.sendDefaultDM2Vehicle(driver);
                    IVehicleRepository.sendCurrentDM2Vehicle(driver);
                }
                else{
                    logInRes.setResponseCode(RESPONSE_CODE_FAILURE);
                    logInRes.setDriver(beforeLoginDriver);
                }
            }
        }else{
            logInRes.setResponseCode(RESPONSE_CODE_NOTREGISTER);
            logInRes.setDriver(beforeLoginDriver);
        }
        return logInRes;
    }

    static boolean isLogInDomainService(LogInReq logInReq){
        Driver driver = logInReq.getDriver();
        return IDriver.isLogin(driver);
    }

    static boolean notLogInDomainService(Driver driver, String dateTime){
        LogInReq logInReq = LogInReq.builder().driver(driver)
                .dateTime(dateTime).build();
        Boolean isLogIn = DriverService.isLogIn(transferFromObject2JSON(logInReq));
        return !isLogIn;
    }

    static String feedbackNotLogInDomainService(Driver driver, String dateTime){
        LogInRes logInRes = LogInRes.builder().driver(driver)
                .dateTime(dateTime)
                .responseCode(RESPONSE_CODE_NOTLOGIN).build();
        return transferFromObject2JSON(logInRes);
    }
}
