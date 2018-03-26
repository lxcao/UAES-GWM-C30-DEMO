package com.uaes.esw.gwmc30demo.domain.model.scenario.drivingAnalytics;

import com.uaes.esw.gwmc30demo.domain.model.entity.journey.Route;

import java.util.Map;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class RouteCharging {
    private Route route;
    private Map<String,Double> chargingTimeByChargerType;
}
