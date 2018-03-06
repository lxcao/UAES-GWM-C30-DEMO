package com.uaes.esw.gwmc30demo.domain.model.drivingAnalytics;

import com.uaes.esw.gwmc30demo.domain.model.charger.Charger;
import com.uaes.esw.gwmc30demo.domain.model.journey.Route;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class RouteCharging {
    private Route route;
    private List<Charger> chargers;
}
