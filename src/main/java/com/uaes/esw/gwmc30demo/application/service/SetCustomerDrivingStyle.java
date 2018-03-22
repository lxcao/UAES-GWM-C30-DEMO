package com.uaes.esw.gwmc30demo.application.service;

import static com.uaes.esw.gwmc30demo.application.assembler.DrivingModeService.setCustomerDrivingMode;

public interface SetCustomerDrivingStyle {
    static String setCustomerDrivingStyle(String setString){
        return setCustomerDrivingMode(setString);
    }
}
