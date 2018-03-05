package com.uaes.esw.gwmc30demo.application.service;

import com.uaes.esw.gwmc30demo.domain.model.road.Road;
import com.uaes.esw.gwmc30demo.domain.model.vehicle.DrivingMode;
import com.uaes.esw.gwmc30demo.domain.model.vehicle.Vehicle;
import com.uaes.esw.gwmc30demo.domain.model.weather.Weather;

public interface VehicleService {

    //得到所有的驾驶模式
    String getAllDrivingMode(String vin);

    Weather getWeather(Road road);

    DrivingMode provideDrivingMode(Weather weather);

    int getSpeedLimitation(Road road);

    boolean setVehicleSpeed(Vehicle vehicle, int speed);
}
