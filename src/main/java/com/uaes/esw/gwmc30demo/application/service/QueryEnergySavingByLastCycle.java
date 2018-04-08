package com.uaes.esw.gwmc30demo.application.service;


import static com.uaes.esw.gwmc30demo.application.assembler.EnergySavingService.queryESLastCycle;

public interface QueryEnergySavingByLastCycle {
    static String queryESByLastCycle(String queryString){
        return queryESLastCycle(queryString);
    }
}
