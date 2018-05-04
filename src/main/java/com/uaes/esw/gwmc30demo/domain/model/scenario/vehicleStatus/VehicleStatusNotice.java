package com.uaes.esw.gwmc30demo.domain.model.scenario.vehicleStatus;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class VehicleStatusNotice {
    VehicleStatus vehicleStatus;
    String dateTime;
}
