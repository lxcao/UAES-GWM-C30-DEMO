package com.uaes.esw.gwmc30demo.application.service;

import static com.uaes.esw.gwmc30demo.application.assembler.DrivingModeService.setDefaultDrivingMode;

public interface SetDefaultDrivingStyle {
    static String setDefaultDrivingStyle(String setString){
        return setDefaultDrivingMode(setString);
    }
}
