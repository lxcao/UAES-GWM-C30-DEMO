package com.uaes.esw.gwmc30demo.application.service;

import static com.uaes.esw.gwmc30demo.application.assembler.EnergySavingService.queryESToday;

public interface QueryEnergySavingByToday {
    static String queryESByToday(String reqString){
        return queryESToday(reqString);
    }
}
