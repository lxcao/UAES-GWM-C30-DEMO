package com.uaes.esw.gwmc30demo.domain.model.scenario.chargingOnDemand;

import com.uaes.esw.gwmc30demo.domain.model.entity.driver.Driver;
import com.uaes.esw.gwmc30demo.domain.model.entity.journey.Journey;
import com.uaes.esw.gwmc30demo.domain.model.scenario.drivingAnalytics.RouteCharging;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class ChargingOnDemandRes {
    Driver driver;
    Journey jouney;
    List<RouteCharging> routeChargings;
    String dateTime;
    String responseCode;
}
