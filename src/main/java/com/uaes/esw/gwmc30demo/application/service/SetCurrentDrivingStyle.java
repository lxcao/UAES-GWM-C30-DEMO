package com.uaes.esw.gwmc30demo.application.service;

import static com.uaes.esw.gwmc30demo.application.assembler.VehicleService.setCurrentDrivingMode;

public interface SetCurrentDrivingStyle {
    static String setCurrentDrivingStyle(String setString){
        return setCurrentDrivingMode(setString);
    }
}
