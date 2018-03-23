package com.uaes.esw.gwmc30demo.domain.service;

import com.uaes.esw.gwmc30demo.domain.model.entity.driver.Driver;
import com.uaes.esw.gwmc30demo.domain.model.entity.driver.IDriver;

public interface DriverDomainService {

    static boolean isLogInDomainService(Driver driver){
        return IDriver.isLogin(driver);
    }
}
