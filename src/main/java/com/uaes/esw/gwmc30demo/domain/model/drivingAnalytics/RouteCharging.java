package com.uaes.esw.gwmc30demo.domain.model.drivingAnalytics;

import com.uaes.esw.gwmc30demo.domain.model.journeySce.Route;

import java.util.Map;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class RouteCharging {
    private Route route;
    private Map<String,Double> chargingTimeByChargerType;
}
