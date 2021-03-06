package com.uaes.esw.gwmc30demo.domain.service;

import com.uaes.esw.gwmc30demo.domain.model.entity.charger.Charger;
import com.uaes.esw.gwmc30demo.domain.model.scenario.chargingOnDemand.ChargingOnDemandReq;
import com.uaes.esw.gwmc30demo.domain.model.scenario.chargingOnDemand.ChargingOnDemandRes;
import com.uaes.esw.gwmc30demo.domain.model.scenario.drivingAnalytics.RouteCharging;
import com.uaes.esw.gwmc30demo.domain.model.entity.journey.Route;
import com.uaes.esw.gwmc30demo.domain.model.entity.vehicle.Vehicle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.uaes.esw.gwmc30demo.constant.CalculateChargingTimeConstants.*;
import static com.uaes.esw.gwmc30demo.constant.CommonConstants.RESPONSE_CODE_SUCCESS;
import static com.uaes.esw.gwmc30demo.domain.repository.charger.IChargerRepository.getChargerList;
import static com.uaes.esw.gwmc30demo.domain.repository.vehicle.IVehicleRepository.getVehicleSnapshot;
import static com.uaes.esw.gwmc30demo.infrastructure.json.JSONUtility.transferFromObject2JSON;
import static com.uaes.esw.gwmc30demo.infrastructure.utils.LoggerUtils.chargingOnDemandLogInfo;

public interface ChargingDomainService {

    static String calChargeTimeByChargerType4Journey(ChargingOnDemandReq chargingOnDemandReq, String vinCode){
        Vehicle vehicle = getVehicleSnapshot(vinCode);
        chargingOnDemandLogInfo("soc="+vehicle.getBattery().getSoc());

        List<Route> routes = chargingOnDemandReq.getJourney().getRoutes();
        List <RouteCharging> routeChargingsList = routes.stream()
                .map(route -> calChargeTimeByChargerType(route, vehicle))
                .collect(Collectors.toList());
        ChargingOnDemandRes chargingOnDemandRes =ChargingOnDemandRes.builder()
                .driver(chargingOnDemandReq.getDriver())
                .dateTime(chargingOnDemandReq.getDateTime())
                .jouney(chargingOnDemandReq.getJourney())
                .responseCode(RESPONSE_CODE_SUCCESS)
                .routeChargings(routeChargingsList)
                .build();
        String chargingOnDemandResString = transferFromObject2JSON(chargingOnDemandRes);
        return chargingOnDemandResString;
    }

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
        double element = (double)distance*PARAMETER_WH/PARAMETER_HKM-soc*PARAMETER_AH*PARAMETER_V;
        return element/chargerPowerKW;
    }
}
