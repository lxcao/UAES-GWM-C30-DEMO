package com.uaes.esw.gwmc30demo.application.service;

import com.uaes.esw.gwmc30demo.domain.model.driver.Driver;
import com.uaes.esw.gwmc30demo.domain.model.driver.DriverStyle;
import com.uaes.esw.gwmc30demo.domain.model.drivingAnalytics.BatteryConsumption;
import com.uaes.esw.gwmc30demo.domain.model.drivingAnalytics.DrivingAnalytics;
import com.uaes.esw.gwmc30demo.domain.model.vehicle.DrivingMode;
import com.uaes.esw.gwmc30demo.domain.model.vehicle.Vehicle;


import java.util.List;

public interface DriverService {
    //注册
    boolean register(String cellPhone, String password);
    //登录
    boolean login(String cellPhone, String password, String DeviceID);
    //登出
    boolean logout(String cellPhone, String DeviceID);
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

    boolean setDrivingMode(Driver driver, Vehicle vehicle, DrivingMode drivingMode);

    int obtainSpeedLimitation(Vehicle vehicle);

    DrivingAnalytics getLastDrivingCycleDrivingAnalytics(Driver driver, Vehicle vehicle);
}
