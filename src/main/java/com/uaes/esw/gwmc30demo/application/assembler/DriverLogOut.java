package com.uaes.esw.gwmc30demo.application.assembler;

import static com.uaes.esw.gwmc30demo.application.service.DriverService.logout;

public interface DriverLogOut {
    static String driverLogout(String loginString){
        return logout(loginString);
    }
}
