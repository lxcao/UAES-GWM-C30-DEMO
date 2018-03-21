package com.uaes.esw.gwmc30demo.application.assembler;

import static com.uaes.esw.gwmc30demo.application.service.DriverService.login;

public interface DriverLogIn {
    static String driverLogin(String loginString){
        return login(loginString);
    }
}
