package com.uaes.esw.gwmc30demo.domain.service;

import com.uaes.esw.gwmc30demo.domain.model.drivingAnalytics.RouteCharging;
import com.uaes.esw.gwmc30demo.domain.model.journey.Route;
import com.uaes.esw.gwmc30demo.domain.model.vehicle.Vehicle;

import java.util.List;

public interface IChargeTime {
    RouteCharging calChargeTimeByChargerType(Route route, Vehicle vehicle);
}
