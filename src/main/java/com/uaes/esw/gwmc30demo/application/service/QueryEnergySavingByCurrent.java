package com.uaes.esw.gwmc30demo.application.service;

import static com.uaes.esw.gwmc30demo.application.assembler.EnergySavingService.queryESCurrent;

public interface QueryEnergySavingByCurrent {
    static String queryESByCurrent(String queryString){
        return queryESCurrent(queryString);
    }
}
