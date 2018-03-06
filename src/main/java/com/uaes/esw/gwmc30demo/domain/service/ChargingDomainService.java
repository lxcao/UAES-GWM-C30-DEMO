package com.uaes.esw.gwmc30demo.domain.service;

import com.uaes.esw.gwmc30demo.domain.model.charger.Charger;
import com.uaes.esw.gwmc30demo.domain.model.drivingAnalytics.RouteCharging;
import com.uaes.esw.gwmc30demo.domain.model.journey.Route;
import com.uaes.esw.gwmc30demo.domain.model.vehicle.Vehicle;

import java.util.ArrayList;
import java.util.List;

import static com.uaes.esw.gwmc30demo.constant.CalculateChargingTimeConstants.*;
import static com.uaes.esw.gwmc30demo.constant.ChargerConstants.*;

public class ChargingDomainService implements IChargeTime {
    public RouteCharging calChargeTimeByChargerType(Route route, Vehicle vehicle){
        List chargerList = new ArrayList();
        Charger ACCharger = Charger.builder().type(CHARGER_AC_POWER_TYPE)
                .chargingPower(CHARGER_AC_POWER_KW)
                .chargingTime(calChargingTime(route.getDistance(),vehicle.getBattery().getSoc(),CHARGER_AC_POWER_KW))
                .build();
        chargerList.add(ACCharger);
        Charger DCCharger = Charger.builder().type(CHARGER_DC_POWER_TYPE)
                .chargingPower(CHARGER_DC_POWER_KW)
                .chargingTime(calChargingTime(route.getDistance(),vehicle.getBattery().getSoc(),CHARGER_DC_POWER_KW))
                .build();
        chargerList.add(DCCharger);
        RouteCharging routeChaging = RouteCharging.builder().route(route)
                .chargers(chargerList).build();
        return routeChaging;
    }

    private double calChargingTime(int distance, int soc, double chargerPowerKW){
        double element = (double)(distance*PARAMETER_KWH_PER_KM-soc*PARAMETER_AH*PARAMETER_V);
        return element/chargerPowerKW;
    }
}
