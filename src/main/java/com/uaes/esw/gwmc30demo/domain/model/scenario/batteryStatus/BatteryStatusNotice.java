package com.uaes.esw.gwmc30demo.domain.model.scenario.batteryStatus;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class BatteryStatusNotice {
    BatteryStatus batteryStatus;
    String dateTime;
}
