package com.uaes.esw.gwmc30demo.domain.service;

import com.uaes.esw.gwmc30demo.domain.model.entity.weather.Weather;
import com.uaes.esw.gwmc30demo.domain.repository.vehicle.IVehicleRepository;
import com.uaes.esw.gwmc30demo.domain.repository.weather.IWeatherRepository;

import java.time.LocalDateTime;

import static com.uaes.esw.gwmc30demo.infrastructure.utils.LoggerUtils.commonLogInfo;

public interface UpdateWeather2VehicleDomainService {
    static void updateWeather2VehicleDomainService(String location){
        commonLogInfo("UpdateWeather@"+location+"@"+LocalDateTime.now());
        Weather weather = IWeatherRepository.queryWeather(location);
        IWeatherRepository.setWeatherMessage2Redis(weather);
        IVehicleRepository.sendWeatherAndDMConfig2Vehicle(weather);
    }
}
