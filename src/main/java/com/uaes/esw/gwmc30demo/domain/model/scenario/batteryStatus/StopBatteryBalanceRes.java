package com.uaes.esw.gwmc30demo.domain.model.scenario.batteryStatus;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class StopBatteryBalanceRes {
    BatteryBalance batteryBalance;
    String responseCode;
    String dateTime;
}
