package com.uaes.esw.gwmc30demo.application.service;

import static com.uaes.esw.gwmc30demo.application.assembler.EnergySavingService.queryESThisTime;

public interface QueryEnergySavingByThisTime {
    static String queryESByThisTime(String queryString){
        return queryESThisTime(queryString);
    }
}
