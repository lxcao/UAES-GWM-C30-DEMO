package com.uaes.esw.gwmc30demo.application.service;

import static com.uaes.esw.gwmc30demo.application.assembler.BatteryService.startPackBatteryBalance;

public interface StartBatteryBalance {
    static String startBatteryBalance(String startBBString){
        return startPackBatteryBalance(startBBString);
    }
}
