package com.uaes.esw.gwmc30demo.domain.model.scenario.batteryStatus;
import com.uaes.esw.gwmc30demo.domain.model.entity.driver.Driver;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class StopBatteryBalanceRes {
    Driver driver;
    BatteryBalance batteryBalance;
    BatteryStatus batteryStatus;
    String responseCode;
    String dateTime;
}
