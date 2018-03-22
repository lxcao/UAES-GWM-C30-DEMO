package com.uaes.esw.gwmc30demo.domain.service;

import com.uaes.esw.gwmc30demo.domain.model.driver.Driver;
import com.uaes.esw.gwmc30demo.domain.model.driver.IDriver;
import com.uaes.esw.gwmc30demo.domain.model.logInOutSce.RegisterReq;
import com.uaes.esw.gwmc30demo.domain.model.logInOutSce.RegisterRes;

import static com.uaes.esw.gwmc30demo.constant.CommonConstants.RESPONSE_CODE_EXISTED;
import static com.uaes.esw.gwmc30demo.constant.CommonConstants.RESPONSE_CODE_FAILURE;
import static com.uaes.esw.gwmc30demo.constant.CommonConstants.RESPONSE_CODE_SUCCESS;

public interface RegisterDomainService {
    static RegisterRes registerDomainService(RegisterReq registerReq){
        Driver driver = registerReq.getDriver();
        RegisterRes registerRes = RegisterRes.builder().driver(driver)
                .dateTime(registerReq.getDateTime()).build();
        boolean isAlreadyRegister = IDriver.isRegister(driver);
        if(isAlreadyRegister)
            registerRes.setResponseCode(RESPONSE_CODE_EXISTED);
        else{
            boolean isRegisterSuccessful = IDriver.register(driver);
            if(isRegisterSuccessful){
                registerRes.setResponseCode(RESPONSE_CODE_SUCCESS);
            }
            else
                registerRes.setResponseCode(RESPONSE_CODE_FAILURE);
        }
        return registerRes;
    }
}
