package com.uaes.esw.gwmc30demo.application.service;

import static com.uaes.esw.gwmc30demo.application.assembler.DriverService.register;

public interface DriverRegister {
    static String driverRegister(String registerString){
        return register(registerString);
    }

}
