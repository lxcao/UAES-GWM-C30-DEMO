package com.uaes.esw.gwmc30demo.domain.model.entity.driver;

import com.uaes.esw.gwmc30demo.domain.repository.driver.IDriverRepository;

public interface IDriver {
    //是否已经注册
    static boolean isRegister(Driver driver){
        boolean result = IDriverRepository.isRegisterExist(driver.getCellPhone());
        return result;
    }
    //注册
    static boolean register(Driver driver){
        IDriverRepository.addRegisterDriver(driver.getCellPhone());
        IDriverRepository.fillDriverInfo(driver);
        return true;
    }

    //是否已经登录
    static boolean isLogin(Driver driver){
        boolean result = IDriverRepository.isLoginExist(driver.getCellPhone());
        return result;
    }

    //登录
    static boolean login(Driver driver){
        IDriverRepository.addLoginDriver(driver.getCellPhone());
        return true;
    }
    //登出
    static boolean logout(Driver driver){
        IDriverRepository.removeLoginDriver(driver.getCellPhone());
        return true;
    }

    //得到司机消息
    static Driver getDriverInfo(String cellPhone){
        return IDriverRepository.getDriverInfo(cellPhone);
    }

}
