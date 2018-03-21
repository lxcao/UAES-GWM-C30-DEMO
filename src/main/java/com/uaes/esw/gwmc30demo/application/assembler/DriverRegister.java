package com.uaes.esw.gwmc30demo.application.assembler;

import static com.uaes.esw.gwmc30demo.application.service.DriverService.register;

public interface DriverRegister {
    static String driverRegister(String registerString){
        return register(registerString);
    }

}
