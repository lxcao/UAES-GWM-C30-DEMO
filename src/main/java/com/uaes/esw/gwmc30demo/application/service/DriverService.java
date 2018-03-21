package com.uaes.esw.gwmc30demo.application.service;

import com.uaes.esw.gwmc30demo.domain.model.driver.*;
import com.uaes.esw.gwmc30demo.domain.model.drivingAnalytics.BatteryConsumption;
import com.uaes.esw.gwmc30demo.domain.model.drivingAnalytics.DrivingAnalytics;
import com.uaes.esw.gwmc30demo.domain.model.vehicle.DrivingModeConfigure;
import com.uaes.esw.gwmc30demo.domain.model.vehicle.Vehicle;

import java.util.List;

import static com.uaes.esw.gwmc30demo.constant.CommonConstants.*;
import static com.uaes.esw.gwmc30demo.domain.repository.driver.IDriverRepository.getDriverInfo;
import static com.uaes.esw.gwmc30demo.infrastructure.json.JSONUtility.transferFromJSON2Object;
import static com.uaes.esw.gwmc30demo.infrastructure.json.JSONUtility.transferFromObject2JSON;

public interface DriverService {
    //注册
    static String register(String registerString){
        RegisterReq registerReq = transferFromJSON2Object(registerString, RegisterReq.class);
        Driver driver = registerReq.getDriver();
        RegisterRes registerResRes = RegisterRes.builder().driver(driver)
                .dateTime(registerReq.getDateTime()).build();
        boolean isAlreadyRegister = IDriver.isRegister(driver);
        if(isAlreadyRegister)
            registerResRes.setResponseCode(RESPONSE_CODE_EXISTED);
        else{
            boolean isRegisterSuccessful = IDriver.register(driver);
            if(isRegisterSuccessful){
                registerResRes.setResponseCode(RESPONSE_CODE_SUCCESS);
            }
            else
                registerResRes.setResponseCode(RESPONSE_CODE_FAILURE);
        }
        return transferFromObject2JSON(registerResRes);
    }
    //登录
    static String login(String loginString){
        LogInReq logInReq = transferFromJSON2Object(loginString, LogInReq.class);
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
        return transferFromObject2JSON(logInRes);
    }

    //登出
    static String logout(String logoutString){
        LogOutReq logOutReq = transferFromJSON2Object(logoutString, LogOutReq.class);
        Driver driver = IDriver.getDriverInfo(logOutReq.getDriver().getCellPhone());
        LogOutRes logOutRes = LogOutRes.builder().driver(driver)
                .dateTime(logOutReq.getDateTime()).build();
        boolean isLogoutSuccessful = IDriver.logout(driver);
        if(isLogoutSuccessful)
            logOutRes.setResponseCode(RESPONSE_CODE_SUCCESS);
        else
            logOutRes.setResponseCode(RESPONSE_CODE_FAILURE);
        return transferFromObject2JSON(logOutRes);
    }

    //绑定车
    boolean bindingVehicle(String cellPhone, String vehicle);
    //选择车
    boolean selectVehicle(String cellPhone, String vehicle);
    //设置默认的驾驶风格
    boolean setDefaultDrivingMode(String cellPhone, String drivingMode);
    //设置当前的驾驶风格
    boolean setCurrentDrivingMode(String cellPhone, String drivingMode);
    //得到默认的驾驶风格
    String getDefaultDrivingMode(String cellPhone);

    boolean setDrivingStyle(Driver driver, Vehicle vehicle, DriverStyle driverStyle);

    BatteryConsumption obtainBatteryConsumption(Vehicle vehicle);

    BatteryConsumption getLastDrivingCycleBatteryConsumption(Driver driver, Vehicle vehicle);

    List<BatteryConsumption> getDrivingCycleBatteryConsumptionByDaily(Vehicle vehicle, int number);

    List<BatteryConsumption> getDrivingCycleBatteryConsumptionWeekly(Vehicle vehicle, int number);

    List<BatteryConsumption> getDrivingCycleBatteryConsumptionByMonthly(Vehicle vehicle, int number);

    boolean setDrivingMode(Driver driver, Vehicle vehicle, DrivingModeConfigure drivingMode);

    int obtainSpeedLimitation(Vehicle vehicle);

    DrivingAnalytics getLastDrivingCycleDrivingAnalytics(Driver driver, Vehicle vehicle);
}
