package com.uaes.esw.gwmc30demo.application.assembler;

import static com.uaes.esw.gwmc30demo.application.service.VehicleService.setCurrentDrivingMode;

public interface SetCurrentDrivingStyle {
    static String setCurrentDrivingStyle(String setString){
        return setCurrentDrivingMode(setString);
    }
}
