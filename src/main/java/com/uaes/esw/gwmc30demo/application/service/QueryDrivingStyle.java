package com.uaes.esw.gwmc30demo.application.service;

import static com.uaes.esw.gwmc30demo.application.assembler.VehicleService.queryDrivingMode;

public interface QueryDrivingStyle {
    static String queryDrivingStyle(String queryString){
        return queryDrivingMode(queryString);
    }
}
