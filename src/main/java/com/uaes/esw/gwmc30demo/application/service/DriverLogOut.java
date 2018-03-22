package com.uaes.esw.gwmc30demo.application.service;

import static com.uaes.esw.gwmc30demo.application.assembler.DriverService.logout;

public interface DriverLogOut {
    static String driverLogout(String loginString){
        return logout(loginString);
    }
}
