package com.uaes.esw.gwmc30demo.domain.model.scenario.chargingOnDemand;

import com.uaes.esw.gwmc30demo.domain.model.entity.driver.Driver;
import com.uaes.esw.gwmc30demo.domain.model.entity.journey.Journey;

import lombok.Builder;
import lombok.Data;


@Data @Builder
public class ChargingOnDemandReq {
    Driver driver;
    Journey journey;
    String dateTime;

}
