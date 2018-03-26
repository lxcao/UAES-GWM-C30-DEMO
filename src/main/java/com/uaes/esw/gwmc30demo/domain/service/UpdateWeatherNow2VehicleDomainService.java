package com.uaes.esw.gwmc30demo.domain.service;

import com.uaes.esw.gwmc30demo.domain.repository.vehicle.IVehicleRepository;
import com.uaes.esw.gwmc30demo.domain.repository.weather.IWeatherRepository;

public interface UpdateWeatherNow2VehicleDomainService {
    static void updateWeatherNow2VehicleDomainService(String location){
        IVehicleRepository.sendWeather2Vehicle(IWeatherRepository.queryWeather(location));
    }
}
