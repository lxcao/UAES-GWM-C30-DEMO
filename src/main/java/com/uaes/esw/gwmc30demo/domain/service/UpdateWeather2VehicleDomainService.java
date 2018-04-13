package com.uaes.esw.gwmc30demo.domain.service;

import com.uaes.esw.gwmc30demo.domain.repository.vehicle.IVehicleRepository;
import com.uaes.esw.gwmc30demo.domain.repository.weather.IWeatherRepository;

import java.time.LocalDateTime;

public interface UpdateWeather2VehicleDomainService {
    static void updateWeather2VehicleDomainService(String location){
        System.out.println("UpdateWeather@"+location+"@"+LocalDateTime.now());
        IVehicleRepository.sendWeather2Vehicle(IWeatherRepository.queryWeather(location));
        System.out.println();
    }
}
