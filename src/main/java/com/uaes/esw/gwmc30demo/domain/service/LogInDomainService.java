package com.uaes.esw.gwmc30demo.domain.service;

import com.uaes.esw.gwmc30demo.domain.model.driver.Driver;
import com.uaes.esw.gwmc30demo.domain.model.driver.IDriver;
import com.uaes.esw.gwmc30demo.domain.model.logInOutSce.LogInReq;
import com.uaes.esw.gwmc30demo.domain.model.logInOutSce.LogInRes;

import static com.uaes.esw.gwmc30demo.constant.CommonConstants.RESPONSE_CODE_EXISTED;
import static com.uaes.esw.gwmc30demo.constant.CommonConstants.RESPONSE_CODE_FAILURE;
import static com.uaes.esw.gwmc30demo.constant.CommonConstants.RESPONSE_CODE_SUCCESS;

public interface LogInDomainService {
    static LogInRes logInDomainService(LogInReq logInReq){
        Driver beforeLoginDriver = logInReq.getDriver();
        Driver driver = IDriver.getDriverInfo(beforeLoginDriver.getCellPhone());
        LogInRes logInRes = LogInRes.builder().dateTime(logInReq.getDateTime()).build();
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
            }
            else{
                logInRes.setResponseCode(RESPONSE_CODE_FAILURE);
                logInRes.setDriver(beforeLoginDriver);
            }
        }
        return logInRes;
    }
}
