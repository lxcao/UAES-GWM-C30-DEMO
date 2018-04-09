package com.uaes.esw.gwmc30demo.application.service;

import static com.uaes.esw.gwmc30demo.application.assembler.BatteryService.stopPackBatteryBalance;

public interface StopBatteryBalance {
    static String stopBatteryBalance(String stopBBString){
        return stopPackBatteryBalance(stopBBString);
    }
}
