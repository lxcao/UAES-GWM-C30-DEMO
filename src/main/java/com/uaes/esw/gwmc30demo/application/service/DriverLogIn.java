package com.uaes.esw.gwmc30demo.application.service;

import static com.uaes.esw.gwmc30demo.application.assembler.DriverService.login;

public interface DriverLogIn {
    static String driverLogin(String loginString){
        return login(loginString);
    }

}
