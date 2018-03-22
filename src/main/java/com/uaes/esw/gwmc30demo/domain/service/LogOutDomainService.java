package com.uaes.esw.gwmc30demo.domain.service;

import com.uaes.esw.gwmc30demo.domain.model.driver.Driver;
import com.uaes.esw.gwmc30demo.domain.model.driver.IDriver;
import com.uaes.esw.gwmc30demo.domain.model.logInOutSce.LogOutReq;
import com.uaes.esw.gwmc30demo.domain.model.logInOutSce.LogOutRes;

import static com.uaes.esw.gwmc30demo.constant.CommonConstants.RESPONSE_CODE_FAILURE;
import static com.uaes.esw.gwmc30demo.constant.CommonConstants.RESPONSE_CODE_SUCCESS;

public interface LogOutDomainService {
    static LogOutRes logOutDomainService(LogOutReq logOutReq){
        Driver driver = IDriver.getDriverInfo(logOutReq.getDriver().getCellPhone());
        LogOutRes logOutRes = LogOutRes.builder().driver(driver)
                .dateTime(logOutReq.getDateTime()).build();
        boolean isLogoutSuccessful = IDriver.logout(driver);
        if(isLogoutSuccessful)
            logOutRes.setResponseCode(RESPONSE_CODE_SUCCESS);
        else
            logOutRes.setResponseCode(RESPONSE_CODE_FAILURE);
        return logOutRes;
    }
}
