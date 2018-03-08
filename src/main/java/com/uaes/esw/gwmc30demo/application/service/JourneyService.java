package com.uaes.esw.gwmc30demo.application.service;

import com.uaes.esw.gwmc30demo.domain.model.drivingAnalytics.RouteCharging;
import com.uaes.esw.gwmc30demo.domain.model.journey.Journey;
import com.uaes.esw.gwmc30demo.domain.model.journey.Route;
import com.uaes.esw.gwmc30demo.domain.model.vehicle.Vehicle;

import java.util.List;
import java.util.stream.Collectors;

import static com.uaes.esw.gwmc30demo.domain.repository.vehicle.IVehicleRepository.getVehicleSnapshot;
import static com.uaes.esw.gwmc30demo.domain.service.ChargingDomainService.calChargeTimeByChargerType;
import static com.uaes.esw.gwmc30demo.infrastructure.json.JSONUtility.transferFromJSON2Object;
import static com.uaes.esw.gwmc30demo.infrastructure.json.JSONUtility.transferFromObject2JSON;

public interface JourneyService {

    //得到规划的里程
    public static Journey getJourney(String journeyString){
        Journey journey = transferFromJSON2Object(journeyString,Journey.class);
        return journey;
    }

    //返回规划里程的充电时间
    public static String chargingOnDemandByJourney(String journeyString){
        Journey journey = transferFromJSON2Object(journeyString,Journey.class);
        List<Route> routes = journey.getRoutes();
        Vehicle c30VehicleSnapshot = getVehicleSnapshot();
        List <RouteCharging> routeChargingsList = routes.stream()
                .map(route -> calChargeTimeByChargerType(route, c30VehicleSnapshot))
                .collect(Collectors.toList());
        String routeChargingListJSON = transferFromObject2JSON(routeChargingsList);
        return routeChargingListJSON;
    }
}
