package com.uaes.esw.gwmc30demo.domain.model.scenario.batteryStatus;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class BatteryBalanceInstruction {
    int balanceInstruction;
    String dateTime;
}
