package com.uaes.esw.gwmc30demo.application.service;

import static com.uaes.esw.gwmc30demo.application.assembler.EnergySavingService.queryESThisWeek;

public interface QueryEnergySavingByThisWeek {
    static String queryESByThisWeek(String reqString){
        return queryESThisWeek(reqString);
    }
}
