package com.uaes.esw.gwmc30demo.application.assembler;

import static com.uaes.esw.gwmc30demo.application.service.VehicleService.queryDrivingMode;

public interface QueryDrivingStyle {
    static String queryDrivingStyle(String queryString){
        return queryDrivingMode(queryString);
    }
}
