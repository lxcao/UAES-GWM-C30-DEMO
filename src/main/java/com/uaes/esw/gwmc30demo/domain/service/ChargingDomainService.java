package com.uaes.esw.gwmc30demo.domain.service;

import com.uaes.esw.gwmc30demo.domain.model.charger.Charger;
import com.uaes.esw.gwmc30demo.domain.model.drivingAnalytics.RouteCharging;
import com.uaes.esw.gwmc30demo.domain.model.journey.Route;
import com.uaes.esw.gwmc30demo.domain.model.vehicle.Vehicle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.uaes.esw.gwmc30demo.constant.CalculateChargingTimeConstants.*;
import static com.uaes.esw.gwmc30demo.domain.repository.charger.IChargerRepository.getChargerList;

public interface ChargingDomainService {
    static RouteCharging calChargeTimeByChargerType(Route route, Vehicle vehicle){
        RouteCharging routeChaging = RouteCharging.builder()
                .route(route)
                .chargingTimeByChargerType(calChargingTimeByChargerType(getChargerList(),route,vehicle)).build();
        return routeChaging;
    }

    static Map<String,Double> calChargingTimeByChargerType(List<Charger> chargerList, Route route, Vehicle vehicle){
        Map<String, Double> chargingTimeByChargerType = new HashMap();
        chargerList.forEach(charger -> {
            chargingTimeByChargerType.put(charger.getType(),
                    calChargingTime(route.getDistance(),vehicle.getBattery().getSoc(),charger.getChargingPower()));
        });
        return chargingTimeByChargerType;
    }

    static double calChargingTime(int distance, double soc, double chargerPowerKW){
        double element = (double)distance*PARAMETER_WH/PARAMETER_HKM-soc*PARAMETER_AH*PARAMETER_V/PARAMETER_K;
        return element/chargerPowerKW;
    }
}
